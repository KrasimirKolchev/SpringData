package com.cardealerxml.persistance.models.dtos.rootDtos;

import com.cardealerxml.persistance.models.dtos.supplierDtos.LocalSupplierViewDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class LocalSuppliersRootDTO {
    @XmlElement(name = "supplier")
    private List<LocalSupplierViewDTO> localSuppliers;

    public LocalSuppliersRootDTO() {
    }

    public List<LocalSupplierViewDTO> getLocalSuppliers() {
        return localSuppliers;
    }

    public void setLocalSupliers(List<LocalSupplierViewDTO> localSuppliers) {
        this.localSuppliers = localSuppliers;
    }
}
