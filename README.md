# idporten-actuators

[![Maven build status](https://github.com/felleslosninger/idporten-actuators/actions/workflows/call-maventests.yml/badge.svg)](https://github.com/felleslosninger/idporten-im-api-starter/actions/workflows/call-maventests.yml)
[![Build image](https://github.com/felleslosninger/idporten-actuators/actions/workflows/call-buildimage.yml/badge.svg)](https://github.com/felleslosninger/idporten-im-api-starter/actions/workflows/call-buildimage.yml)

This library is a Spring Boot starter defining common actuators in ID-porten. Supported:

* /info
* /version

## Requirements

To build the application you need:

* JDK 1.8 (to support application with older Java versions)
* Maven

## Usage

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

## Configuration

...