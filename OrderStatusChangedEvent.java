package com.example.orders.order;

public record OrderStatusChangedEvent(Long orderId, OrderStatus status) {}
