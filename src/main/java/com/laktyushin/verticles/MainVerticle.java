package com.laktyushin.verticles;

import com.laktyushin.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainVerticle extends AbstractVerticle {

    private static final Logger LOG = LoggerFactory.getLogger(Runner.class);

    @Override
    public void start(Promise<Void> startPromise) {
        vertx.createHttpServer().requestHandler(req -> req.response()
                .putHeader("content-type", "text/plain")
                .end("Hello from Vert.x!")).listen(8080, http -> {
            if (http.succeeded()) {
                startPromise.complete();
                LOG.info("HTTP server started on port 8080");
            } else {
                startPromise.fail(http.cause());
            }
        });
    }

    @Override
    public void stop() {
        LOG.info("Shutting down application");
    }
}

