package no.idporten.actuator.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"no.idporten.actuator"})
@PropertySource("classpath:/no/idporten/actuator/config/application-actuator.properties")
public class IDPortenActuatorsAutoConfiguration {

    private final Logger log = LoggerFactory.getLogger(IDPortenActuatorsAutoConfiguration.class);

    public IDPortenActuatorsAutoConfiguration() {
        log.info("Autoconfigured actuators with {}", this.getClass().getName());
    }

}
