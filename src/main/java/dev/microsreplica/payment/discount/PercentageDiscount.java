package dev.microsreplica.payment.discount;

public class PercentageDiscount implements Discount{
    private final double percentage;

    public PercentageDiscount(double percentage){
        this.percentage = percentage;
    }
    @Override
    public double apply(double mount) {
        return mount - (mount * percentage / 100);
    }
}
