package org.domi;

import org.domi.configuration.TaskConfiguration;
import org.domi.tasks.TasksService;
import org.domi.tasks.stringtask.StringTasksService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(TaskConfiguration.class)
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            TasksService tasksService = ctx.getBean(StringTasksService.class);
            tasksService.processTasks();
        };
    }
}
