package org.domi.tasks.stringtask;

import org.domi.tasks.InvalidTask;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringTaskTest {

    @ParameterizedTest
    @ValueSource(strings = {" ", "abc"})
    public void shouldThrowInvalidTaskExceptionWhenEmptyExpression(String expression) {
        assertThrows(InvalidTask.class, () -> new StringTask(expression));
    }

    @Test
    public void shouldCreateStringTaskWhenCorrectExpression() {
        assertThat(new StringTask("200.5 + 10 * 89 - 4.45 / 8.90")).isNotNull();
    }
}