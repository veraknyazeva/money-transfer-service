package ru.netology.moneytransferservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.netology.moneytransferservice.model.entity.MoneyTransferTransaction;
import ru.netology.moneytransferservice.repository.TransactionRepository;
import ru.netology.moneytransferservice.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository repository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public String begin(String cardNumberFrom, String cardNumberTo, Integer sumTransfer) {
        MoneyTransferTransaction transaction = new MoneyTransferTransaction();
        transaction.setCardNumberFrom(cardNumberFrom);
        transaction.setCardNumberTo(cardNumberTo);
        transaction.setSum(sumTransfer);
        return repository.save(transaction);
    }

    @Override
    public MoneyTransferTransaction getTransaction(String operationId) {
       return repository.findByOperationId(operationId);
    }
}
