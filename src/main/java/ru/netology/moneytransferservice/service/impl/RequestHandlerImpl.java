package ru.netology.moneytransferservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.netology.moneytransferservice.exeption.TransferNotPossibleException;
import ru.netology.moneytransferservice.logger.LogUtils;
import ru.netology.moneytransferservice.model.dto.ConfirmOperationRequest;
import ru.netology.moneytransferservice.model.dto.MoneyTransferRequest;
import ru.netology.moneytransferservice.model.dto.MoneyTransferServiceResponse;
import ru.netology.moneytransferservice.model.entity.BankCard;
import ru.netology.moneytransferservice.model.entity.MoneyTransferTransaction;
import ru.netology.moneytransferservice.service.CardService;
import ru.netology.moneytransferservice.service.RequestHandler;
import ru.netology.moneytransferservice.service.TransactionService;

import java.util.Objects;

@Service
@Slf4j
public class RequestHandlerImpl implements RequestHandler {

    private final TransactionService transactionService;
    private final CardService cardService;

    @Autowired
    public RequestHandlerImpl(TransactionService transactionService,
                              CardService cardService) {
        this.transactionService = transactionService;
        this.cardService = cardService;
    }

    @Override
    public MoneyTransferServiceResponse handleTransferRequest(MoneyTransferRequest request) {
        BankCard bankCard = new BankCard();
        bankCard.setNumber(request.getCardFromNumber());
        bankCard.setCvv(request.getCardFromCVV());
        bankCard.setValidTill(request.getCardFromValidTill());
        Integer sumOfTransfer = request.getAmount().getValue();

        boolean transferIsPossible = cardService.transferIsPossible(bankCard, sumOfTransfer);
        if (transferIsPossible) {
            String operationId = transactionService.begin(request.getCardFromNumber(), request.getCardToNumber(), sumOfTransfer);
            return MoneyTransferServiceResponse.builder()
                    .operationId(operationId)
                    .build();
        } else {
            log.warn(LogUtils.BALANCE_NOT_ENOUGH_CARD_TRANSFER_LOG_FORMAT,
                    bankCard.getNumber(), LogUtils.CARD_TRANSFER_UNSUCCESSFUL);
            throw new TransferNotPossibleException("Недостаточно средств");
        }
    }

    @Override
    public MoneyTransferServiceResponse handleConfirmRequest(ConfirmOperationRequest request) {
        String code = request.getCode();
        if (Objects.isNull(code) || code.length() != 4) {
            throw new TransferNotPossibleException("Неверный код");
        }
        MoneyTransferTransaction transaction = transactionService.getTransaction(request.getOperationId());
        cardService.transferMoney(transaction);
        return MoneyTransferServiceResponse.builder()
                .operationId(transaction.getOperationId())
                .build();
    }
}
