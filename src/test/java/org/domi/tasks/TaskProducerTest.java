package org.domi.tasks;

import org.domi.configuration.TaskConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TaskProducerTest {
    private static final TaskStub TASK_STUB = new TaskStub("20");
    private static final int MAX_QUEUE_SIZE = 4;
    private TasksQueue tasksQueue;
    private TaskProducer taskProducer;

    @BeforeEach
    public void setup() {
        TaskConfiguration taskConfiguration = new TaskConfiguration();
        taskConfiguration.setMaxQueueSize(MAX_QUEUE_SIZE);
        tasksQueue = new TasksQueue(taskConfiguration);
        taskProducer = new TaskProducer(1, tasksQueue, () -> TASK_STUB);
    }

    @Test
    public void shouldAddNewTaskToEmptyTasksQueue() {
        // when && then
        assertThat(taskProducer.produce()).isTrue();
    }

    @Test
    public void shouldAddNewTaskToNotEmptyTasksQueue() {
        // given
        tasksQueue.addNewTask(TASK_STUB);
        tasksQueue.addNewTask(TASK_STUB);
        tasksQueue.addNewTask(TASK_STUB);

        // when && then
        assertThat(taskProducer.produce()).isTrue();
    }

    @Test
    public void shouldNotAddNewTaskToFullTasksQueue() {
        // given
        tasksQueue.addNewTask(TASK_STUB);
        tasksQueue.addNewTask(TASK_STUB);
        tasksQueue.addNewTask(TASK_STUB);
        tasksQueue.addNewTask(TASK_STUB);

        // when && then
        assertThat(taskProducer.produce()).isFalse();
    }

    @Test
    public void shouldNotAddNewTaskWhenTasksQueueWasFullAndNotYetHalfSize() throws InterruptedException {
        // given
        tasksQueue.addNewTask(TASK_STUB);
        tasksQueue.addNewTask(TASK_STUB);
        tasksQueue.addNewTask(TASK_STUB);
        tasksQueue.addNewTask(TASK_STUB);
        tasksQueue.readTask();

        // when && then
        assertThat(taskProducer.produce()).isFalse();
    }

    @Test
    public void shouldAddNewTaskWhenTasksQueueWasFullAndReducedToHalfSize() throws InterruptedException {
        // given
        tasksQueue.addNewTask(TASK_STUB);
        tasksQueue.addNewTask(TASK_STUB);
        tasksQueue.addNewTask(TASK_STUB);
        tasksQueue.addNewTask(TASK_STUB);
        tasksQueue.readTask();
        tasksQueue.readTask();

        // when && then
        assertThat(taskProducer.produce()).isTrue();
    }
}