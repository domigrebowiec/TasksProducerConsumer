package org.domi.tasks;

import lombok.extern.slf4j.Slf4j;
import org.domi.configuration.TaskConfiguration;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Component
public class TasksQueue {
    private final ArrayBlockingQueue<Task> queue;
    private final Integer halfQueueSize;
    private final AtomicBoolean full = new AtomicBoolean(false);

    public TasksQueue(TaskConfiguration taskConfiguration) {
        this.queue = new ArrayBlockingQueue<>(taskConfiguration.getMaxQueueSize());
        this.halfQueueSize = taskConfiguration.getMaxQueueSize()/2;
        log.info("TasksQueue created with max queue size = {}, half queue size = {}", taskConfiguration.getMaxQueueSize(), halfQueueSize);
    }

    public synchronized boolean hasCapacity() {
        int remainingCapacity = queue.remainingCapacity();
        return full.get() ? remainingCapacity >= halfQueueSize : remainingCapacity > 0;
    }

    public synchronized boolean addNewTask(Task task) {
        boolean added = queue.offer(task);
        if (added && queue.remainingCapacity() == 0) {
            full.set(true);
        }
        return added;
    }

    public synchronized Task readTask() throws InterruptedException {
        Task task = queue.poll(20, TimeUnit.MILLISECONDS);
        if (full.get() && queue.remainingCapacity() >= halfQueueSize) {
            full.set(false);
        }
        return task;
    }

    public int size() {
        return queue.size();
    }
}
