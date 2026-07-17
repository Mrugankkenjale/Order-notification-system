package com.example.orders.order;

import java.math.BigDecimal;
import java.time.Instant;

public record OrderResponse(Long id, String customerName, String email, String phone,
                            String productName, Integer quantity, BigDecimal totalAmount,
                            OrderStatus status, Instant createdAt, Instant updatedAt) {
    public static OrderResponse from(CustomerOrder order) {
        return new OrderResponse(order.getId(), order.getCustomerName(), order.getEmail(), order.getPhone(),
                order.getProductName(), order.getQuantity(), order.getTotalAmount(), order.getStatus(),
                order.getCreatedAt(), order.getUpdatedAt());
    }
}
