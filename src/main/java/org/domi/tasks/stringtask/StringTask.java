package org.domi.tasks.stringtask;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.domi.tasks.InvalidTask;
import org.domi.tasks.Task;

import java.util.Objects;
import java.util.regex.Pattern;

@Slf4j
final class StringTask implements Task {
    private static final Pattern pattern = Pattern.compile("^[\\d\\s+/*.\\-]*$");
    private final String expression;

    StringTask(String expression) {
        if (StringUtils.isAllBlank(expression)) {
            throw new InvalidTask("String task expression cannot be empty");
        }
        if (!pattern.matcher(expression).matches()) {
            throw new InvalidTask("String task expression can only contain numbers or operands + - * /");
        }
        this.expression = expression;
    }

    @Override
    public String getValue() {
        return expression;
    }

    @Override
    public String toString() {
        return "StringTask { " +
                "expression='" + expression + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringTask that = (StringTask) o;
        return Objects.equals(expression, that.expression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expression);
    }
}
