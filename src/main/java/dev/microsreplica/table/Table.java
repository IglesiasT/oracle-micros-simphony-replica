package dev.microsreplica.table;

import dev.microsreplica.payment.PaymentMethod;
import dev.microsreplica.product.Product;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.annotation.Id;

import java.util.Collections;
import java.util.List;

//@Entity
public class Table {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Positive
    private Integer id;
    private List<Product> products;

    public Table(){
         this.products = Collections.emptyList();
    }

    public Table(Integer id){
        this.id = id;
        this.products = Collections.emptyList();
    }

    public void add(Product product){
        this.products.add(product);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void charge(PaymentMethod paymentMethod) {
        double finalCost = this.getProductsCost();
        paymentMethod.pay(finalCost);
    }

    private double getProductsCost() {
        double finalCost = 0;

        for (Product product : this.products){
            finalCost += product.getCost();
        }

        return finalCost;
    }
}
