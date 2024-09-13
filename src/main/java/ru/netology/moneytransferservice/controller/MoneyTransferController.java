package ru.netology.moneytransferservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.moneytransferservice.model.dto.ConfirmOperationRequest;
import ru.netology.moneytransferservice.model.dto.MoneyTransferRequest;
import ru.netology.moneytransferservice.model.dto.MoneyTransferServiceResponse;
import ru.netology.moneytransferservice.service.RequestHandler;

@RestController
public class MoneyTransferController {
    private final RequestHandler handler;

    @Autowired
    public MoneyTransferController(RequestHandler handler) {
        this.handler = handler;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/transfer")
    public MoneyTransferServiceResponse transfer(@RequestBody MoneyTransferRequest request) {
        return handler.handleTransferRequest(request);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/confirmOperation")
    public MoneyTransferServiceResponse confirmOperation(@RequestBody ConfirmOperationRequest request) {
        return handler.handleConfirmRequest(request);
    }
}
