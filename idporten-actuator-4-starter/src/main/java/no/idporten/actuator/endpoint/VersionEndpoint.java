package no.idporten.actuator.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A simplified version endpoint
 * Version can also be found in the info-endpoint.
 */
@Component
@Endpoint(id = "version")
public class VersionEndpoint {

    private static final String VERSION = "version";
    private final Optional<BuildProperties> buildProperties;

    public VersionEndpoint(Optional<BuildProperties> buildProperties) {
        this.buildProperties = buildProperties;
    }

    @ReadOperation
    public Map<String, Object> version() {
        Map<String, Object> versionMap = new HashMap<>();
        versionMap.put(VERSION, buildProperties.isPresent() ?
                buildProperties.get().getVersion() :
                "unknown");
        return versionMap;
    }
}