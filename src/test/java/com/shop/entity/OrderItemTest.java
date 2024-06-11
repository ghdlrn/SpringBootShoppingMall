package com.shop.entity;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class OrderItemTest {

    @Test
    public void test() {

        Order order = new Order();
        order.setId(10L);
        order.setOrderDate(LocalDateTime.now());

        order.getOrderItem();
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);

        log.info("====================================");
        log.info(orderItem.getOrder().getId());

    }
}