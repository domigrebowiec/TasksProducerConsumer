package org.domi.tasks;

import org.domi.configuration.TaskConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TaskConsumerTest {
    private static final String TASK_RESULT = "10";
    private static final String TASK_VALUE = "20";
    private TasksQueue tasksQueue;
    private TaskConsumer taskConsumer;

    @BeforeEach
    public void setup() {
        TaskConfiguration taskConfiguration = new TaskConfiguration();
        taskConfiguration.setMaxQueueSize(10);
        tasksQueue = new TasksQueue(taskConfiguration);
        taskConsumer = new TaskConsumer(1, tasksQueue, task -> () -> TASK_RESULT);
    }

    @Test
    public void shouldReturnEmptyWhenEmptyTaskQueue() {
        // when && then
        assertThat(taskConsumer.consume()).isEmpty();
    }

    @Test
    public void shouldReturnValueWhenTaskInTaskQueue() {
        // given
        tasksQueue.addNewTask(new TaskStub(TASK_VALUE));

        // when
        Optional<TaskResult> taskResult = taskConsumer.consume();

        // then
        assertThat(taskResult).isNotEmpty();
        assertThat(taskResult.get().getValue()).isEqualTo(TASK_RESULT);
    }
}