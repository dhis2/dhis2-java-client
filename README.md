# DHIS 2 Java Client

DHIS 2 API client for Java and Spring. The client allows you to create, update and
retrieve information from DHIS 2.

### Getting started

To use `dhis2-java-client` with Maven you can specify the following dependency:

```xml
<dependency>
  <groupId>org.hisp</groupId>
  <artifactId>dhis2-java-client</artifactId>
  <version>1.0.0</version>
</dependency>
```

### Configuration

A minimal configuration of `dhis2-java-client` looks like this:

```java
Dhis2Config dhis2Config = new Dhis2Config( 
  "https://play.dhis2.org/2.29/api", 
  "admin", 
  "district" );

Dhis2 dhis2 = new Dhis2( dhis2Config );
```

