package com.gameStore.services.orderServices;

import com.gameStore.persistence.models.entities.Order;
import com.gameStore.persistence.repositories.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService{
    private OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void register(Order order) {
        this.orderRepository.save(order);
    }
}
