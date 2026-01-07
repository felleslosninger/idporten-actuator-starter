package no.idporten.actuator.monitor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HealthCheckEndpointTest {

    @Test
    void testConstructorWithValidParameters() {
        HealthCheckEndpoint endpoint = new HealthCheckEndpoint("name", true, "http://baseUri", "/endpoint", 1000, 2000, "DOWN");
        assertNotNull(endpoint);
    }

    @Test
    void testConstructorWithNullName() {
        Exception exception = assertThrows(NullPointerException.class, () -> new HealthCheckEndpoint(null, true, "http://baseUri", "/endpoint", 1000, 2000, "DOWN"));
        assertEquals("name must not be null", exception.getMessage());
    }

    @Test
    void testConstructorWithNullBaseUri() {
        Exception exception = assertThrows(NullPointerException.class, () -> new HealthCheckEndpoint("name", true, null, "/endpoint", 1000, 2000, "DOWN"));
        assertEquals("baseUri must not be null", exception.getMessage());
    }

    @Test
    void testConstructorWithNullEndpoint() {
        Exception exception = assertThrows(NullPointerException.class, () -> new HealthCheckEndpoint("name", true, "http://baseUri", null, 1000, 2000, "DOWN"));
        assertEquals("endpoint must not be null", exception.getMessage());
    }
}