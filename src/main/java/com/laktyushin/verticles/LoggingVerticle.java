package com.laktyushin.verticles;

import com.laktyushin.Starter;
import com.laktyushin.messages.MathMessage;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;

import static com.laktyushin.Starter.LOG;

public class LoggingVerticle extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) {
        Starter.LOG.info("LoggingVerticle deployed");
        vertx.eventBus().consumer("LoggingVerticle", message -> {
            MathMessage msg = (MathMessage) message.body();
            LOG.info("I have received a message: " + msg);
            HttpServer server = vertx.createHttpServer();
            server.requestHandler(request -> {
                HttpServerResponse response = request.response();
                response.putHeader("content-type", "text/plain");
                response.end(message.body().toString());
            });
            server.listen(8080);
        });
    }
}
