package org.hisp.dhis.response;

/**
 * Enumeration of HTTP status codes.
 */
public enum HttpStatus
{
    CONTINUE( 100, "Continue" ),
    SWITCHING_PROTOCOLS( 101, "Switching Protocols" ),
    PROCESSING( 102, "Processing" ),
    CHECKPOINT( 103, "Checkpoint" ),
    OK( 200, "OK" ),
    CREATED( 201, "Created" ),
    ACCEPTED( 202, "Accepted" ),
    NON_AUTHORITATIVE_INFORMATION( 203, "Non-Authoritative Information" ),
    NO_CONTENT( 204, "No Content" ),
    RESET_CONTENT( 205, "Reset Content" ),
    PARTIAL_CONTENT( 206, "Partial Content" ),
    MULTI_STATUS( 207, "Multi-Status" ),
    ALREADY_REPORTED( 208, "Already Reported" ),
    IM_USED( 226, "IM Used" ),
    MULTIPLE_CHOICES( 300, "Multiple Choices" ),
    MOVED_PERMANENTLY( 301, "Moved Permanently" ),
    FOUND( 302, "Found" ),
    SEE_OTHER( 303, "See Other" ),
    NOT_MODIFIED( 304, "Not Modified" ),
    TEMPORARY_REDIRECT( 307, "Temporary Redirect" ),
    PERMANENT_REDIRECT( 308, "Permanent Redirect" ),
    BAD_REQUEST( 400, "Bad Request" ),
    UNAUTHORIZED( 401, "Unauthorized" ),
    PAYMENT_REQUIRED( 402, "Payment Required" ),
    FORBIDDEN( 403, "Forbidden" ),
    NOT_FOUND( 404, "Not Found" ),
    METHOD_NOT_ALLOWED( 405, "Method Not Allowed" ),
    NOT_ACCEPTABLE( 406, "Not Acceptable" ),
    PROXY_AUTHENTICATION_REQUIRED( 407, "Proxy Authentication Required" ),
    REQUEST_TIMEOUT( 408, "Request Timeout" ),
    CONFLICT( 409, "Conflict" ),
    GONE( 410, "Gone" ),
    LENGTH_REQUIRED( 411, "Length Required" ),
    PRECONDITION_FAILED( 412, "Precondition Failed" ),
    PAYLOAD_TOO_LARGE( 413, "Payload Too Large" ),
    URI_TOO_LONG( 414, "URI Too Long" ),
    UNSUPPORTED_MEDIA_TYPE( 415, "Unsupported Media Type" ),
    REQUESTED_RANGE_NOT_SATISFIABLE( 416, "Requested range not satisfiable" ),
    EXPECTATION_FAILED( 417, "Expectation Failed" ),
    UNPROCESSABLE_ENTITY( 422, "Unprocessable Entity" ),
    LOCKED( 423, "Locked" ),
    FAILED_DEPENDENCY( 424, "Failed Dependency" ),
    UPGRADE_REQUIRED( 426, "Upgrade Required" ),
    TOO_MANY_REQUESTS( 429, "Too Many Requests" ),
    REQUEST_HEADER_FIELDS_TOO_LARGE( 431, "Request Header Fields Too Large" ),
    UNAVAILABLE_FOR_LEGAL_REASONS( 451, "Unavailable For Legal Reasons" ),
    INTERNAL_SERVER_ERROR( 500, "Internal Server Error" ),
    NOT_IMPLEMENTED( 501, "Not Implemented" ),
    BAD_GATEWAY( 502, "Bad Gateway" ),
    SERVICE_UNAVAILABLE( 503, "Service Unavailable" ),
    GATEWAY_TIMEOUT( 504, "Gateway Timeout" ),
    HTTP_VERSION_NOT_SUPPORTED( 505, "HTTP Version not supported" ),
    VARIANT_ALSO_NEGOTIATES( 506, "Variant Also Negotiates" ),
    INSUFFICIENT_STORAGE( 507, "Insufficient Storage" ),
    LOOP_DETECTED( 508, "Loop Detected" ),
    BANDWIDTH_LIMIT_EXCEEDED( 509, "Bandwidth Limit Exceeded" ),
    NOT_EXTENDED( 510, "Not Extended" ),
    NETWORK_AUTHENTICATION_REQUIRED( 511, "Network Authentication Required" );

    private final int value;

    private final String reasonPhrase;

    HttpStatus( int value, String reasonPhrase )
    {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    /**
     * Returns the integer value of this status code.
     *
     * @return the integer value of this status code.
     */
    public int value()
    {
        return this.value;
    }

    /**
     * Returns the reason phrase of this status code.
     *
     * @return the reason phrase of this status code.
     */
    public String getReasonPhrase()
    {
        return this.reasonPhrase;
    }

    /**
     * Returns the HTTP status series of this status code.
     *
     * @return the HTTP status series of this status code.
     */
    public Series series()
    {
        return Series.valueOf( this );
    }

    /**
     * Whether this status code is in the HTTP series Informational.
     *
     * @return true or false.
     */
    public boolean is1xxInformational()
    {
        return series() == Series.INFORMATIONAL;
    }

    /**
     * Whether this status code is in the HTTP series Successful.
     *
     * @return true or false.
     */
    public boolean is2xxSuccessful()
    {
        return series() == Series.SUCCESSFUL;
    }

    /**
     * Whether this status code is in the HTTP series Redirection.
     *
     * @return true or false.
     */
    public boolean is3xxRedirection()
    {
        return series() == Series.REDIRECTION;
    }

    /**
     * Whether this status code is in the HTTP series Client error.
     *
     * @return true or false.
     */
    public boolean is4xxClientError()
    {
        return series() == Series.CLIENT_ERROR;
    }

    /**
     * Whether this status code is in the HTTP series Server error.
     *
     * @return true or false.
     */
    public boolean is5xxServerError()
    {
        return series() == Series.SERVER_ERROR;
    }

    /**
     * Whether this status code is in the HTTP series Client error.
     *
     * @return true or false.
     */
    public boolean isError()
    {
        return is4xxClientError() || is5xxServerError();
    }

    /**
     * Return a string representation of this status code.
     *
     * @return a string representation of this status code.
     */
    @Override
    public String toString()
    {
        return String.format( "%s %s", value, name() );
    }

    /**
     * Return the enum constant of this type with the specified numeric value.
     *
     * @param statusCode the numeric value of the enum to be returned
     * @return the enum constant with the specified numeric value
     * @throws IllegalArgumentException if this enum has no constant for the
     *         specified numeric value
     */
    public static HttpStatus valueOf( int statusCode )
    {
        HttpStatus status = resolve( statusCode );
        if ( status == null )
        {
            throw new IllegalArgumentException( String.format(
                "No matching constant for status code: %d", statusCode ) );
        }
        return status;
    }

    /**
     * Resolve the given status code to an {@code HttpStatus}, if possible.
     *
     * @param statusCode the HTTP status code (potentially non-standard)
     * @return the corresponding {@code HttpStatus}, or {@code null} if not
     *         found
     */
    public static HttpStatus resolve( int statusCode )
    {
        for ( HttpStatus status : values() )
        {
            if ( status.value == statusCode )
            {
                return status;
            }
        }
        return null;
    }

    /**
     * Enumeration of HTTP status series.
     */
    public enum Series
    {
        INFORMATIONAL( 1 ),
        SUCCESSFUL( 2 ),
        REDIRECTION( 3 ),
        CLIENT_ERROR( 4 ),
        SERVER_ERROR( 5 );

        private final int value;

        Series( int value )
        {
            this.value = value;
        }

        /**
         * Returns the integer value of this status series. Ranges from 1 to 5.
         *
         * @return the integer value of this status series. Ranges from 1 to 5.
         */
        public int value()
        {
            return this.value;
        }

        /**
         * Return the enum constant of this type with the corresponding series.
         *
         * @param status a standard HTTP status enum value
         * @return the enum constant of this type with the corresponding series
         * @throws IllegalArgumentException if this enum has no corresponding
         *         constant
         */
        public static Series valueOf( HttpStatus status )
        {
            return valueOf( status.value );
        }

        /**
         * Return the enum constant of this type with the corresponding series.
         *
         * @param statusCode the HTTP status code (potentially non-standard)
         * @return the enum constant of this type with the corresponding series
         * @throws IllegalArgumentException if this enum has no corresponding
         *         constant
         */
        public static Series valueOf( int statusCode )
        {
            Series series = resolve( statusCode );
            if ( series == null )
            {
                throw new IllegalArgumentException( "No matching constant for [" + statusCode + "]" );
            }
            return series;
        }

        /**
         * Resolve the given status code to an {@code HttpStatus.Series}, if
         * possible.
         *
         * @param statusCode the HTTP status code (potentially non-standard)
         * @return the corresponding {@code Series}, or {@code null} if not
         *         found
         */
        public static Series resolve( int statusCode )
        {
            int seriesCode = statusCode / 100;
            for ( Series series : values() )
            {
                if ( series.value == seriesCode )
                {
                    return series;
                }
            }
            return null;
        }
    }
}
