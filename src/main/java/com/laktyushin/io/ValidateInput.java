package com.laktyushin.io;

public class ValidateInput implements Input {
    private final Output out;
    private final Input input;

    public ValidateInput(Output output, Input input) {
        this.out = output;
        this.input = input;
    }

    @Override
    public String askStr(String question) {
        return input.askStr(question);
    }

    @Override
    public double askNumber(String question) {
        boolean invalid = true;
        double result = -1;
        do {
            try {
                result = input.askNumber(question);
                invalid = false;
            } catch (NumberFormatException nfe) {
                System.out.println("Please enter valid data again ");
            }
        } while (invalid);
        return result;
    }
}
