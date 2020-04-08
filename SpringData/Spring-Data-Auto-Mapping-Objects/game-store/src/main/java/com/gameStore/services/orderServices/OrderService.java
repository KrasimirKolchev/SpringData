package com.gameStore.services.orderServices;

import com.gameStore.persistence.models.entities.Order;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    void register(Order order);
}
