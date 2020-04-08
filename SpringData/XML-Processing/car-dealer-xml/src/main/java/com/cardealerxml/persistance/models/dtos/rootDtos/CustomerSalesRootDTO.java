package com.cardealerxml.persistance.models.dtos.rootDtos;

import com.cardealerxml.persistance.models.dtos.customerDtos.CustomerSalesViewDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerSalesRootDTO {
    @XmlElement(name = "customer")
    private List<CustomerSalesViewDTO> customers;

    public CustomerSalesRootDTO() {
    }

    public List<CustomerSalesViewDTO> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerSalesViewDTO> customers) {
        this.customers = customers;
    }
}
