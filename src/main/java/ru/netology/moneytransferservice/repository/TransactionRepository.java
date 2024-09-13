package ru.netology.moneytransferservice.repository;

import ru.netology.moneytransferservice.model.entity.MoneyTransferTransaction;

public interface TransactionRepository {

    MoneyTransferTransaction findByOperationId(String operationId);

    String save(MoneyTransferTransaction transaction);
}
