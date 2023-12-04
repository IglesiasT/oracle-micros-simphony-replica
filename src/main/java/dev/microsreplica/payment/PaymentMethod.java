package dev.microsreplica.payment;

public interface PaymentMethod {

    boolean pay(double amountToPay);
}
