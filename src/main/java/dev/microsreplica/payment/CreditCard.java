package dev.microsreplica.payment;

import dev.microsreplica.payment.discount.Discount;
import dev.microsreplica.payment.discount.NoDiscount;

public class CreditCard extends PaymentMethod {
    private String cardNumber;
    private String expirationDate;
    private Integer cvv;

    public CreditCard(){
        this.discount = new NoDiscount();
        this.cardNumber = "No card number";
        this.expirationDate = "No expiration date";
        this.cvv = 0;
    }

    public CreditCard(Discount discount, String cardNumber, String expirationDate, Integer cvv) {
        this.discount = discount;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
    }


    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    @Override
    public void pay(double mount) {
        System.out.println("Paying " + mount + " with credit card");
        this.discount.apply(mount);
        System.out.println("Successfully paid " + mount + " with credit card");
    }
}
