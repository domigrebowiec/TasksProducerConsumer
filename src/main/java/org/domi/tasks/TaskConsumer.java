package org.domi.tasks;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TaskConsumer {
    private final Integer consumerId;
    private final TasksQueue tasksQueue;
    private final TaskExecutor taskExecutor;

    public TaskConsumer(Integer consumerId, TasksQueue tasksQueue, TaskExecutor taskExecutor) {
        this.consumerId = consumerId;
        this.tasksQueue = tasksQueue;
        this.taskExecutor = taskExecutor;
    }

    public void run() {
        try {
            Task task = tasksQueue.readTask();
            if (task != null) {
                TaskResult taskResult = taskExecutor.execute(task);
                log.info("Executed task by Consumer {} :: {} result :: {}", consumerId, task, taskResult.getValue());
            } else {
                log.info("Empty queue for Consumer {}", consumerId);
            }
        } catch (InterruptedException e) {
            log.error("Consumer {} interrupted", consumerId);
            Thread.currentThread().interrupt();
        }
    }
}
