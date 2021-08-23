package org.hisp.dhis.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateTimeUtils
{
    public static Date getDate( int year, int month, int dayOfMonth )
    {
        LocalDate localDate = LocalDate.of( year, month, dayOfMonth );

        return Date.from( localDate.atStartOfDay( ZoneId.systemDefault() ).toInstant() );
    }
}
