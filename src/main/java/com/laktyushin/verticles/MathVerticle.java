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
        Starter.LOG.info("MathVerticle deployed");
        final double[] num = {x, y};
        final EventBus eb = vertx.eventBus();
        final Router router = Router.router(vertx);
        final HttpServer server = vertx.createHttpServer();
        eb.registerDefaultCodec(MathMessage.class, new Codec<>());
        server.requestHandler(router).listen(8081);
        router
                .get("/math")
                .handler(routingContext -> {
                    String unparsedX = routingContext.queryParam("X").get(0);
                    String unparsedY = routingContext.queryParam("Y").get(0);
                    if ((unparsedX == null) || (unparsedY == null)) {
                        routingContext.fail(400);
                        return;
                    }
                    try {
                        num[0] = Double.parseDouble(unparsedX);
                        num[1] = Double.parseDouble(unparsedY);
                    } catch (NumberFormatException e) {
                        routingContext.fail(400, e);
                        return;
                    }

                    HttpServerResponse response = routingContext.response();
                    response.putHeader("content-type", "text/plain");
                    response.end("Received GET, with parameters " + num[0] + ", " + num[1]);
                    eb.send("LoggingVerticle",
                            new MathMessage(num[0], num[1], new MathMethods().getStringBuilder(num[0], num[1]).toString()));
                });
        eb.send("LoggingVerticle",
                new MathMessage(num[0], num[1], new MathMethods().getStringBuilder(num[0], num[1]).toString()));
    }
}
