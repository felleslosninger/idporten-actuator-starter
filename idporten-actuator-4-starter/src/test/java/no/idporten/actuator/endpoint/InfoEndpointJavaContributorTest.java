package no.idporten.actuator.endpoint;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InfoEndpointJavaContributorTest {

    @Test
    @DisplayName("When adding java info include all the java properties that is required")
    void getJavaInfo() {
        InfoEndpointJavaContributor infoEndpointJavaContributor = new InfoEndpointJavaContributor();
        Map<String, String> javaInfo = infoEndpointJavaContributor.getJavaInfo();
        assertAll(
                () -> assertTrue(javaInfo.containsKey("vendor")),
                () -> assertTrue(javaInfo.containsKey("runtime.version")),
                () -> assertTrue(javaInfo.containsKey("home")));
    }

}
