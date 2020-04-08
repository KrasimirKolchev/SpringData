package com.cardealer.persistance.models.dtos.salesDtos;

import com.cardealer.persistance.models.dtos.carDtos.CarViewDTO;
import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class SalesDiscountsViewDto {
    @Expose
    private CarViewDTO car;
    @Expose
    private String customerName;
    @Expose
    private double Discount;
    @Expose
    private BigDecimal price;
    @Expose
    private BigDecimal priceWithDiscount;

    public SalesDiscountsViewDto() {
    }

    public CarViewDTO getCar() {
        return car;
    }

    public void setCar(CarViewDTO car) {
        this.car = car;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getDiscount() {
        return Discount;
    }

    public void setDiscount(double discount) {
        Discount = discount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPriceWithDiscount() {
        return priceWithDiscount;
    }

    public void setPriceWithDiscount(BigDecimal priceWithDiscount) {
        this.priceWithDiscount = priceWithDiscount;
    }
}
