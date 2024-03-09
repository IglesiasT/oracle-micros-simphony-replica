package dev.microsreplica.product;

import dev.microsreplica.payment.Priceable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "products")
public class Product implements Priceable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double price;

    @Column(unique = true)
    @NotBlank
    private String name;

    public Product() {
        this.price = 0.0;
        this.name = "noName";
    }

    public Product(String name, double price) {
        this.price = price;
        this.name = name;
    }

    public double getPrice(){
        return this.price;
    }

    public void setPrice(double newPrice){
        this.price = newPrice;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public double getFinalPrice() {
        return this.getPrice();
    }
}
