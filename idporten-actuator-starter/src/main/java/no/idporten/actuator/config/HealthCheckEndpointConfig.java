package no.idporten.actuator.config;

import no.idporten.actuator.monitor.ExternalDependencyHealthIndicator;
import no.idporten.actuator.monitor.HealthCheckEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;


@Configuration
@EnableConfigurationProperties(HealthCheckEndpointProperties.class)
public class HealthCheckEndpointConfig implements InitializingBean {
    private final Logger log = LoggerFactory.getLogger(HealthCheckEndpointConfig.class);
    private final HealthCheckEndpointProperties healthCheckEndpointProperties;

    public HealthCheckEndpointConfig(GenericApplicationContext applicationContext, HealthCheckEndpointProperties healthCheckEndpointProperties) {
        this.healthCheckEndpointProperties = healthCheckEndpointProperties;
        if (healthCheckEndpointProperties.healthEndpoints() != null) {
            healthCheckEndpointProperties.healthEndpoints().values()
                    .stream()
                    .filter(HealthCheckEndpoint::enabled)
                    .forEach(healthCheckEndpoint -> {
                        log.info("Registering health check for {}", healthCheckEndpoint.name());
                        applicationContext.registerBean(healthCheckEndpoint.name() + "HealthCheck", ExternalDependencyHealthIndicator.class, () -> {
                            RestTemplateBuilder builder = new RestTemplateBuilder();
                            RestTemplate restTemplate = builder.rootUri(healthCheckEndpoint.baseUri())
                                    .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                                    .setConnectTimeout(Duration.ofMillis(healthCheckEndpoint.connectTimeoutMs()))
                                    .setReadTimeout(Duration.ofMillis(healthCheckEndpoint.readTimeoutMs()))
                                    .build();
                            return new ExternalDependencyHealthIndicator(restTemplate, healthCheckEndpoint);
                        });
                    });
        }
    }

    @Override
    public void afterPropertiesSet() {
        if (healthCheckEndpointProperties.healthEndpoints() != null && !healthCheckEndpointProperties.healthEndpoints().isEmpty()) {
            log.info("Configured health check endpoints: {}", healthCheckEndpointProperties.healthEndpoints().keySet());
        }
    }
}
