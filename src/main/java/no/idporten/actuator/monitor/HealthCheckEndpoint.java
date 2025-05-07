package no.idporten.actuator.monitor;

import org.springframework.boot.context.properties.bind.DefaultValue;

import java.io.Serializable;
import java.util.Objects;

public record HealthCheckEndpoint(
                                  String name,
                                  @DefaultValue("true") boolean enabled,
                                  String baseUri,
                                  String endpoint,
                                  long connectTimeoutMs,
                                  long readTimeoutMs,
                                  //the status that will be set if the endpoint returns anything other than UP or DEGRADED
                                  String mapDownStatusTo)
        implements Serializable {

    public HealthCheckEndpoint {
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(baseUri, "baseUri must not be null");
        Objects.requireNonNull(endpoint, "endpoint must not be null");
    }

    public long connectTimeoutMs() {
        return this.connectTimeoutMs != -1 ? this.connectTimeoutMs : 2000;
    }

    public long readTimeoutMs() {
        return this.readTimeoutMs != -1 ? this.readTimeoutMs : 1000;
    }

    public String downStatus() {
        return this.mapDownStatusTo != null ? this.mapDownStatusTo : CustomStatus.EXTERNAL_DEPENDENCY_DOWN.getCode();
    }

}
