package org.antczak.gemfire.domain;

import org.springframework.data.gemfire.mapping.Region;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * @author Oliver Gierke
 * @author David Turanski
 */

@Region
public class Order implements Serializable {

    private static final long serialVersionUID = -3779061453639083037L;

    private Long id;
    private Integer total;
    private Long customerId;
    private Set<String> lineItems = new HashSet<String>();

    public Order(Long id, Long customerId) {
        Assert.notNull(customerId);

        this.customerId = customerId;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Set<String> getLineItems() {
        return lineItems;
    }

    public void setLineItems(Set<String> lineItems) {
        this.lineItems = lineItems;
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }
}
