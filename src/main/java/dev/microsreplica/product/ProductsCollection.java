package dev.microsreplica.product;

import java.util.ArrayList;
import java.util.Collection;

public class ProductsCollection {
    private Collection<Product> products;

    public ProductsCollection(){
        this.products = new ArrayList<Product>();
    }

    public void add(Product product){
        this.products.add(product);
    }

    public double getFinalCost() {
        double finalCost = 0;

        for (Product product:products) {
            finalCost += product.getFinalCost();
        }

        return finalCost;
    }
}
