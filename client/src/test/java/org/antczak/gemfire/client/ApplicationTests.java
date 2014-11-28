package org.antczak.gemfire.client;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.query.Query;
import com.gemstone.gemfire.cache.query.QueryService;
import com.gemstone.gemfire.cache.query.SelectResults;
import org.antczak.gemfire.client.dao.CustomerRepository;
import org.antczak.gemfire.domain.Customer;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.gemfire.GemfireTemplate;
import org.springframework.data.gemfire.repository.Wrapper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApplicationTests {

    Logger log = Logger.getLogger(ApplicationTests.class);

    @Resource(name = "Customer")
    Region<Integer, Customer> customerRegion;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    GemfireTemplate customerTemplate;

    @Before
    public void regionsAvailable() {
        assertNotNull(customerRegion);
    }

    @Test
    public void AaddCustomersWithRegion() {
        for (int customerId = 100; customerId < 110; customerId++) {
            Customer customer = new Customer(customerId, "Will", "Smith The " + customerId);
            customerRegion.put(customerId, customer);
        }
    }

    @Test
    public void AaddCustomersWithTemplate() {
        for (int customerId = 110; customerId < 120; customerId++) {
            Customer customer = new Customer(customerId, "Will", "Smith The " + customerId);
            customerTemplate.put(customerId, customer);
        }
    }

    @Test
    public void AaddCustomersWithRepository() {
        for (int customerId = 120; customerId < 130; customerId++) {
            Customer customer = new Customer(customerId, "Will", "Smith The " + customerId);
            customerRepository.save(new Wrapper(customer, customerId));
        }
    }

    @Test
    public void BcheckCustomerWithRegion() {
        assertNotNull(customerRegion.get(100));
    }

    @Test
    public void BcheckCustomerWithTemplate() {
        assertNotNull(customerTemplate.get(100));
    }

    @Test
    public void BcheckCustomerWithRepository() {
        assertNotNull(customerRepository.findByCustomerNumber(100));
    }

    @Test
    public void CcheckQueryWithRegion() throws Exception {
        QueryService queryService = customerRegion.getRegionService().getQueryService();
        Query query = queryService.newQuery("SELECT * from /Customer c");
        SelectResults<Customer> results = (SelectResults<Customer>) query.execute();

        assertEquals(30, results.size());
    }

    @Test
    public void CcheckQueryWithTemplate() {
        SelectResults<Customer> results = customerTemplate.query("SELECT * from /Customer c");

        assertEquals(30, results.size());
    }

    @Test
    public void CcheckQueryWithRepository() {
        Iterable<Customer> customers = customerRepository.findAll();

        assertEquals(30, ((Collection<Customer>) customers).size());
    }

    @Test
    public void DcheckOQLWithRegion() throws Exception {
        QueryService queryService = customerRegion.getRegionService().getQueryService();
        Query query = queryService.newQuery("SELECT * from /Customer c WHERE customerNumber > $1");
        Object[] params = new Object[1];
        params[0] = 114;
        SelectResults<Customer> results = (SelectResults<Customer>) query.execute(params);

        assertEquals(15, results.size());
    }

    @Test
    public void DcheckOQLWithTemplate() {
        Object[] params = new Object[1];
        params[0] = new Integer(114);
        SelectResults<Customer> results = customerTemplate.find(
            "SELECT * from /Customer c WHERE customerNumber > $1", params);

        assertEquals(15, results.size());
    }

    @Test
    public void DcheckOQLWithRepository() {
        Iterable<Customer> customers = customerRepository.findByCustomerNumberGreaterThan(114);

        assertEquals(15, ((Collection<Customer>) customers).size());
    }
}
