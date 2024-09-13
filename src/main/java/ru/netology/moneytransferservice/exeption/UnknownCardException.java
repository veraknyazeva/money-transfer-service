package ru.netology.moneytransferservice.exeption;

import lombok.Getter;

@Getter
public class UnknownCardException extends RuntimeException {
    private final int id = 102;

    public UnknownCardException(String msg) {
        super(msg);
    }
}
