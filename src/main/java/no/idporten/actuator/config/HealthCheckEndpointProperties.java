package no.idporten.actuator.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import no.idporten.actuator.monitor.HealthCheckEndpoint;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Data
@Slf4j
@Validated
@ConfigurationProperties(prefix = "external-dependency-health-checks")
public class HealthCheckEndpointProperties {

    private Map<String, @Valid HealthCheckEndpoint> healthEndpoints = new HashMap<>();

}
