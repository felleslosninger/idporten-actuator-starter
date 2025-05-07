package no.idporten.actuator.config;

import no.idporten.actuator.monitor.ExternalDependencyHealthIndicator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("health")
class HealthCheckConfigTest {

    @Autowired
    GenericApplicationContext applicationContext;

    @Test
    @DisplayName("Should create a health check bean for each configured health check")
    void healthChecksAreConfigured() {
        assertEquals(1, applicationContext.getBeanNamesForType(ExternalDependencyHealthIndicator.class).length);
        assertNotNull(applicationContext.getBeanDefinition("exampleHealthCheck"));
        ExternalDependencyHealthIndicator exampleHealthCheck = applicationContext.getBean("exampleHealthCheck", ExternalDependencyHealthIndicator.class);
        assertEquals("SOMMER", exampleHealthCheck.getDownStatusMapping());
    }

}
