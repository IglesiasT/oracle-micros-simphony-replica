package dev.microsreplica.order;

import dev.microsreplica.payment.PaymentMethod;
import dev.microsreplica.payment.Priceable;
import dev.microsreplica.product.Product;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

@Service
public class OrderService{
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    public void charge(Long id, PaymentMethod paymentMethod) {
        double finalCost = 0.0;
        Order orderToCharge = this.orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The order with this id doesn't exits"));

        List<Product> productsToCharge = orderToCharge.getProducts();

        for (Priceable product : productsToCharge){
            finalCost += product.getFinalPrice();
        }
        paymentMethod.pay(finalCost);   // TODO catch pay errors and don't clean the products if the order wasn't pay

        orderToCharge.setProducts(Collections.emptyList());
    }
}
