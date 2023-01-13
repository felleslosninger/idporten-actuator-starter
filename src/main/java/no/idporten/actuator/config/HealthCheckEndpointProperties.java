package no.idporten.actuator.config;

import no.idporten.actuator.monitor.HealthCheckEndpoint;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

@Validated
@ConfigurationProperties(prefix = "external-dependency-health-checks")
public record HealthCheckEndpointProperties(
        Map<String, @Valid HealthCheckEndpoint> healthEndpoints)
        implements Serializable {

    public Map<String, HealthCheckEndpoint> healthEndpoints() {
        return healthEndpoints != null ? healthEndpoints : Collections.emptyMap();
    }
}
