package org.domi.tasks;

import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TasksQueueTest {
    private final static String EXPRESSION = "200 + 100";
    private final static Task task = new TestTask(EXPRESSION);
    private TasksQueue tasksQueue;

    @BeforeEach
    public void setup() {
        tasksQueue = new TasksQueue(10);
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
        TasksQueue tasksQueue = new TasksQueue(2);
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
        TasksQueue tasksQueue = new TasksQueue(6);
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
        TasksQueue tasksQueue = new TasksQueue(5);
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
        Task task1 = new TestTask("100 + 100");
        Task task2 = new TestTask("200 / 5");
        tasksQueue.addNewTask(task1);
        tasksQueue.addNewTask(task2);

        // when && then
        assertThat(tasksQueue.readTask()).isEqualTo(task1);
        assertThat(tasksQueue.readTask()).isEqualTo(task2);
    }

    @Data
    private static class TestTask implements Task {
        private final String value;
    }
}