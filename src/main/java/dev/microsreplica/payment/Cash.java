package dev.microsreplica.payment;

import dev.microsreplica.payment.discount.Discount;

public class Cash extends PaymentMethod {
    private double amount;

    public Cash(Discount discount, double amount){
        this.discount = discount;
        this.amount = amount;
    }

    @Override
    public void pay(double mount) {
        this.amount -= discount.apply(mount);
    }
}
