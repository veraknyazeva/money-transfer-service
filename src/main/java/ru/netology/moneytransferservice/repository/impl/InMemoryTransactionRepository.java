package ru.netology.moneytransferservice.repository.impl;

import org.springframework.stereotype.Repository;
import ru.netology.moneytransferservice.model.entity.MoneyTransferTransaction;
import ru.netology.moneytransferservice.repository.TransactionRepository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryTransactionRepository implements TransactionRepository {

    private final Map<String, MoneyTransferTransaction> transactions = new ConcurrentHashMap<>();

    @Override
    public MoneyTransferTransaction findByOperationId(String operationId) {
        return transactions.get(operationId);
    }

    @Override
    public String save(MoneyTransferTransaction transaction) {
        String operationId = UUID.randomUUID().toString();
        transaction.setOperationId(operationId);
        transactions.put(operationId, transaction);
        return operationId;
    }
}
