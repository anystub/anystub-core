package org.anystub;

@FunctionalInterface
public interface Inverter<T extends Object> {
    T invert(T t, java.util.function.Function<Iterable<String>, T> decoderFunction);
}
