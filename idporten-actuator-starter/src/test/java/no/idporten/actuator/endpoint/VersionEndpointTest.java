package no.idporten.actuator.endpoint;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.info.BuildProperties;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VersionEndpointTest {

    @Test
    @DisplayName("When calling version, return the version included in the build properteis")
    void testGetVersion() {
        BuildProperties buildProperties = mock(BuildProperties.class);
        when(buildProperties.getVersion()).thenReturn("1.0");
        VersionEndpoint versionEndpoint = new VersionEndpoint(Optional.of(buildProperties));
        assertEquals(1, versionEndpoint.version().size(), "Only one field should show here.");
        assertEquals("1.0", versionEndpoint.version().get("version"));
    }

    @Test
    @DisplayName("When calling version, and build properties not set, return unknown")
    void testGetVersionUnknown() {
        VersionEndpoint versionEndpoint = new VersionEndpoint(Optional.empty());
        assertEquals(1, versionEndpoint.version().size(), "Only one field should show here.");
        assertEquals("unknown", versionEndpoint.version().get("version"));
    }
}
