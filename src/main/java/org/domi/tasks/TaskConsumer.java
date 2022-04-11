package org.domi.tasks;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

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

    public Optional<TaskResult> consume() {
        try {
            return getTaskResult(tasksQueue.readTask());
        } catch (InterruptedException e) {
            log.error("Consumer {} interrupted", consumerId);
            Thread.currentThread().interrupt();
        }
        return Optional.empty();
    }

    private Optional<TaskResult> getTaskResult(Task task) {
        if (task != null) {
            TaskResult taskResult = taskExecutor.execute(task);
            log.info("Executed task by Consumer {} :: {} result :: {}", consumerId, task, taskResult.getValue());
            return Optional.of(taskResult);
        } else {
            log.info("Empty queue for Consumer {}", consumerId);
            return Optional.empty();
        }
    }
}
