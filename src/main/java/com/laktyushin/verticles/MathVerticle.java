package com.laktyushin.verticles;

import com.laktyushin.Starter;
import com.laktyushin.messages.MathCodec;
import com.laktyushin.messages.MathMessage;
import com.laktyushin.messages.MathMethods;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

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
        String mathMessage = mathMethods.getStringBuilder(x, y).toString();
        MathMessage message = new MathMessage(x, y, mathMessage);
        vertx.eventBus().registerDefaultCodec(MathMessage.class,
                new MathCodec<>());
        vertx.eventBus().send("LoggingVerticle", message);
    }
}
