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
public class MoneyTransferServiceResponse {
    @JsonProperty("operationId")
    private String operationId;

    @JsonProperty("message")
    private String message;

    @JsonProperty("id")
    private Integer id;
}
