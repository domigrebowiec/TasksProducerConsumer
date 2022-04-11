package org.domi.tasks;

public interface ProducerConsumerFactory {
    TaskProducer createProducer();
    TaskConsumer createConsumer();
}
