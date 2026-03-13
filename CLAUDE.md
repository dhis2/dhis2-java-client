# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

A Java 17 client library for the DHIS2 health information system API. Published to Maven Central. The library lets Java applications create, read, update, and delete DHIS2 metadata, tracker data, analytics, and more.

## Build & Test Commands

```bash
# Build
mvn clean package

# Run unit tests only (default)
mvn test

# Run a single unit test class
mvn test -Dtest=ClassName

# Run integration tests (requires a live DHIS2 instance)
mvn test -Pintegration

# Run a single integration test
mvn test -Pintegration -Dtest=Dhis2ApiTest

# Set log level for tests
mvn test -Dlog.level.dhis2=info

# Apply code formatting (Google Java Format via Spotless)
mvn spotless:apply
```

Integration tests use `@Tag(TestTags.INTEGRATION)` and connect to a DHIS2 play instance configured in `TestFixture.java`. Unit tests use `@Tag(TestTags.UNIT)`.

## Architecture

### Core Classes

- **`Dhis2.java`** — The main public facade (~4500 lines). All API operations (metadata CRUD, tracker, analytics, data values, data store, system info, etc.) are methods on this class.
- **`BaseDhis2.java`** — Base implementation with the Apache HttpClient 5 HTTP layer, Jackson deserialization, and shared request logic.
- **`Dhis2Config.java`** — Immutable config: base URL + authentication strategy.
- **`Dhis2AsyncRequest.java`** — Handles long-running async jobs via DHIS2's job status polling.

### Authentication

`auth/` implements a strategy pattern. The four implementations are `BasicAuthentication`, `AccessTokenAuthentication`, `CookieAuthentication`, and `NoAuthentication`. Pass one to `Dhis2Config`.

### Domain Models (`model/`)

All DHIS2 entities extend `IdentifiableObject` (has `id`, `code`, `created`, `lastUpdated`, etc.). Nameable entities additionally extend `NameableObject` (adds `name`, `shortName`, `description`). Subdirectories group by domain: `trackedentity/`, `event/`, `analytics/`, `datavalueset/`, `metadata/`, `sharing/`, `dimension/`, etc.

### Query DSL (`query/`)

Fluent builder for DHIS2 API filter parameters:
- `Query.instance()` — entry point
- `Filter` — field predicates (`eq`, `like`, `gt`, `in`, etc.)
- `Order` — ascending/descending by field
- `Paging` — page number and size

### Responses (`response/`)

- `Response` — base class with `Status` (OK, WARNING, ERROR) and `HttpStatus`
- Typed subclasses: `ObjectResponse`, `ObjectsResponse`, `EventResponse`, `TrackedEntityResponse`, etc.

### API Field Definitions (`api/`)

Contains constants for DHIS2 API paths and field sets used when constructing requests.

## Key Dependencies

- **Apache HttpComponents Client 5** — HTTP transport
- **Jackson 2** — JSON serialization/deserialization (`JacksonUtils` in `util/`)
- **Lombok** — reduces boilerplate on model classes
- **JTS** — geometry/spatial types for GeoJSON
- **SLF4J** — logging facade

## Code Style

Google Java Format is enforced via Spotless. Run `mvn spotless:apply` before committing if you edit Java files.
