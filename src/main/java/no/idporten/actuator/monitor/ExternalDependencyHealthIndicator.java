package no.idporten.actuator.monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.boot.health.contributor.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

public class ExternalDependencyHealthIndicator implements HealthIndicator {

    private final Logger log = LoggerFactory.getLogger(ExternalDependencyHealthIndicator.class);
    private final RestClient restClient;
    private final HealthCheckEndpoint healthCheckEndpoint;

    public ExternalDependencyHealthIndicator(RestClient restClient, HealthCheckEndpoint healthCheckEndpoint) {
        this.restClient = restClient;
        this.healthCheckEndpoint = healthCheckEndpoint;
        log.info("Created health indicator for {}", healthCheckEndpoint.name());
    }

    @Override
    public Health health() {
        try {
            HealthResponse health = this.getIntegrationHealth();
            if (Status.UP.getCode().equals(health.status())) {
                return Health.up().build();
            } else if (CustomStatus.DEGRADED.getCode().equals(health.status())) {
                return Health.status(CustomStatus.DEGRADED).build();
            } else {
                return Health.status(healthCheckEndpoint.downStatus()).build();
            }
        } catch (HttpClientErrorException e) {
            log.error("Health check to {} failed with exception {}", healthCheckEndpoint.name(), e.getMessage(), e);
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.error("Wrong configuration of {} health endpoint?", healthCheckEndpoint.name());
            }
            return Health.status(healthCheckEndpoint.downStatus()).withException(e).build();
        } catch (Exception e) {
            log.error("Health check to {} failed with exception {}", healthCheckEndpoint.name(), e.getMessage(), e);
            return Health.status(healthCheckEndpoint.downStatus()).withException(e).build();
        }
    }

    protected HealthResponse getIntegrationHealth() {
        ResponseEntity<HealthResponse> forEntity = this.restClient.get()
                .uri(healthCheckEndpoint.endpoint())
                .retrieve()
                .toEntity(HealthResponse.class);

        if (forEntity.getBody() != null) {
            return forEntity.getBody();
        } else {
            return new HealthResponse(Status.DOWN.getCode());
        }
    }

    public String getName() {
        return this.healthCheckEndpoint.name();
    }

    public String getDownStatusMapping() {
        return healthCheckEndpoint.downStatus();
    }

}
