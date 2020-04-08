package alararestaurant.domain.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;

@Entity(name = "order_items")
public class OrderItem extends BaseEntity {
    private Order order;
    private Item item;
    private int quantity;

    public OrderItem() {
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Column(nullable = false)
    @Min(value = 0)
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
