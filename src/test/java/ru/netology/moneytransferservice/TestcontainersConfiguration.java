package ru.netology.moneytransferservice;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.GenericContainer;

import java.util.List;

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

    @Bean
    @ServiceConnection
    public GenericContainer<?> transferMoneyServiceContainer() {
        GenericContainer<?> container = new GenericContainer<>("money-transfer-backend:latest");
        container.addExposedPort(5500);
        container.setPortBindings(List.of("5500:5500"));
        return container;
    }
}
