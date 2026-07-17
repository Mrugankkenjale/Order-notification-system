package com.example.orders.order;

import com.example.orders.common.ResourceNotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository repository;
    private final ApplicationEventPublisher events;

    public OrderService(OrderRepository repository, ApplicationEventPublisher events) {
        this.repository = repository; this.events = events;
    }

    @Transactional
    public OrderResponse create(CreateOrderRequest request) {
        CustomerOrder order = new CustomerOrder();
        order.setCustomerName(request.customerName()); order.setEmail(request.email());
        order.setPhone(request.phone()); order.setProductName(request.productName());
        order.setQuantity(request.quantity()); order.setTotalAmount(request.totalAmount());
        order.setStatus(OrderStatus.CREATED);
        CustomerOrder saved = repository.save(order);
        events.publishEvent(new OrderStatusChangedEvent(saved.getId(), saved.getStatus()));
        return OrderResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public OrderResponse get(Long id) { return OrderResponse.from(find(id)); }

    @Transactional(readOnly = true)
    public List<OrderResponse> list() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "createdAt")).stream().map(OrderResponse::from).toList();
    }

    @Transactional
    public OrderResponse updateStatus(Long id, OrderStatus nextStatus) {
        CustomerOrder order = find(id);
        validateTransition(order.getStatus(), nextStatus);
        order.setStatus(nextStatus);
        CustomerOrder saved = repository.save(order);
        events.publishEvent(new OrderStatusChangedEvent(saved.getId(), nextStatus));
        return OrderResponse.from(saved);
    }

    private CustomerOrder find(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order " + id + " not found"));
    }

    private void validateTransition(OrderStatus current, OrderStatus next) {
        if (current == next) throw new IllegalArgumentException("Order is already " + next);
        if (current == OrderStatus.DELIVERED || current == OrderStatus.CANCELLED)
            throw new IllegalArgumentException("Cannot change a completed or cancelled order");
        if (next == OrderStatus.CREATED)
            throw new IllegalArgumentException("An order cannot return to CREATED");
    }
}
