package org.domi.configuration;

import org.domi.tasks.TaskConsumer;
import org.domi.tasks.TaskProducer;
import org.domi.tasks.TasksQueue;
import org.domi.tasks.stringtask.ExpressionCalculator;
import org.domi.tasks.stringtask.StringTaskCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class BeansConfiguration {

    private static Integer producerId = 1;
    private static Integer consumerId = 1;

    @Bean(name = "stringTaskProducer")
    @Scope("prototype")
    public TaskProducer stringTaskProducer(TasksQueue tasksQueue, StringTaskCreator stringTaskGenerator) {
        return new TaskProducer(producerId++, tasksQueue, stringTaskGenerator);
    }

    @Bean(name = "stringTaskConsumer")
    @Scope("prototype")
    public TaskConsumer stringTaskConsumer(TasksQueue tasksQueue, ExpressionCalculator stringTaskExecutor) {
        return new TaskConsumer(consumerId++, tasksQueue, stringTaskExecutor);
    }

    @Bean
    public TasksQueue tasksQueue(StringTaskConfiguration configuration) {
        return new TasksQueue(configuration.getMaxQueueSize());
    }
}
