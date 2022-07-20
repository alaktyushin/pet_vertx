package com.laktyushin.messages;

import static java.lang.Math.abs;

public class MathResults {
    private final StringBuilder stringBuilder = new StringBuilder();

    public StringBuilder getStringBuilder (double x, double y) {
        return stringBuilder.append(System.lineSeparator()).append("X = ")
                .append(x).append("\t Y = ").append(y).append(System.lineSeparator())
                .append("Result of \t x + y is ").append(x + y).append(System.lineSeparator())
                .append("Result of \t x - y is ").append(x - y).append(System.lineSeparator())
                .append("Result of \t x * y is ").append(x * y).append(System.lineSeparator())
                .append("Result of \t x / y is ").append(x / y).append(System.lineSeparator())
                .append("Result of \t abs(x) is ").append(abs(x)).append(System.lineSeparator())
                .append("Result of \t abs(y) is ").append(abs(y)).append(System.lineSeparator());
    }
}
