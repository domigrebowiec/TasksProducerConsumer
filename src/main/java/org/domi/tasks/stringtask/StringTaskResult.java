package org.domi.tasks.stringtask;

import org.domi.tasks.TaskResult;

class StringTaskResult implements TaskResult {
    private final Float value;

    StringTaskResult(Float value) {
        this.value = value;
    }

    public String getValue() {
        return String.valueOf(value);
    }
}
