# idporten-actuators

[![Maven build status](https://github.com/felleslosninger/idporten-actuator-starter/actions/workflows/call-maventests.yml/badge.svg)](https://github.com/felleslosninger/idporten-actuator-starter/actions/workflows/call-maventests.yml)

This repository contains two Spring Boot starter modules providing common actuators in ID-porten. The actuators must run on port 8090.
Supported:

Custom endpoints for
* /info
* /version

ID-porten default configuration for:

* /health

## Provided artifacts
From stable version ``3.0.0`` the following artifacts are provided:

| Package Coordinates                                | Description     | 
|----------------------------------------------------|-----------------|
| `no.idporten.actuator.idporten-actuator-starter`   | Spring Boot 3.x |
| `no.idporten.actuator.idporten-actuator-4-starter` | Spring Boot 4.x |


## Requirements

To build the application you need:

* JDK 21 (to support application with older Java versions)
* Maven

## Configuration

Important: To get the version the project using this starter must configure the spring-boot-maven-plugin to include:

```
<executions>
    <execution>
        <goals>
            <goal>build-info</goal>
        </goals>
    </execution>
</executions>
```

This will generate a file in the jar-file called build.properties. If this is not set, the version will be set to "
unknown".

## Overriding default settings

You can override/extend the default settings in your application application*.yaml file, but make sure the default
endpoints still work as intended

## Porting from own implementation

- remove old info/version implementations under e.g. /actuator
- the actuators spring starter can be replaced with the idporten-actuator-starter which includes the former.
- prometheus dependency can be removed
- any project.version or info.version can be removed from config.
- remember to check if the build-info goal is set as described in the Configuration section
- ***IMPORTANT:*** Add minimum the following configuration in your application.yaml:
```
management:
  server:
    port: 8090
  endpoints:
    web:
      exposure:
        include: info,version,prometheus,health
      base-path: /
```

- TESTS: there might be issues with the tests if you don't have a "root" application.yaml in your test resources, since
  the management.server.port is set to other than default. It's safest to set ```management.server.port=``` in the test
  application.yaml to make sure it's set to a random port and that you don't need as many test adjustments.
- you _may_ use the IDPortenActuatorWebSecurityProperties in place of the WebSecurityProperties, for simple access
  management
    - default config is idporten-actuators.security.allowed-list=/health/**,/version,/info,/prometheus, but you can
      override this in your application.yaml if you want to use the utility class

## Configuring some simple http health checks

If your application has external dependencies, you can configure the health check for these as shown in the example
below.

```
external-dependency-health-checks:
  health-endpoints:
    maskinporten:
      name: maskinporten
      base-uri: ${maskinporten.oauth2.issuer}
      endpoint: /health
      connect-timeout-ms: 2000
      read-timeout-ms: 1000   
    other-api:
      name: otherApi
      base-uri: http://example.com
      endpoint: /health
      connect-timeout-ms: 2000
      read-timeout-ms: 1000
      map-down-status-to: DOWN  (optional. can be set to any string)
```

To avoid any unintended consequences, the health indicators will return UP, DEGRADED or EXTERNAL_DEPENDENCY_DOWN pr
default. You can configure something other that EXTERNAL_DEPENDENCY_DOWN by setting the down-status property in the
health endpoint config. All of which are 200 OK responses. Overriding the http status code can be done in the default
spring management settings, for example:

    ```
    management:
      endpoint:
        health:
          status:
            http-mapping:
              DEGRADED: 200
              EXTERNAL_DEPENDENCY_DOWN: 503
    ```

If you want to use the Custom health statuses, it is important to add them to the status order depending on your
application's requirements:

```
management:
  endpoint:
    health:
      status:
        order: DOWN, OUT_OF_SERVICE, UNKNOWN, DEGRADED, EXTERNAL_DEPENDENCY_DOWN, UP
```

### Description of health statuses

| Status | Description | Status type |
| ----------- | ----------- | ----------- |
| DOWN | The application is down and not working at all | Default |
| OUT_OF_SERVICE | The application or subsystem has been taken out of service and should not be used. | Default status |
| UNKNOWN | The application status is unknown | Default |
| DEGRADED | The application has functionally or parts not working, but working okay for most cases | Custom |
| EXTERNAL_DEPENDENCY_DOWN | An external dependency the application depends on is down, e.g. an API | Custom |
| UP | The application is up and working fine | Default |

For more info see Spring Boot doc: https://docs.spring.io/spring-boot/api/java/org/springframework/boot/actuate/health/Status.html
