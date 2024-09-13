package ru.netology.moneytransferservice.repository.data;

import ru.netology.moneytransferservice.model.entity.BankCard;

public class BankCardData {
    private static final String NUMBER_OF_CARD_1 = "0000000000000000";
    private static final String CVV_1 = "000";
    private static final String VALID_TILL_1 = "07/27";
    private static final Integer BALANCE_1 = 1000000000;

    private static final String NUMBER_OF_CARD_2 = "1111111111111111";
    private static final String CVV_2 = "111";
    private static final String VALID_TILL_2 = "08/28";
    private static final Integer BALANCE_2 = 1000000000;
    private static final String NUMBER_OF_CARD_3 = "2222222222222222";
    private static final String CVV_3 = "222";
    private static final String VALID_TILL_3 = "09/29";
    private static final Integer BALANCE_3 = 100000;
    private static final String NUMBER_OF_CARD_4 = "3333333333333333";
    private static final String CVV_4 = "333";
    private static final String VALID_TILL_4 = "07/27";
    private static final Integer BALANCE_4 = 0;


    public static BankCard getCardOne() {
        return BankCard.builder()
                .number(NUMBER_OF_CARD_1)
                .cvv(CVV_1)
                .balance(BALANCE_1)
                .validTill(VALID_TILL_1)
                .build();
    }

    public static BankCard getCardTwo() {
        return BankCard.builder()
                .number(NUMBER_OF_CARD_2)
                .cvv(CVV_2)
                .balance(BALANCE_2)
                .validTill(VALID_TILL_2)
                .build();
    }

    public static BankCard getCardThree() {
        return BankCard.builder()
                .number(NUMBER_OF_CARD_3)
                .cvv(CVV_3)
                .balance(BALANCE_3)
                .validTill(VALID_TILL_3)
                .build();
    }

    public static BankCard getCardFour() {
        return BankCard.builder()
                .number(NUMBER_OF_CARD_4)
                .cvv(CVV_4)
                .balance(BALANCE_4)
                .validTill(VALID_TILL_4)
                .build();
    }
}
