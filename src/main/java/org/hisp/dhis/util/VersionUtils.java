package org.hisp.dhis.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor( access = AccessLevel.PRIVATE )
public class VersionUtils
{
    private static final Pattern PATTERN_MAJOR_VERSION = Pattern.compile( "\\d\\.(\\d{2}).*$" );

    private static final Pattern PATTERN_PATCH_VERSION = Pattern.compile( "\\d\\.\\d{2}\\.(\\d+).*$" );

    /**
     * Returns the major version as an integer based on the given version
     * string.
     *
     * @param version the version string.
     * @return the major version.
     */
    public static Integer getMajorVersion( String version )
    {
        return asInt( getFirstGroup( PATTERN_MAJOR_VERSION, orEmpty( version ) ) );
    }

    /**
     * Returns the patch version as an integer based on the given version
     * string.
     *
     * @param version the version string.
     * @return the patch version.
     */
    public static Integer getPatchVersion( String version )
    {
        return asInt( getFirstGroup( PATTERN_PATCH_VERSION, orEmpty( version ) ) );
    }

    private static Integer asInt( String string )
    {
        return string != null ? Integer.valueOf( string ) : null;
    }

    private static String getFirstGroup( Pattern pattern, String string )
    {
        Matcher matcher = pattern.matcher( string );
        return matcher.matches() ? matcher.group( 1 ) : null;
    }

    private static String orEmpty( String string )
    {
        return ObjectUtils.firstNonNull( string, StringUtils.EMPTY );
    }
}
