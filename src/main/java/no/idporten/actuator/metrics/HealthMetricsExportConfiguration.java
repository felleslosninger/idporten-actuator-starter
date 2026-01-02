package no.idporten.actuator.metrics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.health.actuate.endpoint.HealthEndpoint;
import org.springframework.boot.health.contributor.Status;
import org.springframework.context.annotation.Configuration;

import static no.idporten.actuator.monitor.CustomStatus.DEGRADED;
import static no.idporten.actuator.monitor.CustomStatus.EXTERNAL_DEPENDENCY_DOWN;
import static org.springframework.boot.health.contributor.Status.OUT_OF_SERVICE;

@Configuration(proxyBeanMethods = false)
public class HealthMetricsExportConfiguration {

    public HealthMetricsExportConfiguration(MeterRegistry registry, HealthEndpoint healthEndpoint) {
        Gauge.builder("health", healthEndpoint, this::getStatusCode).strongReference(true).register(registry);
    }

    private int getStatusCode(HealthEndpoint health) {
        Status status = health.health().getStatus();
        if (Status.UP.equals(status)) {
            return 0;
        }
        if (EXTERNAL_DEPENDENCY_DOWN.equals(status)) {
            return 1;
        }
        if (DEGRADED.equals(status)) {
            return 1;
        }
        if (OUT_OF_SERVICE.equals(status)) {
            return 1;
        }
        if (Status.DOWN.equals(status)) {
            return 2;
        }
        return 100; // Unknown status
    }
}
