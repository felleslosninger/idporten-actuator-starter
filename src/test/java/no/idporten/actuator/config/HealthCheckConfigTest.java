package no.idporten.actuator.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("health")
public class HealthCheckConfigTest {

    @Autowired
    GenericApplicationContext applicationContext;

    @Test
    void healthChecksAreConfigured() {
        assertNotNull(applicationContext.getBeanDefinition("exampleHealthCheck"));
    }

}
