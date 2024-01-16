package dev.microsreplica.payment;

public class Cash implements PaymentMethod{
    private static final String type = "cash";
    private double amount;

    public Cash(double amount){
        this.amount = amount;
    }

    @Override
    public void pay(double mount) {
        this.amount -= mount;
    }
}
