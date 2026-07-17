package com.example.orders.notification;

import com.example.orders.order.CustomerOrder;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "notifications")
public class Notification {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) private CustomerOrder order;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private NotificationChannel channel;
    @Column(nullable = false) private String recipient;
    @Column(nullable = false, length = 500) private String message;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private DeliveryStatus deliveryStatus;
    @Column(nullable = false, updatable = false) private Instant sentAt;

    protected Notification() {}
    public Notification(CustomerOrder order, NotificationChannel channel, String recipient, String message, DeliveryStatus deliveryStatus) {
        this.order = order; this.channel = channel; this.recipient = recipient;
        this.message = message; this.deliveryStatus = deliveryStatus; this.sentAt = Instant.now();
    }
    public Long getId() { return id; }
    public CustomerOrder getOrder() { return order; }
    public NotificationChannel getChannel() { return channel; }
    public String getRecipient() { return recipient; }
    public String getMessage() { return message; }
    public DeliveryStatus getDeliveryStatus() { return deliveryStatus; }
    public Instant getSentAt() { return sentAt; }
}
