package org.domi.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "task")
@Getter
@Setter
public class TaskConfiguration {
    private Integer numberOfProducers;
    private Integer numberOfConsumers;
    private Integer maxQueueSize;
}
