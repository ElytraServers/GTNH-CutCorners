package cn.elytra.gtnh.cutcorners.util.fp;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.NoSuchElementException;
import java.util.concurrent.Callable;

@NullMarked
public sealed interface MayThrow<T> {

    T unwrap() throws NoSuchElementException;

    @Nullable Throwable exception();

    static <T> MayThrow<T> capture(Callable<T> function) {
        try {
            T value = function.call();
            return new Ok<>(value);
        } catch (Throwable e) {
            return new Err<>(e);
        }
    }

    record Ok<T>(T value) implements MayThrow<T> {

        @Override
        public T unwrap() {
            return value;
        }

        @Override
        public @Nullable Throwable exception() {
            return null;
        }
    }

    record Err<T>(Throwable exception) implements MayThrow<T> {

        @Override
        public T unwrap() {
            throw new NoSuchElementException("Err variant: " + exception.getMessage());
        }
    }

}
