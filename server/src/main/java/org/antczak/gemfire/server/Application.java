package org.antczak.gemfire.server;

import com.gemstone.gemfire.cache.server.CacheServer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

/**
 * Created by a516438 on 2014-11-25.
 */

@SpringBootApplication
@ImportResource("/spring-data-gemfire-server.xml")
@EnableGemfireRepositories
public class Application {

    Logger log = Logger.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    public void showPort(CacheServer cacheServer) {
        log.info("Port number: " + cacheServer.getPort());
    }
}
