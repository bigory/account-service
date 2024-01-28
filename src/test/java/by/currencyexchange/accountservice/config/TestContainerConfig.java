package by.currencyexchange.accountservice.config;


import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;


@TestConfiguration(proxyBeanMethods = false)
public class TestContainerConfig {

    @Bean
    @ServiceConnection
    public PostgreSQLContainer<?> getPostgreSQLContainer() {
        return new PostgreSQLContainer<>("postgres:latest")
                .withUsername("user")
                .withDatabaseName("test")
                .withPassword("pass");
    }

}



