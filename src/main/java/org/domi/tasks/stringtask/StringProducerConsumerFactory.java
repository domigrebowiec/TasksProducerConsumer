package org.domi.tasks.stringtask;

import org.domi.tasks.*;
import org.springframework.stereotype.Component;

@Component
public class StringProducerConsumerFactory implements ProducerConsumerFactory {
    private static int producerId = 0;
    private static int consumerId = 0;
    private final TasksQueue tasksQueue;
    private final StringTaskCreator stringTaskCreator;
    private final TaskExecutor expressionCalculator;

    public StringProducerConsumerFactory(TasksQueue tasksQueue) {
        this.tasksQueue = tasksQueue;
        this.stringTaskCreator = new StringTaskCreator();
        this.expressionCalculator = new ExpressionCalculator();
    }

    @Override
    public TaskProducer createProducer() {
        return new TaskProducer(producerId++, tasksQueue, stringTaskCreator);
    }

    @Override
    public TaskConsumer createConsumer() {
        return new TaskConsumer(consumerId++, tasksQueue, expressionCalculator);
    }
}
