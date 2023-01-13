package no.idporten.actuator.monitor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public record HealthCheckEndpoint(@NotNull
                                  String name,
                                  @NotNull
                                  String baseUri,
                                  @NotNull
                                  String endpoint,
                                  long connectTimeoutMs,
                                  long readTimeoutMs,
                                  //the status that will be set if the endpoint returns anything other than UP or DEGRADED
                                  String downStatus)
        implements Serializable {

    public long connectTimeoutMs() {
        return this.connectTimeoutMs != -1 ? this.connectTimeoutMs : 2000;
    }

    public long readTimeoutMs() {
        return this.readTimeoutMs != -1 ? this.readTimeoutMs : 1000;
    }

    public String downStatus() {
        return this.downStatus != null ? this.downStatus : CustomStatus.EXTERNAL_DEPENDENCY_DOWN.getCode();
    }

}
