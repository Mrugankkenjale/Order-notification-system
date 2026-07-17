package com.example.orders.notification;

import com.example.orders.common.ResourceNotFoundException;
import com.example.orders.order.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import java.util.List;

@Service
public class NotificationService {
    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);
    private final NotificationRepository notifications;
    private final OrderRepository orders;

    public NotificationService(NotificationRepository notifications, OrderRepository orders) {
        this.notifications = notifications; this.orders = orders;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional
    public void onOrderStatusChanged(OrderStatusChangedEvent event) {
        CustomerOrder order = orders.findById(event.orderId()).orElseThrow();
        String message = "Order #" + order.getId() + " for " + order.getProductName() + " is now " + event.status() + ".";
        send(order, NotificationChannel.EMAIL, order.getEmail(), message);
        send(order, NotificationChannel.SMS, order.getPhone(), message);
    }

    private void send(CustomerOrder order, NotificationChannel channel, String recipient, String message) {
        log.info("Simulated {} notification to {}: {}", channel, recipient, message);
        notifications.save(new Notification(order, channel, recipient, message, DeliveryStatus.SENT));
    }

    @Transactional(readOnly = true)
    public List<NotificationResponse> forOrder(Long orderId) {
        if (!orders.existsById(orderId)) throw new ResourceNotFoundException("Order " + orderId + " not found");
        return notifications.findByOrderIdOrderBySentAtDesc(orderId).stream().map(NotificationResponse::from).toList();
    }
}
