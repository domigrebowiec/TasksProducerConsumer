package org.domi.tasks.stringtask;

import org.domi.tasks.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class StringTaskGeneratorTest {

    private StringTaskGenerator expressionGenerator;

    @BeforeEach
    public void setup() {
        expressionGenerator = new StringTaskGenerator();
    }

    @Test
    public void shouldBeAbleToCreateStringTaskFromRandomlyGeneratedExpression() {
        // when
        Task task = expressionGenerator.generate();

        // then
        assertThat(task).isNotNull();
        assertThat(task.getValue()).isNotNull();
    }
}