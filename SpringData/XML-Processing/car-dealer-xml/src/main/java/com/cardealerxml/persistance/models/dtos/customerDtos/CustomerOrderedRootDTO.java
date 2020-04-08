package com.cardealerxml.persistance.models.dtos.customerDtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerOrderedRootDTO {
    @XmlElement(name = "customer")
    private List<CustomerOrderedViewDTO> customers;

    public CustomerOrderedRootDTO() {
    }

    public List<CustomerOrderedViewDTO> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerOrderedViewDTO> customers) {
        this.customers = customers;
    }
}
