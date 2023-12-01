package dev.microsreplica.product;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.data.annotation.Id;

//@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotBlank (message = "Products must have an id")
    private Integer id;

    @Column(unique = true)
    @NotBlank(message = "Products must have a name")
    private String name;

    @PositiveOrZero
    @NotNull
    private float price;

    public Integer getId() {
        return id;
    }
}
