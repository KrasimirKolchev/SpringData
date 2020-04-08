package com.cardealerxml.persistance.models.dtos.rootDtos;

import com.cardealerxml.persistance.models.dtos.supplierDtos.SupplierCreateDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierRootDTO {
    @XmlElement(name = "supplier")
    private List<SupplierCreateDTO> suppliers;

    public SupplierRootDTO() {
    }

    public List<SupplierCreateDTO> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<SupplierCreateDTO> suppliers) {
        this.suppliers = suppliers;
    }
}
