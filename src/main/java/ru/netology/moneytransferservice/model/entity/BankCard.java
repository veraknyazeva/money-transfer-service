package ru.netology.moneytransferservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class BankCard {
    private String number;
    private String validTill;
    private String cvv;
    private Integer balance;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankCard bankCard = (BankCard) o;
        return Objects.equals(number, bankCard.number) && Objects.equals(validTill, bankCard.validTill) && Objects.equals(cvv, bankCard.cvv);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, validTill, cvv);
    }
}
