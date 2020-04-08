package hiberspring.domain.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductsSeedRootDTO {
    @XmlElement(name = "product")
    private List<ProductsSeedDTO> products;

    public ProductsSeedRootDTO() {
    }

    public List<ProductsSeedDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsSeedDTO> products) {
        this.products = products;
    }
}
