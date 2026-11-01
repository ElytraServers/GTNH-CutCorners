package cn.elytra.gtnh.cutcorners.util.math;

import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.function.Function;

public interface MathOp extends Function<BigDecimal, BigDecimal>, MathFunc {

    BigDecimal apply(BigDecimal left);

    // region interface MathFunc
    @Override
    default int apply(int left) {
        return this.apply(new BigDecimal(left)).intValue();
    }

    @Override
    default long apply(long left) {
        return this.apply(new BigDecimal(left)).longValue();
    }

    @Override
    default float apply(float left) {
        return this.apply(new BigDecimal(left)).floatValue();
    }

    @Override
    default double apply(double left) {
        return this.apply(new BigDecimal(left)).doubleValue();
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
