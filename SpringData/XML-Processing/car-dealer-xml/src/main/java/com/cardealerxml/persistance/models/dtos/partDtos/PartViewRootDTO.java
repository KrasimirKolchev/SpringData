package com.cardealerxml.persistance.models.dtos.partDtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "parts")
@XmlAccessorType(XmlAccessType.FIELD)
public class PartViewRootDTO {
    @XmlElement(name = "part")
    private List<PartViewDTO> parts;

    public PartViewRootDTO() {
    }

    public List<PartViewDTO> getParts() {
        return parts;
    }

    public void setParts(List<PartViewDTO> parts) {
        this.parts = parts;
    }
}
