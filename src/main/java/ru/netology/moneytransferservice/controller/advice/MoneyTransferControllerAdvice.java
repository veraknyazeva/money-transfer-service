package ru.netology.moneytransferservice.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.netology.moneytransferservice.exeption.TransferNotPossibleException;
import ru.netology.moneytransferservice.exeption.UnknownCardException;
import ru.netology.moneytransferservice.model.dto.MoneyTransferServiceResponse;

@RestControllerAdvice
public class MoneyTransferControllerAdvice {

    @ExceptionHandler(TransferNotPossibleException.class)
    public ResponseEntity<MoneyTransferServiceResponse> handleTransferNotPossibleException(TransferNotPossibleException exception) {
        String message = exception.getMessage();
        int id = exception.getId();
        MoneyTransferServiceResponse response = MoneyTransferServiceResponse.builder()
                .id(id)
                .message(message)
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UnknownCardException.class)
    public ResponseEntity<MoneyTransferServiceResponse> handleUnknownCardException(UnknownCardException exception) {
        String message = exception.getMessage();
        int id = exception.getId();
        MoneyTransferServiceResponse response = MoneyTransferServiceResponse.builder()
                .id(id)
                .message(message)
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
