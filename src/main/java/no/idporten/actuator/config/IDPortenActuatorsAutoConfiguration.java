package no.idporten.actuator.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
@AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE)
@ComponentScan(basePackages = {"no.idporten.actuator"})
public class IDPortenActuatorsAutoConfiguration {

    private final Logger log = LoggerFactory.getLogger(IDPortenActuatorsAutoConfiguration.class);

    public IDPortenActuatorsAutoConfiguration() {
        log.info("Autoconfigured actuators with {}", this.getClass().getName());
    }
}
