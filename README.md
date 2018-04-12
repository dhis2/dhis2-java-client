# DHIS 2 Java Client

DHIS 2 API client for Java and Spring. The client allows you to create, update and
retrieve information from DHIS 2.

The client depends on `spring-webmvc` and `jackson-annotations` and is suited for use with Spring Boot 2.

## Getting started

To use `dhis2-java-client` with Maven you can specify the following dependency:

```xml
<dependency>
  <groupId>org.hisp</groupId>
  <artifactId>dhis2-java-client</artifactId>
  <version>1.0.0</version>
</dependency>
```

## Configuration

A minimal configuration of `dhis2-java-client` looks like this:

```java
Dhis2Config config = new Dhis2Config( 
    "https://play.dhis2.org/2.29/api", 
    "admin", 
    "district" );

Dhis2 dhis2 = new Dhis2( config );
```
An externalized configuration in a Spring Boot 2 setting could look like this:

```java
@SpringBootApplication
@PropertySource("file:/opt/conf.properties")
public class MyApp
{  
    @Value("${dhis.instance.url}")
    private String dhisInstanceUrl;

    @Value("${dhis.instance.username}")
    private String dhisInstanceUsername;
    
    @Value("${dhis.instance.password}")
    private String dhisInstancePassword;

    @Bean
    public Dhis2 dhis2()
    {
        Dhis2Config config = new Dhis2Config( 
            dhisInstanceUrl, 
            dhisInstanceUsername, 
            dhisInstancePassword );
        
        return new Dhis2( config );
    }
}
```

## Usage

This section explains the basic usage of the client.

#### Retrieve objects

To retrieve all org unit groups:

```java
List<OrgUnitGroup> orgUnitGroups = dhis2.getOrgUnitGroups();
```

To retrieve org units with a filter on the level in a paged way:

```java
List<OrgUnit> orgUnits = dhis2.getOrgUnits( Query.instance()
    .addFilter( Filter.eq( "level", 4 ) )
    .withPaging( 1, 200 ) );
```

To retrieve all org units ordered descending on the name property:

```java
List<OrgUnit> orgUnits = dhis2.getOrgUnits( Query.instance()
    withOrder( Order.desc( "name" ) );
```

To retrive a single org unit by identifier:

```java
OrgUnit orgUnit = dhis2.getOrgUnit( "j7gkH3hf83k" );
```

To retrieve your own, arbitrary domain objects:

```java
DataElement dataElement = dhis2.getObject( "dataElements/khG6T32uJ71", DataElement.class );
```

#### Create objects

To create an org unit:

```java
OrgUnit orgUnit = new OrgUnit();
orgUnit.setName( "Ngelehun" );
orgUnit.setCode( "NGLH" );

dhis2.saveOrgUnit( orgUnit );
```

To update an org unit:

```java
OrgUnit orgUnit = new OrgUnit();
orgUnit.setName( "Ngelehun" );
orgUnit.setCode( "NGLH" );

dhis2.updateOrgUnit( orgUnit );
```

To save your own, arbitrary domain objects:

```java
DataElement dataElement = new DataElement( "Staff ");

dhis2.saveObject( "dataElements", dataElement );
```

To update your own, arbitrary domain objects:

```java
DataElement dataElement = new DataElement( "k7jF98KfJ2k", "Staff ");

dhis2.updateObject( "dataElements/" + dataElement.getId(), dataElement );
```
