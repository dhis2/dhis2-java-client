# DHIS 2 Java Client

DHIS 2 API client for Java. The client allows you to create, update and retrieve information from DHIS 2. Java 8 or later is required.

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

## Configuration

A minimal configuration of `dhis2-java-client` where the configuration parameters refer to the base URL, username and password for the DHIS 2 instance to connect to can be specified like this. The default authentication mechanism is *Basic authentication*. Note that you should *not* include the `api` part or a trailing `/` in the base URL:

```java
Dhis2Config config = new Dhis2Config( 
    "https://play.dhis2.org/2.36.3", 
    "admin", 
    "district" );

Dhis2 dhis2 = new Dhis2( config );
```

## Authentication

The default configuration as specified above uses Basic authentication. 

### Basic authentication

To explicitly use Basic authentication (equivalent to the configuration above), you can specify the username and password of the DHIS 2 account with the `BasicAuthentication` class:

```java
Authentication authentication = new BasicAuthentication( "admin", "district" );

Dhis2Config config = new Dhis2Config( authentication );

Dhis2 dhis2 = new Dhis2( config );
```

### Cookie authentication

To use Cookie-based authentication you can specify the session ID with the `CookieAuthentication` class:

```java
Authentication authentication = new CookieAuthentication( "754E5D586868DB9A8665249A97DC91D3" );

Dhis2Config config = new Dhis2Config( authentication );

Dhis2 dhis2 = new Dhis2( config );
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
    .withPaging( 1, 200 ) );
```

To retrieve all org units ordered descending on the name property:

```java
List<OrgUnit> orgUnits = dhis2.getOrgUnits( Query.instance()
    .withOrder( Order.desc( "name" ) ) );
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
List<OrgUnit> orgUnits = List.of( 
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
List<EventDataValue> dataValues = CollectionUtils.newImmutableList(
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

Events events = new Events( CollectionUtils.newImmutableList( event ) );

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
    .addDimension( Dimension.DIMENSION_PE, "202007", "202008" ) );

File file = new File( "/tmp/data-value-set.json" );
    
dhis2.writeAnalyticsDataValueSet( query, file );
```

