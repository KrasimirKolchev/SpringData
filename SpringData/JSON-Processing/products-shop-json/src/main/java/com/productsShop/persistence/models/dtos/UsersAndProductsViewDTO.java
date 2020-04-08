package com.productsShop.persistence.models.dtos;

import com.google.gson.annotations.Expose;
import com.productsShop.persistence.models.dtos.userDtos.UserWithProductViewDTO;

import java.util.List;

public class UsersAndProductsViewDTO {
    @Expose
    private int usersCount;
    @Expose
    private List<UserWithProductViewDTO> users;

    public UsersAndProductsViewDTO() {
    }

    public int getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(int usersCount) {
        this.usersCount = usersCount;
    }

    public List<UserWithProductViewDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserWithProductViewDTO> users) {
        this.users = users;
    }
}
