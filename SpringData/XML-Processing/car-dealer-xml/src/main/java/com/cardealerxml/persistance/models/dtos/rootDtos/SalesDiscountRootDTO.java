package com.cardealerxml.persistance.models.dtos.rootDtos;

import com.cardealerxml.persistance.models.dtos.salesDtos.SalesDiscountsViewDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "sales")
@XmlAccessorType(XmlAccessType.FIELD)
public class SalesDiscountRootDTO {
    @XmlElement(name = "sale")
    private List<SalesDiscountsViewDto> sales;

    public SalesDiscountRootDTO() {
    }

    public List<SalesDiscountsViewDto> getSales() {
        return sales;
    }

    public void setSales(List<SalesDiscountsViewDto> sales) {
        this.sales = sales;
    }
}
