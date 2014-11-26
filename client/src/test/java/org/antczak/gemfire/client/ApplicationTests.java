package org.antczak.gemfire.client;

import com.gemstone.gemfire.cache.Region;
import org.antczak.gemfire.domain.Customer;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ApplicationTests {

    Logger log = Logger.getLogger(ApplicationTests.class);

    @Resource(name = "CustomerPartitioned")
    Region<Integer, Customer> customerPartitionedRegion;
    @Resource(name = "CustomerReplicated")
    Region<Integer, Customer> customerReplicatedRegion;
    @Resource(name = "CustomerLocal")
    Region<Integer, Customer> customerLocalRegion;

    @Before
    public void regionsAvailable() {
        log.debug("regionsAvailable()");
        assertNotNull(customerPartitionedRegion);
        assertNotNull(customerReplicatedRegion);
        assertNotNull(customerLocalRegion);
    }

    @Test
    public void addCustomers() {
        log.debug("addCustomers()");
        for (int customerId = 100; customerId < 110; customerId++) {
            Customer customer = new Customer(customerId, "Will", "Smith The " + customerId);
            customerPartitionedRegion.put(customerId, customer);
            customerReplicatedRegion.put(customerId, customer);
            customerLocalRegion.put(customerId, customer);
        }

        assertNotNull(customerPartitionedRegion.get(100));
        assertNotNull(customerReplicatedRegion.get(100));
        assertNotNull(customerLocalRegion.get(100));

        // Region type = PROXY, no local copy of data
        assertEquals(0, customerPartitionedRegion.size());
        assertEquals(10, customerReplicatedRegion.size());
        assertEquals(10, customerLocalRegion.size());
    }

}
