package org.hisp.dhis.util;

import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class DateTimeUtils
{
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

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
     * <code>2007-12-03T10:15:30.00</code> or
     * <code>2007-12-03T10:15:30.00Z</code>. A trailing 'Z' indicating Zulu/UTC
     * time will be ignored.
     *
     * @param string the instant string.
     * @return a {@link LocalDateTime}.
     */
    public static LocalDateTime getLocalDateTime( String string )
    {
        string = StringUtils.removeEndIgnoreCase( string, "z" );

        return LocalDateTime.parse( string );
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
     * @return a date time string.
     */
    public static String getLocalDateTimeString( LocalDateTime dateTime )
    {
        return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( dateTime );
    }

    /**
     * Returns a date time string on the format:
     * <code>yyyy-MM-dd'T'HH:mm:ss.SSS</code>.
     *
     * @param dateTime the {@link Date}.
     * @return a date time string.
     */
    public static String getDateTimeString( Date dateTime )
    {
        return new SimpleDateFormat( DATE_TIME_FORMAT ).format( dateTime );
    }

    /**
     * Returns a date time string on the format:
     * <code>yyyy-MM-dd'T'HH:mm:ss.SSSZ</code>.
     *
     * @param dateTime the {@link LocalDateTime}.
     * @return a date time string.
     */
    public static String getUtcDateTimeString( LocalDateTime dateTime )
    {
        return String.format( "%sZ", DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( dateTime ) );
    }

    /**
     * Returns a date time string on the format:
     * <code>yyyy-MM-dd'T'HH:mm:ss.SSSZ</code>.
     *
     * @param dateTime the {@link Date}.
     * @return a date time string.
     */
    public static String getUtcDateTimeString( Date dateTime )
    {
        return String.format( "%sZ", new SimpleDateFormat( DATE_TIME_FORMAT ).format( dateTime ) );
    }
}
