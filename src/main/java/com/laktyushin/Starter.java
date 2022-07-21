package com.laktyushin;

import com.laktyushin.io.*;
import com.laktyushin.verticles.LoggingVerticle;
import com.laktyushin.verticles.MathVerticle;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Starter {
    public Vertx vertx;
    public static final Logger LOG = LoggerFactory.getLogger(Starter.class);

    public static void main(String[] args) {
        Starter starter = new Starter();
        starter.vertx = Vertx.vertx();
        Input input = new ValidateInput(new ConsoleOutput(), new ConsoleInput());
        final double x = input.askNumber("Enter X: ");
        final double y = input.askNumber("Enter Y: ");
        starter.vertx.deployVerticle(new LoggingVerticle());
        starter.vertx.deployVerticle(new MathVerticle(x, y));
    }
}
