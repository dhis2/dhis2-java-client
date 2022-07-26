package org.hisp.dhis.util;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeUtils
{
    /**
     * Format: <code>yyyy-MM-dd'T'HH:mm:ss</code>
     */
    private static final DateTimeFormatter DATE_TIME_S_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    /**
     * Returns a date.
     *
     * @param year the year.
     * @param month the month, from 1.
     * @param dayOfMonth, from 1 to 31.
     * @return a {@link Date}.
     */
    public static Date getDate( int year, int month, int dayOfMonth )
    {
        LocalDate localDate = LocalDate.of( year, month, dayOfMonth );

        return Date.from( localDate.atStartOfDay( ZoneId.systemDefault() ).toInstant() );
    }

    /**
     * Returns a {@link LocalDateTime} based on the given string using the UTC
     * time zone. The string must be on the ISO local date time format, e.g.
     * <code>2007-12-03T10:15:30.00Z</code>.
     *
     * @param string the instant string.
     * @return a {@link LocalDateTime}.
     */
    public static LocalDateTime getLocalDateTime( String string )
    {
        return LocalDateTime.ofInstant( Instant.parse( string ), ZoneId.of( ZoneOffset.UTC.getId() ) );
    }

    /**
     * Indicates whether the given string can be parsed to a local date time,
     * i.e. is in a valid ISO date time format, e.g.
     * <code>2007-12-03T10:15:30.00Z</code>.
     *
     * @param instant the instant string.
     * @return true if the given string can be parsed to a local date time,
     *         false otherwise.
     */
    public static boolean isValidLocalDateTime( String instant )
    {
        try
        {
            return instant != null && getLocalDateTime( instant ) != null;
        }
        catch ( DateTimeException ex )
        {
            return false;
        }
    }

    /**
     * Returns a date time string on the format:
     * <code>yyyy-MM-dd'T'HH:mm:ss.SSS</code>.
     *
     * @param dateTime the {@link LocalDateTime}.
     * @return a date-time string.
     */
    public static String getLocalDateTimeString( LocalDateTime dateTime )
    {
        return DATE_TIME_S_FORMAT.format( dateTime );
    }
}
