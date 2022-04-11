package org.domi.tasks.stringtask;

import org.domi.configuration.TaskConfiguration;
import org.domi.tasks.TasksService;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledExecutorService;

@Component
public class StringTasksService extends TasksService {

    public StringTasksService(ScheduledExecutorService executor, StringProducerConsumerFactory factory, TaskConfiguration configuration) {
        super(executor, factory, configuration);
    }
}
