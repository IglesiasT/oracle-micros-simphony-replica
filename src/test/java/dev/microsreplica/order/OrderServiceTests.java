package dev.microsreplica.order;

import dev.microsreplica.payment.PaymentMethod;
import dev.microsreplica.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTests {

    @Mock
    private OrderRepository orderRepository;

    private OrderService orderService;

    @BeforeEach
    public void setUp(){
        this.orderService = new OrderService(this.orderRepository);
    }

    @Test
    public void chargeOrder_WithCorrectIdAndEnoughCash_ReturnsOrderWithNoProducts(){
        // Arrange
        Order orderWithProducts = mock(Order.class);
        PaymentMethod paymentMethod = mock(PaymentMethod.class);
        List<Product> products = List.of(
                new Product("Product A", 10),
                new Product("Product B", 16),
                new Product("Product C", 4)
        );
        when(this.orderRepository.findById(1L)).thenReturn(Optional.of(orderWithProducts));

        // Act
        orderWithProducts.setProducts(products);
        this.orderService.charge(1L, paymentMethod);

        // Assert
        verify(this.orderRepository, times(1)).findById(1L);
        assertTrue(orderWithProducts.getProducts().isEmpty());
    }


    @Test
    public void chargeOrder_WithNonexistentId_ThrowsNotFoundException() {
        // Arrange
        PaymentMethod paymentMethod = mock(PaymentMethod.class);
        Long nonexistentId = -1L;
        when(orderRepository.findById(nonexistentId)).thenReturn(Optional.empty());
        String expectedMessage = "The order with this id doesn't exits";

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> this.orderService.charge(nonexistentId, paymentMethod));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode(), "Expected NOT_FOUND status");
        assertTrue(exception.getMessage().contains(expectedMessage), "Exception message does not contain expected text");
        verify(orderRepository, times(1)).findById(nonexistentId);
    }

}
