package com.laktyushin.verticles;

import com.laktyushin.Starter;
import com.laktyushin.messages.MathMethods;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

import static com.laktyushin.Starter.LOG;

public class MathVerticle extends AbstractVerticle {

    private final double x;
    private final double y;
    public MathVerticle(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void start(Promise<Void> startPromise) {
        Starter.LOG.info("MathVerticle deployed");
        MathMethods mathMethods = new MathMethods();
        String maths = mathMethods.getStringBuilder(x, y).toString();
        vertx.eventBus().send("LoggingVerticle", maths);
    }
}
