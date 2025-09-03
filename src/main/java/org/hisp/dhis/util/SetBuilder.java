package org.hisp.dhis.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Builder of sets.
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * Set<T> = new SetBuilder<T>()
 *   .add(valueA)
 *   .add(valueB, valueC)
 *   .addAll(Set.of(valueD, valueE))
 *   .build();
 * }</pre>
 */
public class SetBuilder<T> {
  private final Set<T> set;
  
  /**
   * Default constructor.
   */
  public SetBuilder() {
    this.set = new HashSet<>();
  }

  /**
   * Constructor.
   * 
   * @param initial the initial set.
   */
  public SetBuilder(Set<T> initial) {
    this.set = new HashSet<>(initial);
  }

  /**
   * Adds the given item.
   * 
   * @param <T> the type.
   * @param item the item.
   * @return this {@link ListBuilder}.
   */
  public final SetBuilder<T> add(T item) {
    this.set.add(item);
    return this;
  }

  /**
   * Adds the given items.
   * 
   * @param <T> the type.
   * @param items the items.
   * @return this {@link ListBuilder}.
   */
  @SafeVarargs
  public final SetBuilder<T> add(T... items) {
    this.set.addAll(new HashSet<T>(Arrays.asList(items)));
    return this;
  }

  /**
   * Adds the given items.
   * 
   * @param <T> the type.
   * @param item the items.
   * @return this {@link ListBuilder}.
   */
  public final SetBuilder<T> addAll(Set<? extends T> items) {
    this.set.addAll(items);
    return this;
  }
  
  /**
   * Builds the set.
   * 
   * @return a set.
   */
  public final Set<T> build() {
    return Collections.unmodifiableSet(set);
  }
}
