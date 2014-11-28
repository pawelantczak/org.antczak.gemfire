package org.antczak.gemfire.client.dao;

import org.antczak.gemfire.domain.Customer;
import org.springframework.data.gemfire.repository.GemfireRepository;

import java.util.List;

/**
 * Created by a516438 on 2014-11-26.
 */

public interface CustomerRepository extends GemfireRepository<Customer, String> {

    Customer findByLastName(String lastName);

    Customer findByCustomerNumber(int customerNumber);

    List<Customer> findByCustomerNumberGreaterThan(int customerNumber);

}
