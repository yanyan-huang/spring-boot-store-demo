package com.springdemo.store;

import org.springframework.context.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StoreApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(StoreApplication.class, args);
        var orderService = context.getBean(OrderService.class);
//        var orderService = new OrderService(new StripePaymentService());
//        var orderService = new OrderService(new PayPalPaymentService());
//        var orderService = new OrderService();
//        orderService.setPaymentService(new PayPalPaymentService());
        orderService.placeOrder();
    }

}
