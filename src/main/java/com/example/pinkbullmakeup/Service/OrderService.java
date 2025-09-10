package com.example.pinkbullmakeup.Service;

import com.example.pinkbullmakeup.ENUMS.DeliveryStatus;
import com.example.pinkbullmakeup.Entity.*;
import com.example.pinkbullmakeup.Exceptions.ResourceNotFoundException;
import com.example.pinkbullmakeup.Repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final CustomerRepository customerRepository;

    private final ProductService productService;

    public OrderService(OrderRepository orderRepository, CartService cartService, CustomerRepository customerRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.customerRepository = customerRepository;
        this.productService = productService;
    }

    public List<OrderItem> cartToOrderItemMapping(List<CartItem> cartItemList){
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem item : cartItemList){
            OrderItem orderItem = new OrderItem(item.getProductInCart(),item.getProductQuantity(),item.getProductQuantity(),item.getShade());

            orderItems.add(orderItem);
        }

        return orderItems;
    }


    public Order generateOrder(UUID userId, List<CartItem> cartItemList){
        if (cartItemList == null || cartItemList.isEmpty()) {
            throw new IllegalArgumentException("Cannot place an order with an empty cart.");
        }

        Customer customer = customerRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found."));

        List<OrderItem> orderItems = cartToOrderItemMapping(cartItemList);

        productService.reduceStockAfterSale(orderItems);

        Order order = new Order();
        order.setUser(customer);
        order.setOrderItems(orderItems);
        order.setTimeOrderPlaced(LocalDateTime.now());
        order.setTotalPrice(cartService.getCartTotalPrice(customer.getUserId()));

        Delivery delivery = new Delivery();
        delivery.setDeliveryAddress(String.format("%s, %s", customer.getAddress(), customer.getCity()));
        delivery.setEstimatedDeliveryDate(LocalDate.now().plusWeeks(1));
        delivery.setStatus(DeliveryStatus.PENDING);
        order.setDeliveryDetails(delivery);

       Order savedOrder = orderRepository.save(order);

        try {
            cartService.clearCart(customer.getUserId());
        } catch (Exception e) {
            throw new IllegalStateException("Order placed but failed to clear the cart. Please check your cart.");
        }

        return savedOrder;
    }

    public void cancelOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found."));

        if (order.getDeliveryDetails().getStatus() == DeliveryStatus.PENDING) {
            orderRepository.delete(order);
        } else {
            throw new IllegalStateException("Order cannot be canceled. It is already being processed or delivered.");
        }
    }

    @Transactional(readOnly = true)
    public List<Order> getAllOrdersOfUser(UUID userId){
        return orderRepository.findAllByUser_UserId(userId);
    }
}
