# idporten-actuators

[![Maven build status](https://github.com/felleslosninger/idporten-actuator-starter/actions/workflows/call-maventests.yml/badge.svg)](https://github.com/felleslosninger/idporten-actuator-starter/actions/workflows/call-maventests.yml)

This library is a Spring Boot starter defining common actuators in ID-porten. Supported:

* /info
* /version
* /prometheus (only default config)

Upcoming:

* /health (default implementation)

## Requirements

To build the application you need:

* JDK 1.8 (to support application with older Java versions)
* Maven

## Configuration

Important: the project using this starter must configure the spring-boot-maven-plugin to include:

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

## Porting from own implementation

- remove old info/version implementations under e.g. /actuator
- the actuators spring starter can be replaced with the idporten-actuator-starter which includes the former.
- any project.version or info.version can be removed from config.
- remember to check if the build-info goal is set as described in the Configuration section