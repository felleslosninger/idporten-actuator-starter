# idporten-actuators

[![Maven build status](https://github.com/felleslosninger/idporten-actuator-starter/actions/workflows/call-maventests.yml/badge.svg)](https://github.com/felleslosninger/idporten-actuator-starter/actions/workflows/call-maventests.yml)

This library is a Spring Boot starter defining common actuators in ID-porten. The actuators must run on port 8090.
Supported:

Custom endpoints for
* /info
* /version

Upcoming:

* /health (default implementation for now)

## Requirements

To build the application you need:

* JDK 1.8 (to support application with older Java versions)
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
    management.server.port=8090
    management.endpoints.web.base-path=/
    management.endpoints.web.exposure.include=info,version,prometheus,health
```

- you _may_ use the IDPortenActuatorWebSecurityProperties in place of the WebSecurityProperties, if you have one. Then
  you don't have to worry about adding new endpoints if the library adds a new one.
    - default config is idporten-actuators.security.allowed-list=/health/**,/version,/info,/prometheus, but you can
      override this in your application.yaml if you want to use the utility class