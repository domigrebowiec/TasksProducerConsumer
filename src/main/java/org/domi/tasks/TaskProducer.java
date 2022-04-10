package org.domi.tasks;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TaskProducer {
    private final Integer producerId;
    private final TasksQueue tasksQueue;
    private final TaskGenerator taskGenerator;

    public TaskProducer(Integer producerId, TasksQueue tasksQueue, TaskGenerator taskGenerator) {
        this.producerId = producerId;
        this.tasksQueue = tasksQueue;
        this.taskGenerator = taskGenerator;
    }

    public void run() {
        if (tasksQueue.hasCapacity()) {
            Task task = taskGenerator.generate();
            tasksQueue.addNewTask(task);
            log.info("New task added by Producer {} {}", producerId, task);
        } else {
            log.info("No capacity for Producer {}", producerId);
        }
    }
}
