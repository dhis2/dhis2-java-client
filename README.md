# DHIS 2 Java Client

DHIS 2 API client for Java. The client allows you to create, update and retrieve information from DHIS 2.

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

A minimal, local configuration of `dhis2-java-client` looks like this, where the configuration parameters refer to the base URL, username and password for the DHIS 2 instance to connect to. Note that you should not include the `api` part of the base URL:

```java
Dhis2Config config = new Dhis2Config( 
    "https://play.dhis2.org/2.32.2", 
    "admin", 
    "district" );

Dhis2 dhis2 = new Dhis2( config );
```

## Usage

This section explains the basic usage of the client.

### Retrieve objects

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

When retrieving lists of objects, associations to other objects will not 
be populated in the response by default. You can expand associations in 
object lists through the query object like this, e.g. for programs:

```java
List<Program> programs = dhis2.getPrograms( Query.instance()
    .withExpandAssociations() );
```

To retrive a single org unit by identifier:

```java
OrgUnit orgUnit = dhis2.getOrgUnit( "j7gkH3hf83k" );
```

To retrieve your own, arbitrary domain object:

```java
DataElement dataElement = dhis2.getObject( "dataElements/khG6T32uJ71", DataElement.class );
```

### Create objects

To create an org unit:

```java
OrgUnit orgUnit = new OrgUnit();
orgUnit.setName( "Ngelehun" );
orgUnit.setCode( "NGLH" );

ResponseMessage msg = dhis2.saveOrgUnit( orgUnit );
```

To save your own, arbitrary metadata object:

```java
DataElement dataElement = new DataElement( "Staff ");

ResponseMessage msg = dhis2.saveMetadataObject( "dataElements", dataElement );
```

### Update objects

To update an org unit (note that the ID property must be set):

```java
OrgUnit orgUnit = new OrgUnit();
orgUnit.setId( "cDw53Ej8rjT" );
orgUnit.setName( "Ngelehun" );
orgUnit.setCode( "NGLH" );

ResponseMessage msg = dhis2.updateOrgUnit( orgUnit );
```

To update your own, arbitrary metadata object:

```java
ResponseMessage msg = dhis2.updateMetadataObject( 
    "dataElements/" + dataElement.getId(), dataElement );
```

### Get response message

The various save and update methods returns an instance of `ResponseMessage`, which holds information about the operation, such as status, HTTP status, HTTP status code and a message descibing the outcome.

```java
ResponseMessage msg = dhis2.saveMetadataObject( "dataElements", dataElement );

boolean success = msg.getHttpStatus().is2xxSuccessful();
```

### Get system settings

To get system settings:

```java
SystemSettings settings = dhis2.getSystemSettings();
```

### Save data value set

To save a data value set:

```java
DataValue dataValue1 = new DataValue();
dataValue1.setDataElement( "f7n9E0hX8qk" );
dataValue1.setValue( "12" );

DataValue dataValue2 = new DataValue();
dataValue2.setDataElement( "Ix2HsbDMLea" );
dataValue2.setValue( "13" );

DataValueSet dataValueSet = new DataValueSet();
dataValueSet.setDataSet( "pBOMPrpg1QX" );
dataValueSet.setCompleteDate( "2014-02-03" );
dataValueSet.setPeriod( "201910" );
dataValueSet.setOrgUnit( "DiszpKrYNg8" );

dataValueSet.addDataValue( dataValue1 );
dataValueSet.addDataValue( dataValue2 );

DataValueSetImportOptions options = DataValueSetImportOptions.instance();

DataValueSetResponseMessage response = dhis2.saveDataValueSet( dataValueSet, options );
```

### Save data value set from file

To read a data value set from a file and save it:

```java
File file = new File( "/tmp/datavalueset.json" );

DataValueSetImportOptions options = DataValueSetImportOptions.instance();

DataValueSetResponseMessage response = dhis2.saveDataValueSet( file, options );
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

