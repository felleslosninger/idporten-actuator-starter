package no.idporten.actuator.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Slf4j
@Configuration
@AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE)
@ComponentScan(basePackages = {"no.idporten.actuator"})
public class IDPortenActuatorsAutoConfiguration {
    public IDPortenActuatorsAutoConfiguration() {
        log.info("Autoconfigured actuators with {}", this.getClass().getName());
    }
}
