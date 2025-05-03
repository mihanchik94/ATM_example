package org.example.ATM_example;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresSQLTestContainerExtension implements BeforeAllCallback, AfterAllCallback {
    public static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>("postgres:14.0")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        POSTGRE_SQL_CONTAINER.start();

        System.setProperty("spring.datasource.url", POSTGRE_SQL_CONTAINER.getJdbcUrl());
        System.setProperty("spring.datasource.username", POSTGRE_SQL_CONTAINER.getUsername());
        System.setProperty("spring.datasource.password", POSTGRE_SQL_CONTAINER.getPassword());
        System.setProperty("spring.liquibase.change-log", "classpath:/db.changelog/changelog-test.xml");

    }

    @Override
    public void afterAll(ExtensionContext extensionContext) {

    }
}
