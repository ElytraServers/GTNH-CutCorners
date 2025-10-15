package cn.elytra.gtnh.cutcorners.util;

public class ResearchStationHelper {

    private static final MaskUtils.MaskOperator AMP_OP = new MaskUtils.MaskOperator(16, 0);
    private static final MaskUtils.MaskOperator MIN_COMP_OP = new MaskUtils.MaskOperator(16, 16);

    public static int getAmp(int specialValue) {
        return AMP_OP.getValue(specialValue);
    }

    public static int getSpecialValueAtAmp(int specialValue, int amp) {
        return AMP_OP.setValue(specialValue, amp);
    }

    public static int getMinComputation(int specialValue) {
        return MIN_COMP_OP.getValue(specialValue);
    }

    public static int getSpecialValueAtMinComputation(int specialValue, int minComputation) {
        return MIN_COMP_OP.setValue(specialValue, minComputation);
    }

}
