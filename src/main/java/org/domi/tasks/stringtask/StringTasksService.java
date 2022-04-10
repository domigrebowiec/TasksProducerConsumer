package org.domi.tasks.stringtask;

import lombok.extern.slf4j.Slf4j;
import org.domi.configuration.StringTaskConfiguration;
import org.domi.tasks.TaskConsumer;
import org.domi.tasks.TaskProducer;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
public class StringTasksService {
    private final List<TaskProducer> taskProducerList;
    private final List<TaskConsumer> taskConsumerList;
    private final ScheduledExecutorService executor;

    public StringTasksService(StringTaskConfiguration configuration, ApplicationContext applicationContext, ScheduledExecutorService executor) {
        this.taskProducerList = createTaskProducers(configuration.getNumberOfProducers(), applicationContext);
        this.taskConsumerList = createTaskConsumers(configuration.getNumberOfConsumers(), applicationContext);
        this.executor = executor;
    }

    public void processTasks() {
        for (TaskProducer taskProducer : taskProducerList) {
            executor.scheduleAtFixedRate(taskProducer::run, 0, 600, TimeUnit.MILLISECONDS);
        }
        for (TaskConsumer taskConsumer : taskConsumerList) {
            executor.scheduleAtFixedRate(taskConsumer::run, 0, 600, TimeUnit.MILLISECONDS);
        }
    }

    private List<TaskProducer> createTaskProducers(Integer numberOfProducers, ApplicationContext applicationContext) {
        return IntStream.range(0, numberOfProducers)
                .mapToObj(i -> applicationContext.getBean("stringTaskProducer", TaskProducer.class))
                .collect(Collectors.toList());
    }

    private List<TaskConsumer> createTaskConsumers(Integer numberOfConsumers, ApplicationContext applicationContext) {
        return IntStream.range(0, numberOfConsumers)
                .mapToObj(i -> applicationContext.getBean("stringTaskConsumer", TaskConsumer.class))
                .collect(Collectors.toList());
    }
}
