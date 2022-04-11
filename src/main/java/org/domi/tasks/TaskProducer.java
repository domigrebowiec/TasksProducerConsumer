package org.domi.tasks;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TaskProducer {
    private final Integer producerId;
    private final TasksQueue tasksQueue;
    private final TaskCreator taskCreator;

    public TaskProducer(Integer producerId, TasksQueue tasksQueue, TaskCreator taskCreator) {
        this.producerId = producerId;
        this.tasksQueue = tasksQueue;
        this.taskCreator = taskCreator;
    }

    public boolean produce() {
        synchronized (tasksQueue) {
            if (tasksQueue.hasCapacity()) {
                return addNewTask();
            } else {
                log.info("No capacity for Producer {}", producerId);
                return false;
            }
        }
    }

    private boolean addNewTask() {
        Task task = taskCreator.create();
        if (tasksQueue.addNewTask(task)) {
            log.info("New task added by Producer {} {}", producerId, task);
            return true;
        } else {
            log.info("New task was not added by Producer {}", producerId);
            return false;
        }
    }
}
