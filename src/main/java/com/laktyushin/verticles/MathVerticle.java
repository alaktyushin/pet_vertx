package com.laktyushin.verticles;

import com.laktyushin.Starter;
import com.laktyushin.messages.MathMethods;
import com.laktyushin.messages.MathResults;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

import static com.laktyushin.Starter.LOG;

public class MathVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) {
        Starter.LOG.info("MathVerticle deployed");
        final double[] numbers = new double[2];
        vertx.eventBus().consumer("MathVerticle", message -> {
            LOG.info("MathVerticle received X: " + message.body());
            numbers[0] = (double) message.body();
        });
        vertx.eventBus().consumer("MathVerticle", message -> {
            LOG.info("MathVerticle received Y: " + message.body());
            numbers[1] = (double) message.body();
        });
        //MathResults mathResults = new MathResults();
        MathMethods mathResults = new MathMethods();
        String maths = mathResults.getStringBuilder(numbers[0], numbers[1]).toString();
        vertx.eventBus().send("LoggingVerticle", maths);
        LOG.info("The job is done. MathVerticle shuts down.");
        vertx.close();
    }
}
