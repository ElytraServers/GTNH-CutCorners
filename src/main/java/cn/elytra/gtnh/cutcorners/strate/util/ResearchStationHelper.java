package cn.elytra.gtnh.cutcorners.strate.util;

public class ResearchStationHelper {

    private static final int MASK = 0xFFFF;

    public static int getAmp(int specialValue) {
        return specialValue & MASK;
    }

    public static int getSpecialValueAtAmp(int specialValue, int amp) {
        return (specialValue & ~MASK) | (amp & MASK);
    }

    public static int getMinComputation(int specialValue) {
        return specialValue >> 16 & MASK;
    }

    public static int getSpecialValueAtMinComputation(int specialValue, int minComputation) {
        return (specialValue & MASK) | (minComputation & MASK << 16);
    }

}
