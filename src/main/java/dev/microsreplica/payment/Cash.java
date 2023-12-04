package dev.microsreplica.payment;

public class Cash implements PaymentMethod{

    @Override
    public boolean pay(double amountToPay) {
        return true;
    }
}
