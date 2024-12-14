package cn.elytra.gtnh.cutcorners.config;

import com.github.bsideup.jabel.Desugar;

/**
 * @see NoMod
 * @see Fixed
 * @see Rational
 */
public interface ValueModification {

    int getModifiedValue(int originalValue);

    default int getModifiedValue(int originalValue, int min) {
        return Math.max(min, getModifiedValue(originalValue));
    }

    final class NoMod implements ValueModification {
        @Override
        public int getModifiedValue(int originalValue) {
            return originalValue;
        }
    }

    @Desugar
    record Fixed(int value) implements ValueModification {
        @Override
        public int getModifiedValue(int originalValue) {
            return value;
        }
    }

    @Desugar
    record Rational(double multiplier) implements ValueModification {
        @Override
        public int getModifiedValue(int originalValue) {
            return (int) multiplier * originalValue;
        }
    }
}
