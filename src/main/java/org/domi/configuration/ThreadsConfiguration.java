package org.domi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Configuration
public class ThreadsConfiguration {

    @Bean
    public ScheduledExecutorService scheduledExecutorService(TaskConfiguration configuration) {
        return new ScheduledThreadPoolExecutor(getThreadsNumber(configuration));
    }

    private int getThreadsNumber(TaskConfiguration configuration) {
        return configuration.getNumberOfConsumers() + configuration.getNumberOfProducers();
    }
}
