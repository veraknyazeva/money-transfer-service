package ru.netology.moneytransferservice;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import ru.netology.moneytransferservice.model.dto.Amount;
import ru.netology.moneytransferservice.model.dto.ConfirmOperationRequest;
import ru.netology.moneytransferservice.model.dto.MoneyTransferRequest;
import ru.netology.moneytransferservice.model.dto.MoneyTransferServiceResponse;
import ru.netology.moneytransferservice.model.entity.BankCard;
import ru.netology.moneytransferservice.repository.data.BankCardData;
import ru.netology.moneytransferservice.repository.impl.InMemoryCardRepository;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = {MoneyTransferServiceApplication.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MoneyTransferServiceApplicationTest {

    private static final BankCard cardOne = BankCardData.getCardOne();
    private static final BankCard cardTwo = BankCardData.getCardTwo();
    private static final BankCard cardFour = BankCardData.getCardFour();
    private static final String LOCALHOST_5500_TRANSFER = "http://localhost:5500/transfer";
    private static final String LOCALHOST_5500_CONFIRM = "http://localhost:5500/confirmOperation";
    private static final String NUMBER_OF_CARD_5 = "2345622222222222";
    private static final String CVV_5 = "555";
    private static final String VALID_TILL_5 = "09/29";
    private static final Integer BALANCE_5 = 100000;
    private static final Integer SUM = 1000;

    private final TestRestTemplate testRestTemplate = new TestRestTemplate();
    private static String operationId = "";

    @Autowired
    private InMemoryCardRepository repository;

    @Test
    @Order(1)
    void contextLoads() {
        assertThat(testRestTemplate).isNotNull();
    }

    @Test
    @Order(2)
    public void transfer_controller_test() {
        ResponseEntity<MoneyTransferServiceResponse> response =
                testRestTemplate.postForEntity(LOCALHOST_5500_TRANSFER,
                        MoneyTransferRequest.builder()
                                .cardFromNumber(cardOne.getNumber())
                                .cardFromCVV(cardOne.getCvv())
                                .cardFromValidTill(cardOne.getValidTill())
                                .cardToNumber(cardTwo.getNumber())
                                .amount(
                                        Amount.builder()
                                                .currency("RUB")
                                                .value(100)
                                                .build()
                                )
                                .build(),
                        MoneyTransferServiceResponse.class
                );
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isNotNull();
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        MoneyTransferServiceResponse body = response.getBody();
        assertThat(body.getOperationId()).isNotNull();
        assertThat(body.getOperationId()).isNotBlank();
        operationId = body.getOperationId();
    }

    @Test
    @Order(3)
    public void transfer_confirm_controller_test() {
        ResponseEntity<MoneyTransferServiceResponse> response =
                testRestTemplate.postForEntity(LOCALHOST_5500_CONFIRM,
                        ConfirmOperationRequest.builder()
                                .operationId(operationId)
                                .code("2222")
                                .build(),
                        MoneyTransferServiceResponse.class
                );
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isNotNull();
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        MoneyTransferServiceResponse body = response.getBody();
        assertThat(body.getOperationId()).isNotNull();
        assertThat(body.getOperationId()).isNotBlank();
    }

    @Test
    @Order(4)
    public void transfer_controller_negative_test() {
        ResponseEntity<MoneyTransferServiceResponse> response =
                testRestTemplate.postForEntity(LOCALHOST_5500_TRANSFER,
                        MoneyTransferRequest.builder()
                                .cardFromNumber(NUMBER_OF_CARD_5)
                                .cardFromCVV(CVV_5)
                                .cardFromValidTill(VALID_TILL_5)
                                .cardToNumber(cardTwo.getNumber())
                                .amount(
                                        Amount.builder()
                                                .currency("RUB")
                                                .value(100)
                                                .build()
                                )
                                .build(),
                        MoneyTransferServiceResponse.class
                );
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        MoneyTransferServiceResponse body = response.getBody();
        assertThat(body.getId()).isNotNull();
        assertThat(body.getId()).isEqualTo(102);
    }

    @Test
    @Order(5)
    public void transfer_controller_negative_test2() {
        ResponseEntity<MoneyTransferServiceResponse> response =
                testRestTemplate.postForEntity(LOCALHOST_5500_TRANSFER,
                        MoneyTransferRequest.builder()
                                .cardFromNumber(cardFour.getNumber())
                                .cardFromCVV(cardFour.getCvv())
                                .cardFromValidTill(cardFour.getValidTill())
                                .cardToNumber(cardTwo.getNumber())
                                .amount(
                                        Amount.builder()
                                                .currency("RUB")
                                                .value(100)
                                                .build()
                                )
                                .build(),
                        MoneyTransferServiceResponse.class
                );
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
        MoneyTransferServiceResponse body = response.getBody();
        assertThat(body.getId()).isNotNull();
        assertThat(body.getId()).isEqualTo(101);
    }

    @Test
    @Order(6)
    public void transfer_confirm_controller_negative_test(){
        ResponseEntity<MoneyTransferServiceResponse> responseTransfer =
                testRestTemplate.postForEntity(LOCALHOST_5500_TRANSFER,
                        MoneyTransferRequest.builder()
                                .cardFromNumber(cardOne.getNumber())
                                .cardFromCVV(cardOne.getCvv())
                                .cardFromValidTill(cardOne.getValidTill())
                                .cardToNumber(cardTwo.getNumber())
                                .amount(
                                        Amount.builder()
                                                .currency("RUB")
                                                .value(100)
                                                .build()
                                )
                                .build(),
                        MoneyTransferServiceResponse.class
                );
        cardOne.setBalance(0);
        repository.findByNumber(cardOne.getNumber()).setBalance(0);
        ResponseEntity<MoneyTransferServiceResponse> responseConfirm =
                testRestTemplate.postForEntity(LOCALHOST_5500_CONFIRM,
                        ConfirmOperationRequest.builder()
                                .operationId(responseTransfer.getBody().getOperationId())
                                .code("2222")
                                .build(),
                        MoneyTransferServiceResponse.class
                );
        assertThat(responseTransfer).isNotNull();
        assertThat(responseTransfer.getStatusCode()).isNotNull();
        assertThat(responseTransfer.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseTransfer.getBody()).isNotNull();

        assertThat(responseConfirm).isNotNull();
        assertThat(responseConfirm.getStatusCode()).isNotNull();
        assertThat(responseConfirm.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(responseConfirm.getBody()).isNotNull();
        MoneyTransferServiceResponse body = responseConfirm.getBody();
        assertThat(body.getId()).isNotNull();
        assertThat(body.getId()).isEqualTo(101);
    }
}
