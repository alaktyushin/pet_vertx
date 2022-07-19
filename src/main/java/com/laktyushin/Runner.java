package com.laktyushin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final public class Runner {
    private static final Logger LOG = LoggerFactory.getLogger(Runner.class);

    public static void main(String[] args) {
        LOG.info("Starting Runner");
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> LOG.error("Uncaught exception {} on thread {} ", e, t.getName()));
        //AnnotationConfigApplicationContext springContext = new AnnotationConfigApplicationContext();
        //LOG.info("active profiles are: {}, ", Arrays.toString(springContext.getEnvironment().getActiveProfiles()));
        //springContext.register(ValaRunnerConfiguration.class);
        //springContext.refresh();
    }
}
