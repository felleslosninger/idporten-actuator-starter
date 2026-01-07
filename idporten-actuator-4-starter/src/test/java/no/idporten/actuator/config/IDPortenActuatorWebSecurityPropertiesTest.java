package no.idporten.actuator.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class IDPortenActuatorWebSecurityPropertiesTest {

    @Autowired
    private IDPortenActuatorWebSecurityProperties idPortenActuatorWebSecurityProperties;

    @Test
    void checkIfAllowedListSet(){
        assertNotNull(idPortenActuatorWebSecurityProperties.getAllowedList());
        assertEquals(4, idPortenActuatorWebSecurityProperties.getAllowedList().size());
    }

}
