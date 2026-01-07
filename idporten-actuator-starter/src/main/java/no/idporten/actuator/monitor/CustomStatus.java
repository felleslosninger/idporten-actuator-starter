package no.idporten.actuator.monitor;

import org.springframework.boot.actuate.health.Status;

import java.io.Serializable;

public class CustomStatus implements Serializable {
    public static final Status DEGRADED = new Status("DEGRADED");
    public static final Status EXTERNAL_DEPENDENCY_DOWN = new Status("EXTERNAL_DEPENDENCY_DOWN");

    private CustomStatus() {
    }
}
