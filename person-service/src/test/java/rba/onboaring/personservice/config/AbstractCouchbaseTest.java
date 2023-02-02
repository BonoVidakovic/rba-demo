package rba.onboaring.personservice.config;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import rba.onboaring.personservice.PersonServiceApplication;

@SpringBootTest(
        classes = {
                PersonServiceApplication.class,
        },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {AbstractCouchbaseTest.PostgresInitializer.class})
@Testcontainers
public abstract class AbstractCouchbaseTest {
    static private final String databaseName = "rba-integration-test";
    static private final String username = "user";
    static private final String password = "password";
    @Container
    final static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer()
            .withDatabaseName(databaseName)
            .withUsername(username)
            .withPassword(password);

    static class PostgresInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}