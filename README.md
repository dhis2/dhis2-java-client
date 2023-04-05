# DHIS 2 Java Client

DHIS 2 API client for Java. The client allows you to create, update and retrieve information from DHIS 2. The client is compatible with Java 8 and later JDK versions.

## Getting started

To use `dhis2-java-client` with Maven you can specify the following dependency:

```xml
<dependency>
  <groupId>org.hisp</groupId>
  <artifactId>dhis2-java-client</artifactId>
  <version>LATEST</version>
</dependency>
```

You can find `dhis2-java-client` and the available versions in [Maven central repository](https://mvnrepository.com/artifact/org.hisp/dhis2-java-client).

## Configuration and Authentication

This section describes configuration and authentication of the client.

#### Basic authentication

A minimal configuration of `dhis2-java-client` where the configuration parameters refer to the base URL, username and password for the DHIS 2 instance to connect to can be specified like this. The default authentication mechanism is *Basic authentication*. Note that you should *not* include the `api` part nor a trailing `/` in the base URL:

```java
Dhis2Config config = new Dhis2Config( 
    "https://play.dhis2.org/2.39.0", 
    "admin", "district" );

Dhis2 dhis2 = new Dhis2( config );
```

Alternatively, to use Basic authentication you can specify the username and password of the DHIS 2 account together with the base URL of the DHIS 2 instance:

```java
Dhis2 dhis2 = Dhis2.withBasicAuth( 
    "https://play.dhis2.org/2.39.0", 
    "admin", "district" );
```

You can use the username and password of a regular DHIS 2 user account.

### Personal access token authentication

To use personal access token (PAT)-based authentication you can specify the access token:

```java
Dhis2 dhis2 = Dhis2.withAccessTokenAuth( 
    "https://play.dhis2.org/2.39.0", 
    "d2pat_2bBQecgNcxrS4EPhBJuRlQkwiLr2ATnC2557514242" );
```

PATs can be created through the API or the user interface by going to Profile > Settings > Personal access tokens.

### Cookie authentication

To use cookie-based authentication you can specify the session identifier:

```java
Dhis2 dhis2 = Dhis2.withCookieAuth( 
    "https://play.dhis2.org/2.39.0", 
    "5EC557E60D7E5CE8D78EEC1389592D3E" );
```

The name of the session cookie used by the DHIS 2 API is `JSESSIONID`. The value can typically be retrieved from the `Cookie` HTTP request header sent with DHIS 2 API requests.

## Usage

This section explains the basic usage of the client.

### Query objects

To retrieve all org unit groups:

```java
List<OrgUnitGroup> orgUnitGroups = dhis2.getOrgUnitGroups();
```

To retrieve org units with a filter on the level in a paged way:

```java
List<OrgUnit> orgUnits = dhis2.getOrgUnits( Query.instance()
    .addFilter( Filter.eq( "level", 4 ) )
    .addFilter( Filter.like( "code", "fac" ) )
    .setPaging( 1, 200 ) );
```

To retrieve all org units ordered descending on the name property:

```java
List<OrgUnit> orgUnits = dhis2.getOrgUnits( Query.instance()
    .setOrder( Order.desc( "name" ) ) );
```

When retrieving lists of objects, associations to other objects will not be populated in the response by default. You can expand associations in object lists through the query object like this, e.g. for programs:

```java
List<Program> programs = dhis2.getPrograms( Query.instance()
    .withExpandAssociations() );
```

### Get object

To retrieve a single org unit by identifier:

```java
OrgUnit orgUnit = dhis2.getOrgUnit( "j7gkH3hf83k" );
```

### Create object

To create an org unit:

```java
OrgUnit orgUnit = new OrgUnit();
orgUnit.setName( "Ngelehun" );
orgUnit.setCode( "NGLH" );

ObjectResponse response = dhis2.saveOrgUnit( orgUnit );
```

### Create multiple objects

To create or update multiple objects:

```java
List<OrgUnit> orgUnits = CollectionUtils.list( 
    new OrgUnit( "nEt3lFHOqYP", "Ngelehun"),
    new OrgUnit( "gnAOCDoZUVO", "Kailahun" ) );

ObjectsResponse response = dhis2.saveOrgUnits( orgUnits );
```

### Update object

To update an org unit (note that the ID property must be set):

```java
OrgUnit orgUnit = new OrgUnit();
orgUnit.setId( "cDw53Ej8rjT" );
orgUnit.setName( "Ngelehun" );
orgUnit.setCode( "NGLH" );

ObjectResponse response = dhis2.updateOrgUnit( orgUnit );
```

### Check if object exists

To check if an object exists:

```java
boolean exists = dhis2.isOrgUnit( "O6uvpzGd5pu" );
```

### Remove object

To remove an org unit:

```java
ObjectResponse response = dhis2.removeOrgUnit( "j7gkH3hf83k" );
```

### Get response message

The various metadata object save and update methods return an instance of `ObjectResponse` which holds information about the operation, such as status, HTTP status, HTTP status code and a message describing the outcome.

```java
ObjectResponse response = dhis2.saveDataElement( dataElement );

Status status = response.getStatus();
boolean success = response.getHttpStatus().is2xxSuccessful();
```

### Get system settings

To get system settings:

```java
SystemSettings settings = dhis2.getSystemSettings();
```

### Save events

To save a list of events:

```java
List<EventDataValue> dataValues = CollectionUtils.list(
    new EventDataValue( "oZg33kd9taw", "Male" ),
    new EventDataValue( "qrur9Dvnyt5", "45" ),
    new EventDataValue( "GieVkTxp4HH", "143" ),
    new EventDataValue( "eMyVanycQSC", "2021-07-02" ),
    new EventDataValue( "msodh3rEMJa", "2021-08-05" ),
    new EventDataValue( "K6uUAvq500H", "A010" ),
    new EventDataValue( "fWIAEtYVEGk", "MODDISCH" ) );

Event event = new Event( "EHlOLNtR4J0" );
event.setProgram( "eBAyeGv0exc" );
event.setProgramStage( "Zj7UnCAulEk" );
event.setOrgUnit( "DiszpKrYNg8" );
event.setOccurredAt( DateTimeUtils.getDate( 2021, 7, 12 ) );
event.setDataValues( dataValues );

Events events = new Events( CollectionUtils.list( event ) );

EventResponse response = dhis2.saveEvents( events );
```

### Get event

To retrieve an event:

```java
Event event = dhis2.getEvent( "EHlOLNtR4J0" );
```

### Remove event

To remove an event:

```java
EventResponse response = dhis2.removeEvent( event );
```

### Save data value set

To save a data value set:

```java
DataValue dv1 = new DataValue();
dv1.setDataElement( "f7n9E0hX8qk" );
dv1.setValue( "12" );

DataValue dv2 = new DataValue();
dv2.setDataElement( "Ix2HsbDMLea" );
dv2.setValue( "13" );

DataValueSet dvs = new DataValueSet();
dvs.setDataSet( "pBOMPrpg1QX" );
dvs.setCompleteDate( "2014-02-03" );
dvs.setPeriod( "201910" );
dvs.setOrgUnit( "DiszpKrYNg8" );

dvs.addDataValue( dv1 );
dvs.addDataValue( dv2 );

DataValueSetImportOptions options = DataValueSetImportOptions.instance();

DataValueSetResponse response = dhis2.saveDataValueSet( dvs, options );
```

### Save data value set from file

To read a data value set from a file and save it:

```java
File file = new File( "/tmp/datavalueset.json" );

DataValueSetImportOptions options = DataValueSetImportOptions.instance();

DataValueSetResponse response = dhis2.saveDataValueSet( file, options );
```

### Get analytics data value set

To retrieve analytics data in the data value set format:

```java
DataValueSet dvs = dhis2.getAnalyticsDataValueSet( AnalyticsQuery.instance()
    .addDimension( Dimension.DIMENSION_DX, "cYeuwXTCPkU", "Jtf34kNZhzP" )
    .addDimension( Dimension.DIMENSION_OU, "O6uvpzGd5pu", "fdc6uOvgoji" )
    .addDimension( Dimension.DIMENSION_PE, "202007", "202008" ) );
```

### Write analytics data value set to file

To retrieve analytics data and write the content to the file:

```java
AnalyticsQuery query = AnalyticsQuery.instance()
    .addDimension( Dimension.DIMENSION_DX, "cYeuwXTCPkU", "Jtf34kNZhzP" )
    .addDimension( Dimension.DIMENSION_OU, "O6uvpzGd5pu", "fdc6uOvgoji" )
    .addDimension( Dimension.DIMENSION_PE, "202007", "202008" );

File file = new File( "/tmp/data-value-set.json" );
    
dhis2.writeAnalyticsDataValueSet( query, file );
```

### Get user authentication

To retrieve the list of authorities granted to the authenticated user:

```java
List<String> authorities = dhis2.getUserAuthorization();
```

### Get system info

To get system info:

```java
SystemInfo info = dhis2.getSystemInfo();

String version = info.getVersion();
```

To compare the system version:

```java
SystemInfo info = dhis2.getSystemInfo();

SystemVersion version = info.getSystemVersion();

boolean isHigher = version.isHigher( "2.37.0" );
```

## Development

Package:

```
mvn clean package
```

Run unit tests:

```
mvn test
```

Run integration tests:

```
mvn test -P integration
```

## Deployment

The artifact will be deployed through a GitHub action to the OSSRH Maven repository when detecting a commit to master 
where the main version is  not a SNAPSHOT version. To trigger a deploy, make a commit to master where the main version 
is bumped to a non-SNAPSHOT version.

