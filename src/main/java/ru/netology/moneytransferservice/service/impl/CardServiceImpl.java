package ru.netology.moneytransferservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.netology.moneytransferservice.exeption.TransferNotPossibleException;
import ru.netology.moneytransferservice.exeption.UnknownCardException;
import ru.netology.moneytransferservice.logger.LogUtils;
import ru.netology.moneytransferservice.model.entity.BankCard;
import ru.netology.moneytransferservice.model.entity.MoneyTransferTransaction;
import ru.netology.moneytransferservice.repository.CrudRepository;
import ru.netology.moneytransferservice.service.CardService;

import java.util.Objects;

@Service
@Slf4j
public class CardServiceImpl implements CardService {

    @Value("${service.commissions.rate}")
    private Double commissionRate;
    private final CrudRepository<BankCard> repository;

    @Autowired
    public CardServiceImpl(CrudRepository<BankCard> repository) {
        this.repository = repository;
    }

    @Override
    public boolean transferIsPossible(BankCard bankCardFrom, Integer sumOfTransfer) {
        BankCard cardFrom = repository.findByNumber(bankCardFrom.getNumber());
        if (Objects.nonNull(cardFrom) && bankCardFrom.equals(cardFrom)) {
            return cardFrom.getBalance() >= sumOfTransfer;
        } else {
            log.warn(LogUtils.CARD_NOT_FOUND_LOG_FORMAT,
                    bankCardFrom.getNumber(), LogUtils.CARD_TRANSFER_UNSUCCESSFUL);
            throw new UnknownCardException("Перевод с неизвестной карты");
        }
    }

    @Override
    public boolean transferMoney(MoneyTransferTransaction transaction) {
        BankCard cardFrom = repository.findByNumber(transaction.getCardNumberFrom());
        BankCard cardTo = repository.findByNumber(transaction.getCardNumberTo());
        Integer sum = transaction.getSum();
        Integer commission = (int) (sum * commissionRate);
        Integer finalSumOfTransfer = sum + commission;
        boolean transferIsPossible = transferIsPossible(cardFrom, finalSumOfTransfer); //двухфазная проверка
        if (transferIsPossible) {
            Integer newBalanceCardFrom = cardFrom.getBalance() - finalSumOfTransfer;
            cardFrom.setBalance(newBalanceCardFrom);
            if (Objects.nonNull(cardTo)) {
                Integer newBalanceCardTo = cardTo.getBalance() + sum;
                cardTo.setBalance(newBalanceCardTo);
            }
            log.info(LogUtils.CARD_TRANSFER_LOG_FORMAT,
                    transaction.getCardNumberFrom(), transaction.getCardNumberTo(),
                    sum, commission, LogUtils.CARD_TRANSFER_SUCCESSFUL
            );
            return true;
        } else {
            log.warn(LogUtils.BALANCE_NOT_ENOUGH_CARD_TRANSFER_LOG_FORMAT,
                    transaction.getCardNumberFrom(), LogUtils.CARD_TRANSFER_UNSUCCESSFUL);
            throw new TransferNotPossibleException("Недостаточно средств");
        }
    }
}
