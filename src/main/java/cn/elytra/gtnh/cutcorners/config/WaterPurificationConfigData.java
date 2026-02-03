package cn.elytra.gtnh.cutcorners.config;

public class WaterPurificationConfigData {

    private static final int MODE_NONE = 0;
    private static final int MODE_FIXED = 1;
    private static final int MODE_ADDITION = 2;

    private final int mode;
    private final float value;

    private WaterPurificationConfigData(int mode, float value) {
        this.mode = mode;
        this.value = value;
    }

    public static WaterPurificationConfigData none() {
        return new WaterPurificationConfigData(MODE_NONE, 0);
    }

    public static WaterPurificationConfigData fixed(float value) {
        return new WaterPurificationConfigData(MODE_FIXED, value);
    }

    public static WaterPurificationConfigData additional(float value) {
        return new WaterPurificationConfigData(MODE_ADDITION, value);
    }

    public float getValue(float original) {
        return switch (mode) {
            case MODE_NONE -> original;
            case MODE_FIXED -> value;
            case MODE_ADDITION -> original + value;
            default -> throw new IllegalStateException("Unexpected value: " + mode);
        };
    }
}
