package cn.elytra.gtnh.cutcorners.util.math;

/// A primitive specialized [MathOp].
public interface MathFunc {

    int apply(int left);

    long apply(long left);

    float apply(float left);

    double apply(double left);

}
