package cn.elytra.gtnh.cutcorners.util.fp;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/// [LazyMapping] transforms the [given input][source] to output type by [mapping] realtime.
public class LazyMapping<T, R> implements Supplier<R> {
    private static final Object UNDEFINED = new Object();

    private final Supplier<T> source;
    private final Function<T, R> mapping;

    private Object lastValue = UNDEFINED;
    private R value;

    public LazyMapping(Supplier<T> source, Function<T, R> mapping) {
        this.source = source;
        this.mapping = mapping;
    }

    public static <T, R> LazyMapping<T, R> lazyMapping(Supplier<T> source, Function<T, R> mapping) {
        return new LazyMapping<>(source, mapping);
    }

    @Override
    public R get() {
        T newValue = source.get();
        if (lastValue == UNDEFINED || !Objects.equals(lastValue, newValue)) {
            lastValue = newValue;
            return value = mapping.apply(newValue);
        }
        return value;
    }
}
