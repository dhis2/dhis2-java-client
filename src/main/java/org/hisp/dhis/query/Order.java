package org.hisp.dhis.query;

/**
 * Query response ordering.
 *
 * @author Lars Helge Overland
 */
public class Order
{
    private final String property;

    private final Direction direction;

    protected Order( String property, Direction direction )
    {
        this.property = property;
        this.direction = direction;
    }

    public static Order asc( String property )
    {
        return new Order( property, Direction.ASC );
    }

    public static Order desc( String property )
    {
        return new Order( property, Direction.DESC );
    }

    public String getProperty()
    {
        return property;
    }

    public Direction getDirection()
    {
        return direction;
    }

    public boolean hasOrder()
    {
        return property != null && direction != null;
    }
}
