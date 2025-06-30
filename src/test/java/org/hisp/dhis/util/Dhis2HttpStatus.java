package org.hisp.dhis.util;

/** HttpStatus enumeration. */
public enum Dhis2HttpStatus {
  OK("OK","200"),
  CREATED("CREATED","201"),
  MOVED_PERMANENTLY("MOVED-PERMANENTLY","301"),
  FOUND("FOUND","302"),
  TEMPORARY_REDIRECT("TEMPORARY-REDIRECT","307"),
  CONFLICT("CONFLICT","409");

  private final String name;

  private final String value;

  Dhis2HttpStatus(String name, String value) {
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public String getValue() {
    return value;
  }
}