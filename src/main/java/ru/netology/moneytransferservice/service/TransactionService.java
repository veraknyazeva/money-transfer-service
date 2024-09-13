package ru.netology.moneytransferservice.service;

import ru.netology.moneytransferservice.model.entity.MoneyTransferTransaction;

public interface TransactionService {

    String begin(String cardNumberFrom, String cardNumberTo, Integer sumTransfer);

    MoneyTransferTransaction getTransaction(String operationId);
}
