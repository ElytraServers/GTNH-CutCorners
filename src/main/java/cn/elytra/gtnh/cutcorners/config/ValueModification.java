package cn.elytra.gtnh.cutcorners.config;

/**
 * @see NoMod
 * @see Fixed
 * @see Rational
 */
public sealed interface ValueModification {

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

    record Fixed(int value) implements ValueModification {
        @Override
        public int getModifiedValue(int originalValue) {
            return value;
        }
    }

    record Rational(double multiplier) implements ValueModification {
        @Override
        public int getModifiedValue(int originalValue) {
            return (int) (multiplier * originalValue);
        }
    }
}
