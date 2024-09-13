package ru.netology.moneytransferservice.repository.impl;

import org.springframework.stereotype.Repository;
import ru.netology.moneytransferservice.model.entity.BankCard;
import ru.netology.moneytransferservice.repository.CrudRepository;
import ru.netology.moneytransferservice.repository.data.BankCardData;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryCardRepository implements CrudRepository<BankCard> {

    private static final Map<String, BankCard> cards = new ConcurrentHashMap<>();


    static {
        BankCard cardOne = BankCardData.getCardOne();
        cards.put(cardOne.getNumber(), cardOne);
        BankCard cardTwo = BankCardData.getCardTwo();
        cards.put(cardTwo.getNumber(), cardTwo);
        BankCard cardThree = BankCardData.getCardThree();
        cards.put(cardThree.getNumber(), cardThree);
        BankCard cardFour = BankCardData.getCardFour();
        cards.put(cardFour.getNumber(), cardFour);
    }

    @Override
    public BankCard findByNumber(String cardNumber) {
        return cards.get(cardNumber);
    }
}
