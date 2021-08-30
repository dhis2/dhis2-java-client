package org.hisp.dhis.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeUtils
{
    private static final DateTimeFormatter DATE_TIME_S_FORMAT = DateTimeFormatter
        .ofPattern( "yyyy-MM-dd'T'HH:mm:ss" );

    private static final DateTimeFormatter DATE_TIME_MS_FORMAT = DateTimeFormatter
        .ofPattern( "yyyy-MM-dd'T'HH:mm:ss.SSS" );

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
     * Returns a date-time string with second precision on the format:
     * <code>yyyy-MM-dd'T'HH:mm:ss</code>.
     *
     * @param dateTime the {@link LocalDateTime}.
     * @return a date-time string.
     */
    public static String getTimestampSecondsString( LocalDateTime dateTime )
    {
        return DATE_TIME_S_FORMAT.format( dateTime );
    }

    /**
     * Returns a date-time string with millisecond precision on the format:
     * <code>yyyy-MM-dd'T'HH:mm:ss.SSS</code>.
     *
     * @param dateTime the {@link LocalDateTime}.
     * @return a date-time string.
     */
    public static String getTimestampMillisString( LocalDateTime dateTime )
    {
        return DATE_TIME_MS_FORMAT.format( dateTime );
    }
}
