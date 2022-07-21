package com.laktyushin.verticles;

import com.laktyushin.Starter;
import com.laktyushin.messages.Codec;
import com.laktyushin.messages.MathMessage;
import com.laktyushin.messages.MathMethods;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

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
        vertx.eventBus().registerDefaultCodec(MathMessage.class, new Codec<>());
        vertx.eventBus().send("LoggingVerticle", message);

        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);

        router.route().method(HttpMethod.POST).path("/exp*").handler(ctx -> {

            HttpServerResponse response = ctx.response();
            response.putHeader("content-type", "text/plain");
            response.end(String.valueOf(ctx.request()));
        });

        router.route().method(HttpMethod.GET).path("/exp*").handler(ctx -> {
            HttpServerResponse response = ctx.response();
            response.putHeader("content-type", "text/plain");
            response.end("GET");
        });

        router.route()
                .path("/math")
                .handler(ctx -> {
                    HttpServerResponse response = ctx.response().setChunked(true);
                    response.putHeader("content-type", "text/plain");
                    response.end("X = "
                            .concat(String.valueOf(x))
                            .concat("    Y = ")
                            .concat(String.valueOf(y))
                            .concat(System.lineSeparator())
                            .concat(System.lineSeparator())
                            .concat(mathMessage)
                    );
                });

        server.requestHandler(router).listen(8081);
    }
}
