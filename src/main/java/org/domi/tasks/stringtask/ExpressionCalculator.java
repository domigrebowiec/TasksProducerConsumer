package org.domi.tasks.stringtask;

import org.domi.tasks.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

@Component
public class ExpressionCalculator implements TaskExecutor {
    private final Pattern pattern = Pattern.compile("[+/*-]");

    @Override
    public TaskResult execute(Task task) {
        Stack<Float> stack = new Stack<>();
        for (String s : infixToPostfix(task.getValue())) {
            if (isOperand(s)) {
                Float result = calculate(s, stack.pop(), stack.pop());
                stack.push(result);
            } else {
                stack.push(Float.parseFloat(s));
            }
        }
        return new StringTaskResult(stack.pop());
    }

    private List<String> infixToPostfix(String expression) {
        Stack<String> stack = new Stack<>();
        List<String> result = new ArrayList<>();
        Arrays.stream(expression.split(" ")).forEach(s -> {
            if (isOperand(s)) {
                while (!stack.isEmpty() && hasHigherPrecedence(stack.peek(), s)) {
                    result.add(stack.pop());
                }
                stack.push(s);
            } else {
                result.add(s);
            }
        });
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        return result;
    }

    private boolean hasHigherPrecedence(String operator1, String operator2) {
        return getWeight(operator1) >= getWeight(operator2);
    }

    private int getWeight(String operator) {
        switch (operator) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            default:
                return -1;
        }
    }

    private boolean isOperand(String s) {
        return pattern.matcher(s).matches();
    }

    private Float calculate(String operator, Float number1, Float number2) {
        switch (operator) {
            case "+":
                return number1 + number2;
            case "-":
                return number2 - number1;
            case "*":
                return number1 * number2;
            case "/":
                return number2 / number1;
            default:
                throw new InvalidTask("Unknown operator " + operator);
        }
    }
}
