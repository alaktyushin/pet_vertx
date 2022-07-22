package com.laktyushin.verticles;

import com.laktyushin.Starter;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

import java.util.concurrent.atomic.AtomicReference;

import static com.laktyushin.Starter.LOG;

public class LoggingVerticle extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) {
        Starter.LOG.info("LoggingVerticle deployed");
        final Router router = Router.router(vertx);
        final HttpServer server = vertx.createHttpServer();
        final AtomicReference<Object> body = new AtomicReference<>();
        server.requestHandler(router).listen(8080);
        vertx.eventBus().consumer("LoggingVerticle", message -> {
                    LOG.info("I have received a message.");
                    message.reply("Message delivered.");
                    body.set(message.body());
                }
        );
        router.route().handler(ctx -> {
            HttpServerResponse response = ctx.response();
            response.putHeader("content-type", "text/plain");
            response.end(body.get().toString());
        });
    }
}
