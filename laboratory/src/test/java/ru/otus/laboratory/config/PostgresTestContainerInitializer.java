package ru.otus.laboratory.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.PostgreSQLContainer;

@Slf4j
@Configuration
public class PostgresTestContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final PostgreSQLContainer<?> POSTGRES_CONTAINER = initContainer();

    private static PostgreSQLContainer<?> initContainer() {
        PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:17")
                .withDatabaseName("test_postgres")
                .withUsername("test")
                .withPassword("test");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (container.isRunning()) {
                container.stop();
            }
        }));

        return container;
    }

    @Override
    public void initialize(ConfigurableApplicationContext ctx) {
        if (POSTGRES_CONTAINER.isRunning()) {
            POSTGRES_CONTAINER.stop();
        }

        POSTGRES_CONTAINER.start();

        TestPropertyValues.of(
                "spring.datasource.url=" + POSTGRES_CONTAINER.getJdbcUrl(),
                "spring.datasource.username=" + POSTGRES_CONTAINER.getUsername(),
                "spring.datasource.password=" + POSTGRES_CONTAINER.getPassword()
        ).applyTo(ctx.getEnvironment());
    }

}
