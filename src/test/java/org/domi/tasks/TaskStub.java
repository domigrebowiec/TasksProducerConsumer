package org.domi.tasks;

public class TaskStub implements Task {
    private final String value;
    public TaskStub(String value) {
        this.value = value;
    }
    @Override
    public String getValue() {
        return this.value;
    }
}
