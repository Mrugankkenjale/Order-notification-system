package com.example.orders.notification;

import java.time.Instant;

public record NotificationResponse(Long id, Long orderId, NotificationChannel channel,
                                   String recipient, String message, DeliveryStatus deliveryStatus,
                                   Instant sentAt) {
    public static NotificationResponse from(Notification n) {
        return new NotificationResponse(n.getId(), n.getOrder().getId(), n.getChannel(), n.getRecipient(),
                n.getMessage(), n.getDeliveryStatus(), n.getSentAt());
    }
}
