package com.laktyushin.io;

import java.util.Scanner;

public class ConsoleInput implements Input {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String askStr(String question) {
        System.out.print(question);
        return scanner.nextLine();
    }

    @Override
    public double askNumber(String question) {
        return Double.parseDouble(askStr(question));
    }
}
