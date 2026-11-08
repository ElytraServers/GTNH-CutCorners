package cn.elytra.gtnh.cutcorners.mixinloader;

import me.fallenbreath.conditionalmixin.api.mixin.ConditionTester;

public class ExtraMixinTester implements ConditionTester {

    @Override
    public boolean isSatisfied(String mixinClassName) {
        if (mixinClassName.contains("MTEIceCreamMachineMixin")) {
            return ExtraMixinConfig.ENABLE_ICE_CREAM_PUNCHING;
        }

        throw new IllegalArgumentException("Unknown extra mixin class: " + mixinClassName);
    }
}
