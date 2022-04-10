package org.domi.tasks;

public class InvalidTask extends RuntimeException {
    public InvalidTask(String message) {
        super(message);
    }
}
