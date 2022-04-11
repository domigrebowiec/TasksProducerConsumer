package org.domi.tasks.stringtask;

import org.domi.configuration.TaskConfiguration;
import org.domi.tasks.TasksService;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledExecutorService;

@Component
public class StringTasksService {
    private final TasksService tasksService;
    public StringTasksService(ScheduledExecutorService executor, StringProducerConsumerFactory factory, TaskConfiguration configuration) {
        tasksService = new TasksService(executor, factory, configuration);
    }

    public void processTasks() {
        tasksService.processTasks();
    }
}
