package no.idporten.actuator.config;

import no.idporten.actuator.monitor.ExternalDependencyHealthIndicator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.GenericApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class NoHealthCheckConfigTest {

    @Autowired
    GenericApplicationContext applicationContext;

    @Test
    @DisplayName("Should not create any health check beans when no health checks are configured and not throw exception")
    void dontfailIfNoHealthChecksConfigures() {
        assertEquals(0, applicationContext.getBeanNamesForType(ExternalDependencyHealthIndicator.class).length);
    }

}
