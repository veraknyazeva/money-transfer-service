package ru.netology.moneytransferservice.service;

import ru.netology.moneytransferservice.model.entity.BankCard;
import ru.netology.moneytransferservice.model.entity.MoneyTransferTransaction;

public interface CardService {
    boolean transferIsPossible(BankCard bankCardFrom, Integer sumOfTransfer);

    boolean transferMoney(MoneyTransferTransaction transaction);
}
