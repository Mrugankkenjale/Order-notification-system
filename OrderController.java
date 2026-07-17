package com.example.orders.order;

import com.example.orders.notification.NotificationResponse;
import com.example.orders.notification.NotificationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orders;
    private final NotificationService notifications;

    public OrderController(OrderService orders, NotificationService notifications) {
        this.orders = orders; this.notifications = notifications;
    }

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse create(@Valid @RequestBody CreateOrderRequest request) { return orders.create(request); }

    @GetMapping
    public List<OrderResponse> list() { return orders.list(); }

    @GetMapping("/{id}")
    public OrderResponse get(@PathVariable Long id) { return orders.get(id); }

    @PatchMapping("/{id}/status")
    public OrderResponse updateStatus(@PathVariable Long id, @Valid @RequestBody UpdateOrderStatusRequest request) {
        return orders.updateStatus(id, request.status());
    }

    @GetMapping("/{id}/notifications")
    public List<NotificationResponse> notifications(@PathVariable Long id) { return notifications.forOrder(id); }
}
