package alararestaurant.domain.dtos;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "orders")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderSeedRootDTO {
    @XmlElement(name = "order")
    private List<OrderSeedDTO> orders;

    public OrderSeedRootDTO() {
    }

    @NotNull
    public List<OrderSeedDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderSeedDTO> orders) {
        this.orders = orders;
    }
}
