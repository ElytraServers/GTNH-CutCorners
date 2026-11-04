package cn.elytra.gtnh.cutcorners.util.math;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public final class MathOps {

    static final Logger log = LogManager.getLogger();

    private MathOps() {
    }

    private static boolean isNaNTolerant(MathOp op) {
        return op instanceof NoOp || op instanceof ReplaceOp;
    }

    public static float safeApply(MathOp op, float value) {
        // if the op can accept the value (either the value is finite, or the op can handle NaN)
        // apply the op; or return the value as-is.
        return Float.isFinite(value) || isNaNTolerant(op) ? op.apply(value) : value;
    }

    public static double safeApply(MathOp op, double value) {
        // if the op can accept the value (either the value is finite, or the op can handle NaN)
        // apply the op; or return the value as-is.
        return Double.isFinite(value) || isNaNTolerant(op) ? op.apply(value) : value;
    }

    static MathOp coerceAtMost(BigDecimal max) {
        // val > max ? max : val
        return val -> val.compareTo(max) > 0 ? max : val;
    }

    static MathOp coerceAtLeast(BigDecimal min) {
        // val < min ? min : val
        return val -> val.compareTo(min) < 0 ? min : val;
    }

    static MathOp noop() {
        return NoOp.NO_OP;
    }

    static MathOp replace(BigDecimal value) {
        return new ReplaceOp(value);
    }

    static abstract class AbstractMathOp implements MathOp {
        protected final String op;
        protected final BigDecimal right;

        public AbstractMathOp(String op, BigDecimal right) {
            this.op = op;
            this.right = right;
        }

        public abstract BigDecimal apply(BigDecimal left);

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof AbstractMathOp that)) return false;
            return Objects.equals(op, that.op) && Objects.equals(right, that.right);
        }

        @Override
        public int hashCode() {
            return Objects.hash(op, right);
        }

        @Override
        public String toString() {
            return op + " " + right;
        }

        static class Add extends AbstractMathOp {
            public Add(BigDecimal right) {
                super("+", right);
            }

            @Override
            public BigDecimal apply(BigDecimal left) {
                return left.add(right);
            }
        }

        static class Subtract extends AbstractMathOp {
            public Subtract(BigDecimal right) {
                super("-", right);
            }

            @Override
            public BigDecimal apply(BigDecimal left) {
                return left.subtract(right);
            }
        }

        static class Multiply extends AbstractMathOp {
            public Multiply(BigDecimal right) {
                super("×", right);
            }

            @Override
            public BigDecimal apply(BigDecimal left) {
                return left.multiply(right);
            }
        }

        static class Divide extends AbstractMathOp {
            public Divide(BigDecimal right) {
                super("÷", right);
            }

            @Override
            public BigDecimal apply(BigDecimal left) {
                return left.divide(right, RoundingMode.HALF_UP);
            }
        }
    }

    static class MathOpList implements MathOp {
        private final List<MathOp> ops;

        public MathOpList(List<MathOp> ops) {
            this.ops = ops;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof MathOpList that)) return false;
            return Objects.equals(ops, that.ops);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(ops);
        }

        @Override
        public MathOp then(MathOp next) {
            if (next instanceof MathOpList thenList) {
                ops.addAll(thenList.ops);
                return this;
            } else if (next instanceof NoOp) {
                // nothing to do
                return this;
            } else {
                ops.add(next);
                return this;
            }
        }

        @Override
        public BigDecimal apply(BigDecimal left) {
            BigDecimal val = left;
            for (MathOp op : ops) val = op.apply(val);
            return val;
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    static class MemoizedOp implements MathOp {
        private static final long MAX_CACHE_SIZE = 100_000;
        private final LoadingCache<BigDecimal, BigDecimal> valueCache;

        public MemoizedOp(MathOp source) {
            this.valueCache = CacheBuilder.newBuilder()
                .maximumSize(MAX_CACHE_SIZE)
                .build(CacheLoader.from(source::apply));
        }

        @Override
        public MathOp memoize() {
            return this;
        }

        @Override
        public BigDecimal apply(BigDecimal left) {
            try {
                return valueCache.get(left);
            } catch (ExecutionException e) {
                throw new RuntimeException("Unable to load cache", e);
            }
        }
    }

    /// no-op op.
    static class NoOp implements MathOp {
        private static final NoOp NO_OP = new NoOp();

        private NoOp() {
        }

        @Override
        public BigDecimal apply(BigDecimal left) {
            return left;
        }

        @Override
        public int apply(int left) {
            return left;
        }

        @Override
        public long apply(long left) {
            return left;
        }

        @Override
        public float apply(float left) {
            return left;
        }

        @Override
        public double apply(double left) {
            return left;
        }

        @Override
        public MathOp then(MathOp next) {
            // inline this op
            return next;
        }

        @Override
        public MathOp memoize() {
            // nothing to memoize
            return this;
        }
    }

    /// A special op that discard all before values, and use set value.
    static class ReplaceOp implements MathOp {
        private final BigDecimal value;

        public ReplaceOp(BigDecimal value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof ReplaceOp replaceOp)) return false;
            return Objects.equals(value, replaceOp.value);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(value);
        }

        @Override
        public BigDecimal apply(BigDecimal dropped) {
            return value;
        }

        @Override
        public int apply(int left) {
            return value.intValue();
        }

        @Override
        public long apply(long left) {
            return value.longValue();
        }

        @Override
        public float apply(float left) {
            return value.floatValue();
        }

        @Override
        public double apply(double left) {
            return value.doubleValue();
        }

        @Override
        public MathOp coerceAtMost(BigDecimal max) {
            // this > max ? max : this
            return value.compareTo(max) > 0 ? replace(max) : this;
        }

        @Override
        public MathOp coerceAtLeast(BigDecimal min) {
            // this < min ? min : this
            return value.compareTo(min) < 0 ? replace(min) : this;
        }

        @Override
        public MathOp memoize() {
            // nothing to memoize
            return this;
        }
    }

}
