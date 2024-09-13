package ru.netology.moneytransferservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class MoneyTransferTransaction {
    private String operationId;
    private String cardNumberFrom;
    private String cardNumberTo;
    private Integer sum;
}
