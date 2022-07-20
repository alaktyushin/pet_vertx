package com.laktyushin.messages;

public class MathMessage {
    private final double x;
    private final double y;
    private final String string;

    public MathMessage(double x, double y, String string) {
        this.x = x;
        this.y = y;
        this.string = string;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "\nX = " + getX() +
                ", Y = " + getY() +
                ", \n" + string;
    }
}
