package org.hisp.dhis.model;

import org.apache.maven.artifact.versioning.ComparableVersion;

public class SystemVersion
{
    private final String version;

    public SystemVersion( String version )
    {
        this.version = version;
    }

    public String getVersion()
    {
        return version;
    }

    /**
     * Indicates whether this system version is higher than the given version.
     *
     * @param version the version to compare with.
     * @return true if higher, false if not.
     */
    public boolean isHigher( String version )
    {
        return compareThisTo( version ) > 0;
    }

    /**
     * Indicates whether this system version is higher than or equal to the
     * given version.
     *
     * @param version the version to compare with.
     * @return true if higher or equal, false if not.
     */
    public boolean isHigherOrEqual( String version )
    {
        return compareThisTo( version ) >= 0;
    }

    /**
     * Indicates whether this system version is equal to the given version.
     *
     * @param version the version to compare with.
     * @return true if equal, false if not.
     */
    public boolean isEqual( String version )
    {
        return compareThisTo( version ) == 0;
    }

    /**
     * Indicates whether this system version is lower than or equal to the given
     * version.
     *
     * @param version the version to compare with.
     * @return true if lower or equal, false if not.
     */
    public boolean isLowerOrEqual( String version )
    {
        return compareThisTo( version ) <= 0;
    }

    /**
     * Indicates whether this system version is lower than the given version.
     *
     * @param version the version to compare with.
     * @return true if lower, false if not.
     */
    public boolean isLower( String version )
    {
        return compareThisTo( version ) < 0;
    }

    private int compareThisTo( String version )
    {
        return new ComparableVersion( this.version )
            .compareTo( new ComparableVersion( version ) );
    }
}
