package no.idporten.actuator.config;

import no.idporten.actuator.monitor.HealthCheckEndpoint;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@ConfigurationProperties(prefix = "external-dependency-health-checks")
public class HealthCheckEndpointProperties implements Serializable {
    private Map<String, HealthCheckEndpoint> healthEndpoints;

    public HealthCheckEndpointProperties(Map<String, HealthCheckEndpoint> healthEndpoints) {
        this.setHealthEndpoints(healthEndpoints);
    }

    public void setHealthEndpoints(Map<String, HealthCheckEndpoint> healthEndpoints) {
        this.healthEndpoints = Objects.requireNonNullElse(healthEndpoints, Collections.emptyMap());
    }

    public Map<String, HealthCheckEndpoint> healthEndpoints() {
        return healthEndpoints;
    }
}
