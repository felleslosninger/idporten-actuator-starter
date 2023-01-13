package no.idporten.actuator.monitor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ExternalDependencyHealthIndicatorTest {

    @Mock
    private HealthCheckEndpoint healthCheckEndpoint;

    @Spy
    @InjectMocks
    private ExternalDependencyHealthIndicator externalDependencyHealthIndicator;

    @BeforeEach
    void init() {
        //default
        when(healthCheckEndpoint.getDownStatus()).thenReturn(CustomStatus.EXTERNAL_DEPENDENCY_DOWN.getCode());
    }

    @Test
    void testHealthCheckOk() {
        doReturn(new HealthResponse(Status.UP.getCode())).when(externalDependencyHealthIndicator).getIntegrationHealth();
        Health health = externalDependencyHealthIndicator.health();
        assertEquals(Status.UP, health.getStatus());
    }

    @Test
    void testHealthCheckNotOk() {
        doReturn(new HealthResponse(Status.DOWN.getCode())).when(externalDependencyHealthIndicator).getIntegrationHealth();
        Health health = externalDependencyHealthIndicator.health();
        assertEquals(CustomStatus.EXTERNAL_DEPENDENCY_DOWN, health.getStatus());
    }

    @Test
    void testHealthCheckNotOk2() {
        doThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND)).when(externalDependencyHealthIndicator).getIntegrationHealth();
        Health health = externalDependencyHealthIndicator.health();
        assertEquals(CustomStatus.EXTERNAL_DEPENDENCY_DOWN, health.getStatus());
    }

    @Test
    void testHealthCheckNotOkWithMappedDownStatus() {
        when(healthCheckEndpoint.getDownStatus()).thenReturn("BANAN");
        doReturn(new HealthResponse(Status.DOWN.getCode())).when(externalDependencyHealthIndicator).getIntegrationHealth();
        Health health = externalDependencyHealthIndicator.health();
        assertEquals("BANAN", health.getStatus().getCode());
    }

    @Test
    void testHealthCheckNotOkWithMappedDownStatus2() {
        when(healthCheckEndpoint.getDownStatus()).thenReturn("BANAN");
        doThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND)).when(externalDependencyHealthIndicator).getIntegrationHealth();
        Health health = externalDependencyHealthIndicator.health();
        assertEquals("BANAN", health.getStatus().getCode());
    }

    @Test
    void testHealthCheckDegraded() {
        doReturn(new HealthResponse(CustomStatus.DEGRADED.getCode())).when(externalDependencyHealthIndicator).getIntegrationHealth();
        Health health = externalDependencyHealthIndicator.health();
        assertEquals(CustomStatus.DEGRADED, health.getStatus());
    }
}
