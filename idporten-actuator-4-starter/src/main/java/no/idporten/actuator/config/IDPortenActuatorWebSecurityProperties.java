package no.idporten.actuator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.List;

/**
    Optional property object to use in the consumer application's websecurity config
 */
@Configuration
@PropertySource("classpath:/no/idporten/actuator/config/application-actuator.properties")
@ConfigurationProperties(ignoreInvalidFields = true, prefix = "idporten-actuators.security")
public class IDPortenActuatorWebSecurityProperties {

    private final List<String> allowedList = new ArrayList<>();

    public List<String> getAllowedList(){
        return this.allowedList;
    }
}
