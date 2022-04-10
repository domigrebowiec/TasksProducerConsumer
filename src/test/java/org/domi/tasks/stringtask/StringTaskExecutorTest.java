package org.domi.tasks.stringtask;

import org.domi.tasks.TaskResult;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class StringTaskExecutorTest {

    private final ExpressionCalculator stringTaskExecutor = new ExpressionCalculator();

    @ParameterizedTest
    @CsvSource({
            "200.5 + 100 * 2 / 5 - 10, 230.5",
            "2 * 100 - 80 + 8 / 2,     124.0",
            "90 - 10 / 2,              85.0"})
    public void shouldCalculateExpression(String expression, String result) {
        // when
        TaskResult taskResult = stringTaskExecutor.execute(new StringTask(expression));

        // then
        assertThat(taskResult.getValue()).isEqualTo(result);
    }
}