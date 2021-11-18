package com.toast.cloud.common.utils;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class StringBuilderEx {

    private StringBuilder builder = new StringBuilder();

    public StringBuilderEx newline() {
        this.builder.append(System.lineSeparator());
        return this;
    }

    public StringBuilderEx append(String format, Object... values) {
        this.builder.append(String.format(format, values));
        return this;
    }

    public String toString() {
        return this.builder.toString();
    }

}

