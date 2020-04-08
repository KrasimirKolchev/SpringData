package com.cardealerxml.persistance.models.dtos.rootDtos;

import com.cardealerxml.persistance.models.dtos.customerDtos.CustomerCreateDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerRootDTO {
    @XmlElement(name = "customer")
    private List<CustomerCreateDTO> customers;

    public CustomerRootDTO() {
    }

    public List<CustomerCreateDTO> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerCreateDTO> customers) {
        this.customers = customers;
    }
}
