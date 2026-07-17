Order Notification System

A Java 17 + Spring Boot REST application that stores customer orders, tracks status changes, and records simulated email and SMS notifications.
Features

- Create, list, and retrieve orders
- Update an order through `CREATED`, `CONFIRMED`, `PROCESSING`, `SHIPPED`, `DELIVERED`, or `CANCELLED`
- Automatically create email and SMS notification records after each committed status change
- Input validation and consistent JSON error responses
- H2 persistent database and browser console
- Integration tests for the main API workflow
