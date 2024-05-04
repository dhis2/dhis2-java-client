package org.hisp.dhis.support;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import org.hisp.dhis.util.CollectionUtils;

public class Assertions
{
    private static final String NULL_MESSAGE = "Actual collection is null";

    /**
     * Asserts that the actual collection contains exactly the expected items.
     *
     * @param <E>
     * @param actual the collection.
     * @param expected the items.
     */
    @SafeVarargs
    public static <E> void assertContainsExactly( Collection<E> actual, E... expected )
    {
        assertNotNull( actual, NULL_MESSAGE );
        assertContains( actual, expected );

        List<E> expected_ = CollectionUtils.list( expected );

        for ( E item : actual )
        {
            String message = format( "Actual item is not present in expected: '%s' (expected size: %d)",
                item, expected_.size() );

            assertTrue( expected_.contains( item ), message );
        }
    }

    /**
     * Asserts that the actual collection contains the expected items.
     *
     * @param <E>
     * @param actual the collection.
     * @param expected the items.
     */
    @SafeVarargs
    public static <E> void assertContains( Collection<E> actual, E... expected )
    {
        assertNotNull( actual, NULL_MESSAGE );

        for ( E item : expected )
        {
            String message = format( "Expected item is not present in actual: '%s' (actual size: %d)",
                item, actual.size() );

            assertTrue( actual.contains( item ), message );
        }
    }

    /**
     * Asserts that the actual collection does not contain the expected items.
     *
     * @param actual the collection.
     * @param expected the items.
     */
    @SafeVarargs
    public static <E> void assertNotContains( Collection<E> actual, E... expected )
    {
        assertNotNull( actual, NULL_MESSAGE );

        for ( E item : expected )
        {
            String message = format( "Expected item is present in actual: '%s' (actual size: %d)",
                item, actual.size() );

            assertFalse( actual.contains( item ), message );
        }
    }

    /**
     * Asserts that the actual collection is empty.
     *
     * @param actual the collection.
     */
    public static void assertEmpty( Collection<?> actual )
    {
        assertNotNull( actual, NULL_MESSAGE );
        assertEquals( 0, actual.size(),
            format( "Actual collection is not empty with size: %d", actual.size() ) );
    }

    /**
     * Asserts that the actual collection is empty.
     *
     * @param actual the collection.
     */
    public static void assertNotEmpty( Collection<?> actual )
    {
        assertNotNull( actual, NULL_MESSAGE );
        assertTrue( !actual.isEmpty() );
    }

    /**
     * Asserts that the actual collection is of the expected size.
     *
     * @param expected the expected size.
     * @param actual the collection.
     */
    public static void assertSize( int expected, Collection<?> actual )
    {
        assertNotNull( actual, NULL_MESSAGE );
        assertEquals( expected, actual.size() );
    }

    /**
     * Asserts that the given collection contains any elements which match the
     * given predicate.
     *
     * @param <E>
     * @param actual the collection.
     * @param predicate the {@link Predicate}.
     */
    public static <E> void assertContains( Collection<E> actual, Predicate<E> predicate )
    {
        assertNotNull( actual, NULL_MESSAGE );
        assertTrue( actual.stream().anyMatch( predicate ), "No item in actual matches predicate" );
    }
}
