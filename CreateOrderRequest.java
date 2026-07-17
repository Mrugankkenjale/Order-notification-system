package com.example.orders.order;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record CreateOrderRequest(
        @NotBlank String customerName,
        @NotBlank @Email String email,
        @NotBlank @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "must be a valid phone number") String phone,
        @NotBlank String productName,
        @NotNull @Min(1) Integer quantity,
        @NotNull @DecimalMin(value = "0.01") BigDecimal totalAmount) {}
