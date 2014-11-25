package org.antczak.gemfire.client;

import com.gemstone.gemfire.cache.Region;
import org.antczak.gemfire.domain.Order;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.Random;

/**
 * Created by a516438 on 2014-11-25.
 */

@SpringBootApplication
@ImportResource("/spring-data-gemfire-client.xml")
@EnableGemfireRepositories
public class Application {

    Logger log = Logger.getLogger(Application.class);

    static ApplicationContext applicationContext;

    @Resource(name = "Order")
    Region<Long, Order> region;

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    public void addData() {
        Random rand = new Random(new Date().getTime());
        for (long orderId = 1; orderId <= 100; orderId++) {
            Order order = new Order(orderId, (new Long(rand.nextInt(100) + 1)));
            region.put(orderId, order);
        }
    }


}
