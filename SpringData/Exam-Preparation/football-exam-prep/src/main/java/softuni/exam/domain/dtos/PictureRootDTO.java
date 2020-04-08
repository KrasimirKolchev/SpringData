package softuni.exam.domain.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "pictures")
@XmlAccessorType(XmlAccessType.FIELD)
public class PictureRootDTO {
    @XmlElement(name = "picture")
    private List<PictureViewDTO> pictures;

    public PictureRootDTO() {
    }

    public List<PictureViewDTO> getPictures() {
        return pictures;
    }

    public void setPictures(List<PictureViewDTO> pictures) {
        this.pictures = pictures;
    }
}
