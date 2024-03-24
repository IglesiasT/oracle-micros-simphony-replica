package dev.microsreplica.payment.discount;

public class NoDiscount implements Discount {
    @Override
    public double apply(double price) {
        return price;
    }
}
