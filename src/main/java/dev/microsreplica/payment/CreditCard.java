package dev.microsreplica.payment;

public class CreditCard implements PaymentMethod{
    private String cardNumber;
    private String expirationDate;
    private Integer cvv;

    public CreditCard(){

    }

    public CreditCard(String cardNumber, String expirationDate, Integer cvv) {
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

    }
}
