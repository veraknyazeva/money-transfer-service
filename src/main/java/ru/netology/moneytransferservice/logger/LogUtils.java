package ru.netology.moneytransferservice.logger;

import lombok.experimental.UtilityClass;

@UtilityClass
public class LogUtils {
    public static final String CARD_TRANSFER_UNSUCCESSFUL = "НЕУСПЕШНО";
    public static final String CARD_TRANSFER_LOG_FORMAT = "Перевод с карты: {} на карту: {}. сумма: {}, комиссия: {}. Результат: {}";
    public static final String CARD_NOT_FOUND_LOG_FORMAT = "Перевод с неизвестной карты: {}, результат: {}";
    public static final String CARD_TRANSFER_SUCCESSFUL = "УСПЕШНО";
    public static final String BALANCE_NOT_ENOUGH_CARD_TRANSFER_LOG_FORMAT = "Недостаточно средств на карте: {}, результат: {}";

}
