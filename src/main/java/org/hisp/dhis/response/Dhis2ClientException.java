/*
 * Copyright (c) 2004-2024, University of Oslo
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * Neither the name of the HISP project nor the names of its contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.hisp.dhis.response;

import lombok.Getter;

/**
 * Exception caused by client side errors.
 *
 * @author Lars Helge Overland
 */
@Getter
public class Dhis2ClientException extends RuntimeException {
  private final int statusCode;

  private final HttpStatus httpStatus;

  private final String errorCode;

  /**
   * Constructor.
   *
   * @param message the message.
   * @param statusCode the stsatus code.
   */
  public Dhis2ClientException(String message, int statusCode) {
    super(message);
    this.statusCode = statusCode;
    this.httpStatus = HttpStatus.valueOf(statusCode);
    this.errorCode = null;
  }

  /**
   * Constructor.
   *
   * @param message the message.
   * @param httpStatus the HTTP status.
   */
  public Dhis2ClientException(String message, HttpStatus httpStatus) {
    super(message);
    this.statusCode = httpStatus.value();
    this.httpStatus = httpStatus;
    this.errorCode = null;
  }

  /**
   * Constructor.
   *
   * @param message the message.
   * @param statusCode the status code.
   * @param errorCode the error code.
   */
  public Dhis2ClientException(String message, int statusCode, String errorCode) {
    super(getMessage(message, statusCode, errorCode));
    this.statusCode = statusCode;
    this.httpStatus = HttpStatus.valueOf(statusCode);
    this.errorCode = errorCode;
  }

  /**
   * Constructor.
   *
   * @param message the message.
   * @param cause the cause of the exception.
   */
  public Dhis2ClientException(String message, Throwable cause) {
    super(message, cause);
    this.statusCode = -1;
    this.httpStatus = HttpStatus.NO_STATUS;
    this.errorCode = null;
  }

  /**
   * Constructor.
   *
   * @param message the message.
   * @param cause the cause of the exception.
   * @param statusCode the status code.
   */
  public Dhis2ClientException(String message, Throwable cause, int statusCode) {
    super(message, cause);
    this.statusCode = statusCode;
    this.httpStatus = HttpStatus.valueOf(statusCode);
    this.errorCode = null;
  }

  /**
   * Indicates whether the exception is caused by invalid authentication credentials.
   *
   * @return true if the exception is caused by invalid authentication credentials.
   */
  public boolean isUnauthorized() {
    return HttpStatus.UNAUTHORIZED == httpStatus;
  }

  /**
   * Indicates whether the exception is caused by insufficient permissions.
   *
   * @return true if the exception is caused by insufficient permissions.
   */
  public boolean isForbidden() {
    return HttpStatus.FORBIDDEN == httpStatus;
  }

  /**
   * Indicates whether the exception is caused by the requested object not existing.
   *
   * @return true if the exception is caused by the requested object not existing.
   */
  public boolean isNotFound() {
    return HttpStatus.NOT_FOUND == httpStatus;
  }

  /**
   * Returns a formatted message with the status code and error code.
   *
   * @param message the original message.
   * @return a formatted message.
   */
  private static String getMessage(String message, int statusCode, String errorCode) {
    return String.format("%s. Status code: %d. Error code: '%s'.", message, statusCode, errorCode);
  }
}
