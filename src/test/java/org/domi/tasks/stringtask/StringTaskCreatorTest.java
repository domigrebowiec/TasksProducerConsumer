package org.domi.tasks.stringtask;

import org.domi.tasks.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class StringTaskCreatorTest {

    private StringTaskCreator expressionGenerator;

    @BeforeEach
    public void setup() {
        expressionGenerator = new StringTaskCreator();
    }

    @Test
    public void shouldBeAbleToCreateStringTaskFromRandomlyGeneratedExpression() {
        // when
        Task task = expressionGenerator.create();

        // then
        assertThat(task).isNotNull();
        assertThat(task.getValue()).isNotNull();
    }
}