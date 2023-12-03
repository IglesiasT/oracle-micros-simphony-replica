package dev.microsreplica.table;

import dev.microsreplica.product.Product;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Collection;

//@Entity
public class Table {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotBlank
    private Integer id;
    private Collection<Product> products;

    public Table(){
    }

    public Table(Integer id){
        this.id = id;
        this.products = new ArrayList<>();
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

    public Collection<Product> getProducts() {
        return products;
    }

    public void setProducts(Collection<Product> products) {
        this.products = products;
    }
}
