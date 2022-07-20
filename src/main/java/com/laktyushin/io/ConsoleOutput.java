package com.laktyushin.io;

public class ConsoleOutput implements Output {
    @Override
    public void println(Object object) {
        System.out.println(object);
    }
}
