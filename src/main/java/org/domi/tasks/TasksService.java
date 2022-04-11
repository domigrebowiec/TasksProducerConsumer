package org.domi.tasks;

import org.domi.configuration.TaskConfiguration;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TasksService {
    private final ScheduledExecutorService executor;
    private final ProducerConsumerFactory producerConsumerFactory;
    private final TaskConfiguration configuration;

    public TasksService(ScheduledExecutorService executor, ProducerConsumerFactory producerConsumerFactory, TaskConfiguration configuration) {
        this.executor = executor;
        this.producerConsumerFactory = producerConsumerFactory;
        this.configuration = configuration;
    }

    public void processTasks() {
        for (TaskProducer taskProducer : getTaskProducers()) {
            executor.scheduleAtFixedRate(taskProducer::run, 0, 600, TimeUnit.MILLISECONDS);
        }
        for (TaskConsumer taskConsumer : getTaskConsumers()) {
            executor.scheduleAtFixedRate(taskConsumer::run, 0, 600, TimeUnit.MILLISECONDS);
        }
    }

    private List<TaskConsumer> getTaskConsumers() {
        return IntStream.range(0, configuration.getNumberOfConsumers()).mapToObj(i -> producerConsumerFactory.createConsumer()).collect(Collectors.toList());
    }

    private List<TaskProducer> getTaskProducers() {
        return IntStream.range(0, configuration.getNumberOfProducers()).mapToObj(i -> producerConsumerFactory.createProducer()).collect(Collectors.toList());
    }
}
