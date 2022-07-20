package com.laktyushin.messages;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MathMethods {

    private final StringBuilder stringBuilder = new StringBuilder();

    private Method[] getMethods() {
        return Math.class.getMethods();
    }

    public StringBuilder getStringBuilder (double x, double y) {
        StringBuilder result = stringBuilder.append(System.lineSeparator())
                .append("X = " + x + "\t Y = " + y).append(System.lineSeparator())
                .append("Name of method: +").append(". Result: ").append(x + y).append(System.lineSeparator())
                .append("Name of method: -").append(". Result: ").append(x - y).append(System.lineSeparator())
                .append("Name of method: *").append(". Result: ").append(x * y).append(System.lineSeparator())
                .append("Name of method: /").append(". Result: ").append(x / y).append(System.lineSeparator());
        Method[] methods1 = getMethods();
        try {
            for (Method method : methods1) {
                result.append("Name of method: " + method.toString());
                if (method.getParameters().length == 1) {
                    try {
                        result.append(". Result: " + method.invoke(1, x)).append(System.lineSeparator());
                    } catch (IllegalArgumentException e) {
                        result.append(". Argument type mismatch").append(System.lineSeparator());
                    }
                }
                if (method.getParameters().length == 2) {
                    try {
                        result.append(". Result: " + method.invoke(1, x, y)).append(System.lineSeparator());
                    } catch (IllegalArgumentException e) {
                        result.append(". Argument type mismatch").append(System.lineSeparator());
                    }
                }
                if (method.getParameters().length > 2 || method.getParameters().length < 1) {
                    result.append(". Not applicable").append(System.lineSeparator());
                }
            }
        } catch (InvocationTargetException | IllegalAccessException e) {
            result.append(". That's all!").append(System.lineSeparator());
        }

        return result;
    }
}
