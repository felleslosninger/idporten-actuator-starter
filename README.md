# idporten-actuators

[![Maven build status](https://github.com/felleslosninger/idporten-actuator-starter/actions/workflows/call-maventests.yml/badge.svg)](https://github.com/felleslosninger/idporten-actuator-starter/actions/workflows/call-maventests.yml)

This library is a Spring Boot starter defining common actuators in ID-porten. The actuators will default run on port 8090. Supported:

* /info
* /version
* /prometheus (only default config)

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

You can override/extend the default settings in your application application*.yaml file, but make sure the default endpoints still work as intended

## Porting from own implementation

- remove old info/version implementations under e.g. /actuator
- the actuators spring starter can be replaced with the idporten-actuator-starter which includes the former.
- prometheus dependency can be removed  
- any project.version or info.version can be removed from config.
- remember to check if the build-info goal is set as described in the Configuration section
- TESTS: there might be issues with the tests since the management.server.port is set in the lib config. It's safest to set management.server.port=0 in the test application.yaml to make sure it's set to a random port and that you don't need as many test adjustments. look at the tests in maskinporten for tips.
- you _may_ use the IDPortenActuatorWebSecurityProperties in place of the WebSecurityProperties, if you have one. Then you don't have to worry about adding new endpoints if the library adds a new one.