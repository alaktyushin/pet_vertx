package com.laktyushin;

import com.laktyushin.verticles.MainVerticle;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final public class Runner {
    private static final Logger LOG = LoggerFactory.getLogger(Runner.class);

    public static void main(String[] args) {

        LOG.info("Starting Runner");
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> LOG.error("Uncaught exception {} on thread {} ", e, t.getName()));
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new MainVerticle());
    }
}
