package no.idporten.actuator.monitor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
public class ExternalDependencyHealthIndicator implements HealthIndicator {

    private final RestTemplate restTemplate;
    private final HealthCheckEndpoint healthCheckEndpoint;

    @Override
    public Health health() {
        try {
            HealthResponse health = this.getIntegrationHealth();
            if (Status.UP.getCode().equals(health.getStatus())) {
                return Health.up().build();
            } else if (CustomStatus.DEGRADED.getCode().equals(health.getStatus())) {
                return Health.status(CustomStatus.DEGRADED).build();
            } else {
                return Health.status(CustomStatus.EXTERNAL_DEPENDENCY_DOWN).withDetail("status", health.getStatus()).build();
            }
        } catch (HttpClientErrorException e) {
            log.error("Health check to {} failed with exception {}", healthCheckEndpoint.getName(), e.getMessage(), e);
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.error("Wrong configuration of {} health endpoint?", healthCheckEndpoint.getName());
            }
            return Health.status(CustomStatus.EXTERNAL_DEPENDENCY_DOWN).withException(e).build();
        } catch (Exception e) {
            log.error("Health check to {} failed with exception {}", healthCheckEndpoint.getName(), e.getMessage(), e);
            return Health.status(CustomStatus.EXTERNAL_DEPENDENCY_DOWN).withException(e).build();
        }
    }

    protected HealthResponse getIntegrationHealth() {
        ResponseEntity<HealthResponse> forEntity = this.restTemplate.getForEntity(healthCheckEndpoint.getEndpoint(),
                HealthResponse.class);
        if (forEntity.getBody() != null) {
            return forEntity.getBody();
        } else {
            return new HealthResponse(Status.DOWN.getCode());
        }
    }

    public String getName() {
        return this.healthCheckEndpoint.getName();
    }

}
