package com.cardealerxml.persistance.models.dtos.rootDtos;

import com.cardealerxml.persistance.models.dtos.partDtos.PartCreateDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "parts")
@XmlAccessorType(XmlAccessType.FIELD)
public class PartsRootDTO {
    @XmlElement(name = "part")
    private List<PartCreateDTO> parts;

    public PartsRootDTO() {
    }

    public List<PartCreateDTO> getParts() {
        return parts;
    }

    public void setParts(List<PartCreateDTO> parts) {
        this.parts = parts;
    }
}
