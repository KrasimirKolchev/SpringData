package com.cardealerxml.persistance.models.dtos.carDtos;

import com.cardealerxml.persistance.models.dtos.partDtos.PartViewRootDTO;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "car")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarPartsViewDto {
    @XmlAttribute(name = "make")
    private String make;
    @XmlAttribute(name = "model")
    private String model;
    @XmlAttribute(name = "travelled-distance")
    private long travelledDistance;
    @XmlElement(name = "parts")
    private PartViewRootDTO parts;

    public CarPartsViewDto() {
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public long getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(long travelledDistance) {
        this.travelledDistance = travelledDistance;
    }

    public PartViewRootDTO getParts() {
        return parts;
    }

    public void setParts(PartViewRootDTO parts) {
        this.parts = parts;
    }
}
