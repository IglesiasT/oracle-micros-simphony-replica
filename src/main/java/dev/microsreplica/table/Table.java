package dev.microsreplica.table;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.microsreplica.payment.PaymentMethod;
import dev.microsreplica.product.Product;
import dev.microsreplica.product.ProductsCollection;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.annotation.Id;

//@Entity
public class Table {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private ProductsCollection products;

    public Table(){
    }

    public Table(Integer id){
        this.id = id;
        this.products = new ProductsCollection();
    }

    public void add(Product product){
        this.products.add(product);
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProductsCollection getProducts() {
        return this.products;
    }

    public void setProducts(ProductsCollection products) {
        this.products = products;
    }

    public void charge(PaymentMethod paymentMethod) {
        paymentMethod.pay(this.getFinalCost());
    }

    private double getFinalCost() {
        return this.products.getFinalCost();
    }
}
