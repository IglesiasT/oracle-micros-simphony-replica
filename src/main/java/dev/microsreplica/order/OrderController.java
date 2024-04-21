package dev.microsreplica.order;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public Order getById(@PathVariable Long id){
        return this.orderService.getById(id);
    }

    @PostMapping
    public Order addOrder(@RequestBody @Valid Order order){
        return this.orderService.saveOrder(order);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EDITOR')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id){
        this.orderService.deleteOrder(id);
    }
}
