package com.productsshopxml.persistence.models.dtos.userDtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsersWithSoldProductsRootDTO {
    @XmlElement(name = "user")
    private List<UserSoldProductViewDTO> users;

    public UsersWithSoldProductsRootDTO() {
    }

    public List<UserSoldProductViewDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserSoldProductViewDTO> users) {
        this.users = users;
    }
}
