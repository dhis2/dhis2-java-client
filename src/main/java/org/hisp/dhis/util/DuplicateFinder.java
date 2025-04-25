package org.hisp.dhis.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Duplicate value finder.
 */
public class DuplicateFinder {

  private Map<String, Integer> valueCounts;

  /**
   * Constructor.
   */
  public DuplicateFinder() {
    this.valueCounts = new HashMap<>();
  }

  /**
   * Constructor.
   * 
   * @param values the collection of values.
   */
  public DuplicateFinder(Collection<String> values) {
    this();
    addAll(values);
  }

  /**
   * Adds a value.
   *
   * @param value the value.
   */
  public void add(String value) {
    valueCounts.put(value, valueCounts.getOrDefault(value, 0) + 1);
  }
  
  /**
   * Adds a collection of values.
   * 
   * @param values the values.
   */
  public void addAll(Collection<String> values) {
    values.forEach(value -> add(value));
  }

  /**
   * Returns the duplicates as a map, where the key is the duplicate value and the value is the
   * number of occurrences.
   *
   * @return the duplicates as a map.
   */
  public Map<String, Integer> getDuplicates() {
    Map<String, Integer> duplicates = new HashMap<>();
    for (Map.Entry<String, Integer> entry : valueCounts.entrySet()) {
      if (entry.getValue() > 1) {
        duplicates.put(entry.getKey(), entry.getValue());
      }
    }
    return duplicates;
  }
}