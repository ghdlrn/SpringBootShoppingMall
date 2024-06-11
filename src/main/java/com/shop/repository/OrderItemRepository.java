package com.shop.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
