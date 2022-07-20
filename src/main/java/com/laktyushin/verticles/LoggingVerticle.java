package com.laktyushin.verticles;

import com.laktyushin.Starter;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

import static com.laktyushin.Starter.LOG;
public class LoggingVerticle extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) {
        Starter.LOG.info("LoggingVerticle deployed");
        vertx.eventBus().consumer("LoggingVerticle", message ->
                LOG.info("I have received a message: " + message.body()));
        vertx.close();
    }
}
