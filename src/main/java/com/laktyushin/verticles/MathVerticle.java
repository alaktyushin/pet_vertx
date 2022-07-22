package com.laktyushin.verticles;

import com.laktyushin.Starter;
import com.laktyushin.messages.Codec;
import com.laktyushin.messages.MathMessage;
import com.laktyushin.messages.MathMethods;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
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
        EventBus eb = vertx.eventBus();
        Starter.LOG.info("MathVerticle deployed");
        final MathMethods[] mathMethods = {new MathMethods()};
        final String[] mathMessage = {mathMethods[0].getStringBuilder(x, y).toString()};
        final MathMessage[] message = {new MathMessage(x, y, mathMessage[0])};
        eb.registerDefaultCodec(MathMessage.class, new Codec<>());
        Router router = Router.router(vertx);
        HttpServer server = vertx.createHttpServer();
        server.requestHandler(router).listen(8081);
        eb.request("LoggingVerticle", message[0], res -> {
            if (res.succeeded()) {
                Starter.LOG.info("Received reply: " + res.result().body());
            }
        });
        router
                .get("/math")
                .handler(routingContext -> {
                    String unparsedX = routingContext.queryParam("X").get(0);
                    String unparsedY = routingContext.queryParam("Y").get(0);
                    if ((unparsedX == null) || (unparsedY == null)) {
                        routingContext.fail(400);
                        return;
                    }
                    double x;
                    double y;
                    try {
                        x = Integer.parseInt(unparsedX);
                        y = Integer.parseInt(unparsedY);
                    } catch (NumberFormatException e) {
                        routingContext.fail(401, e);
                        return;
                    }
                    Starter.LOG.info("X = " + x);
                    Starter.LOG.info("Y = " + y);
                    mathMessage[0] = mathMethods[0].getStringBuilder(x, y).toString();
                    message[0] = new MathMessage(x, y, mathMessage[0]);
                    eb.request("LoggingVerticle", message[0], res -> {
                        if (res.succeeded()) {
                            Starter.LOG.info("Received reply: " + res.result().body());
                        }
                    });
                    HttpServerResponse response = routingContext.response();
                    response.putHeader("content-type", "text/plain");
                    response.end("Received GET, with parameters " + x + ", " + y);
                });
    }
}
