package cn.elytra.gtnh.cutcorners.util.math;

import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.function.Function;

import static cn.elytra.gtnh.cutcorners.util.math.MathOps.log;

public interface MathOp extends Function<BigDecimal, BigDecimal>, MathFunc {

    BigDecimal apply(BigDecimal left);

    /// Apply the value.
    ///
    /// - if the value is **[finite][Double#isFinite(double)]**, it works like [#apply(BigDecimal)].
    /// - if this OP can handle **[NaN][Double#NaN]** or **[Infinite][Double#isInfinite(double)]**, it works like [#apply(BigDecimal)] still.
    /// - otherwise, the value is returned as-is, unchanged.
    default float safeApply(float value) {
        return MathOps.safeApply(this, value);
    }

    /// See [#safeApply(float)].
    default double safeApply(double value) {
        return MathOps.safeApply(this, value);
    }

    // region interface MathFunc
    @Override
    default int apply(int left) {
        try {
            return this.apply(new BigDecimal(left)).intValue();
        } catch (NumberFormatException e) {
            log.warn("MathOp format error: {}", left, e);
            return left; // return as-is
        }
    }

    @Override
    default long apply(long left) {
        try {
            return this.apply(new BigDecimal(left)).longValue();
        } catch (NumberFormatException e) {
            log.warn("MathOp format error: {}", left, e);
            return left; // return as-is
        }
    }

    @Override
    default float apply(float left) {
        try {
            return this.apply(new BigDecimal(left)).floatValue();
        } catch (NumberFormatException e) {
            log.warn("MathOp format error: {}", left, e);
            return left; // return as-is
        }
    }

    @Override
    default double apply(double left) {
        try {
            return this.apply(new BigDecimal(left)).doubleValue();
        } catch (NumberFormatException e) {
            log.warn("MathOp format error: {}", left, e);
            return left; // return as-is
        }
    }
    // endregion interface MathFunc

    default MathOp then(MathOp next) {
        if (next instanceof MathOps.ReplaceOp) return next;
        return new MathOps.MathOpList(Lists.newArrayList(this, next));
    }

    default MathOp coerceAtMost(BigDecimal max) {
        return then(MathOps.coerceAtMost(max));
    }

    default MathOp coerceAtLeast(BigDecimal min) {
        return then(MathOps.coerceAtLeast(min));
    }

    default MathOp memoize() {
        return new MathOps.MemoizedOp(this);
    }

    static MathOp noop() {
        return MathOps.noop();
    }

    static MathOp replace(BigDecimal value) {
        return MathOps.replace(value);
    }

    static MathOp plus(BigDecimal right) {
        if (right.equals(BigDecimal.ZERO)) return noop();
        return new MathOps.AbstractMathOp.Add(right);
    }

    static MathOp subtract(BigDecimal right) {
        if (right.equals(BigDecimal.ZERO)) return noop();
        return new MathOps.AbstractMathOp.Subtract(right);
    }

    static MathOp multiply(BigDecimal right) {
        if (right.equals(BigDecimal.ONE)) return noop();
        return new MathOps.AbstractMathOp.Multiply(right);

    }

    static MathOp divide(BigDecimal right) {
        if (right.equals(BigDecimal.ZERO)) throw new IllegalArgumentException("can't divide by zero!");
        if (right.equals(BigDecimal.ONE)) return noop();
        return new MathOps.AbstractMathOp.Divide(right);
    }

}
