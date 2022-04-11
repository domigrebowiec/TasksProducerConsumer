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

    public void produce() {
        synchronized (tasksQueue) {
            if (tasksQueue.hasCapacity()) {
                Task task = taskCreator.create();
                tasksQueue.addNewTask(task);
                log.info("New task added by Producer {} {}", producerId, task);
            } else {
                log.info("No capacity for Producer {}", producerId);
            }
        }
    }
}
