package dev.microsreplica.table;

import dev.microsreplica.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Collections;
import java.util.List;

@Entity
@jakarta.persistence.Table(name = "tables")
public class Table {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Positive
    private Integer id;

    @ManyToMany
    @JoinTable(
            name = "products_by_table",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "table_id", referencedColumnName = "id")
    )
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

}
