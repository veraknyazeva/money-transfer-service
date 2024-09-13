package ru.netology.moneytransferservice.service;

import ru.netology.moneytransferservice.model.dto.ConfirmOperationRequest;
import ru.netology.moneytransferservice.model.dto.MoneyTransferRequest;
import ru.netology.moneytransferservice.model.dto.MoneyTransferServiceResponse;

public interface RequestHandler {
    MoneyTransferServiceResponse handleTransferRequest(MoneyTransferRequest request);

    MoneyTransferServiceResponse handleConfirmRequest(ConfirmOperationRequest request);

}
