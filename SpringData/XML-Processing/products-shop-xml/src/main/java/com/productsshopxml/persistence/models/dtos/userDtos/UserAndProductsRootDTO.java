package com.productsshopxml.persistence.models.dtos.userDtos;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserAndProductsRootDTO {
    @XmlAttribute(name = "count")
    private int count;
    @XmlElement(name = "user")
    private List<UserWithProductViewDTO> users;

    public UserAndProductsRootDTO() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<UserWithProductViewDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserWithProductViewDTO> users) {
        this.users = users;
    }
}
