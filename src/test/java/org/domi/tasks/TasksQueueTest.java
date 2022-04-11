package org.domi.tasks;

import lombok.Data;
import org.domi.configuration.TaskConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TasksQueueTest {
    private final static String EXPRESSION = "200 + 100";
    private final static Task task = new TaskStub(EXPRESSION);
    private TasksQueue tasksQueue;
    private TaskConfiguration taskConfiguration;

    @BeforeEach
    public void setup() {
        taskConfiguration = new TaskConfiguration();
        taskConfiguration.setMaxQueueSize(10);
        tasksQueue = new TasksQueue(taskConfiguration);
    }

    @Test
    public void shouldBeAbleToAddTaskToEmptyQueue() {
        // when && then
        assertThat(tasksQueue.addNewTask(task)).isTrue();
    }

    @Test
    public void shouldBeAbleToAddTaskToNotEmptyQueue() {
        // given
        tasksQueue.addNewTask(task);
        tasksQueue.addNewTask(task);

        // when && then
        assertThat(tasksQueue.addNewTask(task)).isTrue();
    }

    @Test
    public void shouldNotBeAbleToAddTaskToFullQueue() {
        // given
        taskConfiguration.setMaxQueueSize(2);
        TasksQueue tasksQueue = new TasksQueue(taskConfiguration);
        tasksQueue.addNewTask(task);
        tasksQueue.addNewTask(task);

        // when
        boolean taskAdded = tasksQueue.addNewTask(task);

        // then
        assertThat(taskAdded).isFalse();
    }

    @Test
    public void shouldHasNoCapacityWhenQueueWasFullAndSizeIsNotReducedToHalf() throws InterruptedException {
        // given
        taskConfiguration.setMaxQueueSize(6);
        TasksQueue tasksQueue = new TasksQueue(taskConfiguration);
        tasksQueue.addNewTask(task);
        tasksQueue.addNewTask(task);
        tasksQueue.addNewTask(task);
        tasksQueue.addNewTask(task);
        tasksQueue.addNewTask(task);
        tasksQueue.addNewTask(task);

        tasksQueue.readTask();
        tasksQueue.readTask();

        // when && then
        assertThat(tasksQueue.hasCapacity()).isFalse();
    }

    @Test
    public void shouldBeAbleToAddTaskToQueueWhichWasFullAndSizeIsReducedToHalf() throws InterruptedException {
        // given
        taskConfiguration.setMaxQueueSize(5);
        TasksQueue tasksQueue = new TasksQueue(taskConfiguration);
        tasksQueue.addNewTask(task);
        tasksQueue.addNewTask(task);
        tasksQueue.addNewTask(task);
        tasksQueue.addNewTask(task);
        tasksQueue.addNewTask(task);
        tasksQueue.addNewTask(task);

        tasksQueue.readTask();
        tasksQueue.readTask();
        tasksQueue.readTask();

        // when && then
        assertThat(tasksQueue.addNewTask(task)).isTrue();
    }

    @Test
    public void shouldReturnNullWhenEmptyQueue() throws InterruptedException {
        // when && then
        assertThat(tasksQueue.readTask()).isNull();
    }

    @Test
    public void shouldReadNonNullTaskWhenNonEmptyQueue() throws InterruptedException {
        // given
        tasksQueue.addNewTask(task);

        // when && then
        Task actualTask = tasksQueue.readTask();
        assertThat(actualTask).isNotNull();
        assertThat(actualTask).isEqualTo(task);
    }

    @Test
    public void shouldQueueBeFIFO() throws InterruptedException {
        // given
        Task task1 = new TaskStub("100 + 100");
        Task task2 = new TaskStub("200 / 5");
        tasksQueue.addNewTask(task1);
        tasksQueue.addNewTask(task2);

        // when && then
        assertThat(tasksQueue.readTask()).isEqualTo(task1);
        assertThat(tasksQueue.readTask()).isEqualTo(task2);
    }
}