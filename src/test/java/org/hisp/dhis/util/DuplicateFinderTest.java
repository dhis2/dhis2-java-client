package org.hisp.dhis.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class DuplicateFinderTest {
    @Test
    void testAddNoDuplicates() {
        DuplicateFinder finder = new DuplicateFinder();
        finder.add("apple");
        finder.add("banana");
        finder.add("cherry");
        
        Map<String, Integer> duplicates = finder.getDuplicates();
        
        assertTrue(duplicates.isEmpty(), "Should be no duplicates");
    }

    @Test
    void testAddSingleDuplicate() {
        DuplicateFinder finder = new DuplicateFinder();
        finder.add("apple");
        finder.add("banana");
        finder.add("apple");
        
        Map<String, Integer> duplicates = finder.getDuplicates();
        
        assertEquals(1, duplicates.size(), "Should be one duplicate");
        assertTrue(duplicates.containsKey("apple"), "Duplicate should be 'apple'");
        assertEquals(2, duplicates.get("apple"), "'apple' should have a count of 2");
    }

    @Test
    void testAddAllSingleDuplicate() {
        DuplicateFinder finder = new DuplicateFinder();
        
        List<String> values = List.of("apple", "banana", "apple");
        finder.addAll(values);
        
        Map<String, Integer> duplicates = finder.getDuplicates();
        
        assertEquals(1, duplicates.size(), "Should be one duplicate");
        assertTrue(duplicates.containsKey("apple"), "Duplicate should be 'apple'");
        assertEquals(2, duplicates.get("apple"), "'apple' should have a count of 2");
    }

    @Test
    void testAddMultipleDuplicates() {
        DuplicateFinder finder = new DuplicateFinder();
        finder.add("apple");
        finder.add("banana");
        finder.add("apple");
        finder.add("cherry");
        finder.add("banana");
        finder.add("banana");
        
        Map<String, Integer> duplicates = finder.getDuplicates();
        
        assertEquals(2, duplicates.size(), "Should be two duplicates");
        assertTrue(duplicates.containsKey("apple"), "Duplicate should contain 'apple'");
        assertEquals(2, duplicates.get("apple"), "'apple' should have a count of 2");
        assertTrue(duplicates.containsKey("banana"), "Duplicate should contain 'banana'");
        assertEquals(3, duplicates.get("banana"), "'banana' should have a count of 3");
    }

    @Test
    void testAddCaseSensitivityDuplicates() {
        DuplicateFinder finder = new DuplicateFinder();
        finder.add("Apple");
        finder.add("Apple");
        finder.add("apple");
        finder.add("apple");

        Map<String, Integer> duplicates = finder.getDuplicates();
        assertEquals(2, duplicates.size(), "Should be two duplicates (case-sensitive)");
        assertTrue(duplicates.containsKey("Apple"), "'Apple' should be a duplicate");
        assertEquals(2, duplicates.get("Apple"), "'Apple' should have a count of 2");
        assertTrue(duplicates.containsKey("apple"), "'apple' should be a duplicate");
        assertEquals(2, duplicates.get("apple"), "'apple' should have a count of 2");
    }

    @Test
    void testAddEmptyInput() {
        DuplicateFinder finder = new DuplicateFinder();
        
        Map<String, Integer> duplicates = finder.getDuplicates();
        
        assertTrue(duplicates.isEmpty(), "Should be no duplicates for empty input");
    }

    @Test
    void testAddOnlyDuplicates() {
        DuplicateFinder finder = new DuplicateFinder();
        finder.add("a");
        finder.add("a");
        finder.add("b");
        finder.add("b");
        finder.add("b");
        
        Map<String, Integer> duplicates = finder.getDuplicates();
        
        assertEquals(2, duplicates.size(), "Should be two duplicates");
        assertTrue(duplicates.containsKey("a"), "Duplicate should contain 'a'");
        assertEquals(2, duplicates.get("a"), "'a' should have a count of 2");
        assertTrue(duplicates.containsKey("b"), "Duplicate should contain 'b'");
        assertEquals(3, duplicates.get("b"), "'b' should have a count of 3");
    }
}