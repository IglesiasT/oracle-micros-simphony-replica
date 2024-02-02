package dev.microsreplica.payment;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Cash.class, name = "cash"),
        @JsonSubTypes.Type(value = CreditCard.class, name = "creditCard")
})
public interface PaymentMethod {
    void pay(double mount);
}
