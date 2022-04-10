package org.domi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Configuration
public class ThreadsConfiguration {

    @Bean
    public ScheduledExecutorService scheduledExecutorService(StringTaskConfiguration configuration) {
        return new ScheduledThreadPoolExecutor(getThreadsNumber(configuration));
    }

    private int getThreadsNumber(StringTaskConfiguration configuration) {
        return configuration.getNumberOfConsumers() + configuration.getNumberOfProducers();
    }
}
