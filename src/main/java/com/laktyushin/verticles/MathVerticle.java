package com.laktyushin.verticles;

import com.laktyushin.Starter;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class MathVerticle extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) {
        Starter.LOG.info("MathVerticle deployed");
        vertx.eventBus().send("LoggingVerticle", "MATH-math");
        vertx.close();
    }
}
