package no.idporten.actuator.endpoint;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Adds java environment info to the /info endpoint
 * All environment data can be found in the /env endpoint, if exposed.
 */
@Component
public class InfoEndpointJavaContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("java", getJavaInfo());
    }

    protected Map<String, String> getJavaInfo() {
        Map<String, String> javaInfoMap = new HashMap<>();
        javaInfoMap.put("vendor", System.getProperty("java.vendor"));
        javaInfoMap.put("runtime.version", System.getProperty("java.runtime.version"));
        javaInfoMap.put("home", System.getProperty("java.home"));
        return javaInfoMap;
    }
}
