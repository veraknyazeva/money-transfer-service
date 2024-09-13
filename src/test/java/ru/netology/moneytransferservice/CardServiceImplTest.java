package ru.netology.moneytransferservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.netology.moneytransferservice.exeption.UnknownCardException;
import ru.netology.moneytransferservice.model.entity.BankCard;
import ru.netology.moneytransferservice.repository.CrudRepository;
import ru.netology.moneytransferservice.repository.data.BankCardData;
import ru.netology.moneytransferservice.repository.impl.InMemoryCardRepository;
import ru.netology.moneytransferservice.service.CardService;
import ru.netology.moneytransferservice.service.impl.CardServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CardServiceImpl.class, InMemoryCardRepository.class})
public class CardServiceImplTest {

    static {
        System.setProperty("service.commissions.rate", "0.01");
    }

    private static final String NUMBER_OF_CARD_5 = "2345622222222222";
    private static final String CVV_5 = "555";
    private static final String VALID_TILL_5 = "09/29";
    private static final Integer BALANCE_5 = 100000;
    private static final Integer SUM = 1000;

    @Autowired
    private CardService cardService;
    @Autowired
    private CrudRepository<BankCard> repository;

    @Test
    public void transfer_is_possible_test() {
        assertThat(cardService).isNotNull();
        assertThat(cardService.transferIsPossible(BankCardData.getCardOne(), SUM)).isTrue();
    }

    @Test
    public void transfer_is_not_possible_test() {
        assertThat(cardService.transferIsPossible(BankCardData.getCardFour(), SUM)).isFalse();
    }


    @Test
    public void transfer_is_not_possible_test2() {
        BankCard cardFive = BankCard.builder()
                .number(NUMBER_OF_CARD_5)
                .cvv(CVV_5)
                .balance(BALANCE_5)
                .validTill(VALID_TILL_5)
                .build();
        UnknownCardException unknownCardException = assertThrows(UnknownCardException.class,
                () -> cardService.transferIsPossible(cardFive, SUM));

        assertThat(unknownCardException).isNotNull();
        assertThat(unknownCardException.getMessage()).isEqualTo("Перевод с неизвестной карты");
    }
}
