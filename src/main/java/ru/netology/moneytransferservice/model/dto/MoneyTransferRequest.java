package ru.netology.moneytransferservice.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MoneyTransferRequest {

    @JsonProperty("cardFromNumber")
    private String cardFromNumber;

    @JsonProperty("cardFromValidTill")
    private String cardFromValidTill;

    @JsonProperty("cardFromCVV")
    private String cardFromCVV;

    @JsonProperty("cardToNumber")
    private String cardToNumber;

    @JsonProperty("amount")
    private Amount amount;
}
