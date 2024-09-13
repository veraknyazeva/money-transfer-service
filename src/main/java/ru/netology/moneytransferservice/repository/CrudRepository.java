package ru.netology.moneytransferservice.repository;

public interface CrudRepository<T> {

    T findByNumber(String cardNumber);
}
