package org.domi.tasks.stringtask;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.domi.tasks.Task;
import org.domi.tasks.TaskCreator;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Component
public class StringTaskCreator implements TaskCreator {
    private static final int MINIMAL_NUMBER_VALUE = 0;
    private static final int MAXIMAL_NUMBER_VALUE = 1000000;
    private static final String OPERATORS = "+/*-";

    @Override
    public Task create() {
        return new StringTask(randomlyGenerateExpression());
    }

    private String randomlyGenerateExpression() {
        StringBuilder sb = new StringBuilder();
        IntStream.range(0, 5).forEach(value -> appendRandomNumberAndOperand(sb));
        sb.append(RandomUtils.nextFloat(MINIMAL_NUMBER_VALUE, MAXIMAL_NUMBER_VALUE));
        return sb.toString();
    }

    private void appendRandomNumberAndOperand(StringBuilder sb) {
        sb.append(RandomUtils.nextFloat(MINIMAL_NUMBER_VALUE, MAXIMAL_NUMBER_VALUE));
        sb.append(" ");
        sb.append(RandomStringUtils.random(1, OPERATORS));
        sb.append(" ");
    }
}
