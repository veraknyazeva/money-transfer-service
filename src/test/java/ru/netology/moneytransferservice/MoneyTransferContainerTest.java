package ru.netology.moneytransferservice;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.netology.moneytransferservice.model.dto.Amount;
import ru.netology.moneytransferservice.model.dto.ConfirmOperationRequest;
import ru.netology.moneytransferservice.model.dto.MoneyTransferRequest;
import ru.netology.moneytransferservice.model.dto.MoneyTransferServiceResponse;
import ru.netology.moneytransferservice.model.entity.BankCard;
import ru.netology.moneytransferservice.repository.data.BankCardData;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestcontainersConfiguration.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MoneyTransferContainerTest {
    private static final BankCard cardOne = BankCardData.getCardOne();
    private static final BankCard cardTwo = BankCardData.getCardTwo();
    private static final String LOCALHOST_5500_TRANSFER = "http://localhost:5500/transfer";
    private static final String LOCALHOST_5500_CONFIRM = "http://localhost:5500/confirmOperation";

    private final TestRestTemplate testRestTemplate = new TestRestTemplate();
    private static String operationId = "";

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
}
