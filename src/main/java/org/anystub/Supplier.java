package org.anystub;

/**
 * functional interface.
 */
@FunctionalInterface
public interface Supplier<T extends Object, E extends Throwable> {
    T get() throws E;

}
