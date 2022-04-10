package org.domi.tasks;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TasksQueue {
    private final ArrayBlockingQueue<Task> queue;
    private final Integer halfQueueSize;

    public TasksQueue(Integer maxQueueSize) {
        this.queue = new ArrayBlockingQueue<>(maxQueueSize);
        this.halfQueueSize = maxQueueSize/2;
        log.info("TasksQueue created with max queue size = {}, half queue size = {}", maxQueueSize, halfQueueSize);
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
