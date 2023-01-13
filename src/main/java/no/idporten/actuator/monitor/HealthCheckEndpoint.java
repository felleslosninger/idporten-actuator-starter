package no.idporten.actuator.monitor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
@Validated
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HealthCheckEndpoint implements Serializable {

    @NotNull
    private String name;
    @NotNull
    private String baseUri;
    @NotNull
    private String endpoint;
    @Min(1)
    @Builder.Default
    private int connectTimeoutMs = 2000;
    @Min(1)
    @Builder.Default
    private int readTimeoutMs = 1000;

    //the status that will be set if the endpoint returns anything other than UP or DEGRADED
    @Builder.Default
    private String downStatus = CustomStatus.EXTERNAL_DEPENDENCY_DOWN.getCode();


}
