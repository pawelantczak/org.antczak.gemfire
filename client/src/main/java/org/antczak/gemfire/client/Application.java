package org.antczak.gemfire.client;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

/**
 * Created by a516438 on 2014-11-25.
 */

@SpringBootApplication
@ImportResource("/spring-data-gemfire-client.xml")
@EnableGemfireRepositories
public class Application {

    Logger log = Logger.getLogger(Application.class);

    static ApplicationContext applicationContext;

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(Application.class, args);
    }

   /* @PostConstruct
    public void addData() {
        Random rand = new Random(new Date().getTime());
        for (int bookId = 1; bookId <= 100; bookId++) {
            Book book =
                new Book(bookId, "a book", 22, 2014, "Dr Who", "The Book");
            region.put(bookId, book);
        }
    }*/


}
