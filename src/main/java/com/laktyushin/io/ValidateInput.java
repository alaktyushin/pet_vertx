package com.laktyushin.io;

public class ValidateInput implements Input {
    private final Output out;
    private final Input in;

    public ValidateInput(Output out, Input input) {
        this.out = out;
        this.in = input;
    }

    @Override
    public String askStr(String question) {
        return in.askStr(question);
    }

    @Override
    public double askNumber(String question) {
        boolean invalid = true;
        double rsl = -1;
        do {
            try {
                rsl = in.askNumber(question);
                invalid = false;
            } catch (NumberFormatException nfe) {
                System.out.println("Please enter valid data again ");
            }
        } while (invalid);
        return rsl;
    }
}
