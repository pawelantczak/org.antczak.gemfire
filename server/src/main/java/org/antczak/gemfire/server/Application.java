package org.antczak.gemfire.server;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by a516438 on 2014-11-25.
 */

@SpringBootApplication
//@ImportResource("/spring-data-gemfire-cache.xml")
//@EnableGemfireRepositories
public class Application {

    Logger log = Logger.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
