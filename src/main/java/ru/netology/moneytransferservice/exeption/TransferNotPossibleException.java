package ru.netology.moneytransferservice.exeption;

import lombok.Getter;

@Getter
public class TransferNotPossibleException extends RuntimeException {

    private final int id = 101;

    public TransferNotPossibleException(String msg) {
        super(msg);
    }
}
