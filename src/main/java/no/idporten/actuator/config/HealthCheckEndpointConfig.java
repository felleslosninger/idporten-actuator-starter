package no.idporten.actuator.config;

import lombok.extern.slf4j.Slf4j;
import no.idporten.actuator.monitor.ExternalDependencyHealthIndicator;
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
@Slf4j
@EnableConfigurationProperties(HealthCheckEndpointProperties.class)
public class HealthCheckEndpointConfig implements InitializingBean {

    private final HealthCheckEndpointProperties healthCheckEndpointProperties;

    public HealthCheckEndpointConfig(GenericApplicationContext applicationContext, HealthCheckEndpointProperties healthCheckEndpointProperties) {
        this.healthCheckEndpointProperties = healthCheckEndpointProperties;
        if (healthCheckEndpointProperties.getHealthEndpoints() != null) {
            healthCheckEndpointProperties.getHealthEndpoints().values()
                    .forEach(healthCheckEndpoint -> {
                        log.info("Registering health check for {}", healthCheckEndpoint.getName());
                        applicationContext.registerBean(healthCheckEndpoint.getName() + "HealthCheck", ExternalDependencyHealthIndicator.class, () -> {
                            RestTemplateBuilder builder = new RestTemplateBuilder();
                            RestTemplate restTemplate = builder.rootUri(healthCheckEndpoint.getBaseUri())
                                    .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                                    .setConnectTimeout(Duration.ofMillis(healthCheckEndpoint.getConnectTimeoutMs()))
                                    .setReadTimeout(Duration.ofMillis(healthCheckEndpoint.getReadTimeoutMs()))
                                    .build();
                            return new ExternalDependencyHealthIndicator(restTemplate, healthCheckEndpoint);
                        });
                    });
        }
    }

    @Override
    public void afterPropertiesSet() {
        if (healthCheckEndpointProperties.getHealthEndpoints() == null || healthCheckEndpointProperties.getHealthEndpoints().isEmpty()) {
            log.info("No health check endpoints configured");
        } else {
            log.info("Configured health check endpoints: {}", healthCheckEndpointProperties.getHealthEndpoints().keySet());
        }

    }
}
