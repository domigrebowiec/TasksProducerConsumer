package org.domi.tasks;

import lombok.extern.slf4j.Slf4j;
import org.domi.configuration.TaskConfiguration;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class TasksQueue {
    private final ArrayBlockingQueue<Task> queue;
    private final Integer halfQueueSize;

    public TasksQueue(TaskConfiguration taskConfiguration) {
        this.queue = new ArrayBlockingQueue<>(taskConfiguration.getMaxQueueSize());
        this.halfQueueSize = taskConfiguration.getMaxQueueSize()/2;
        log.info("TasksQueue created with max queue size = {}, half queue size = {}", taskConfiguration.getMaxQueueSize(), halfQueueSize);
    }

    public boolean hasCapacity() {
        return queue.remainingCapacity() >= halfQueueSize;
    }

    public boolean addNewTask(Task task) {
       return queue.offer(task);
    }

    public Task readTask() throws InterruptedException {
        return queue.poll(2, TimeUnit.SECONDS);
    }
}
