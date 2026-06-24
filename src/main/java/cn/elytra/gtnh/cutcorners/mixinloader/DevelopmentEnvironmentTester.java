package cn.elytra.gtnh.cutcorners.mixinloader;

import me.fallenbreath.conditionalmixin.api.mixin.ConditionTester;
import net.minecraft.launchwrapper.Launch;

public final class DevelopmentEnvironmentTester implements ConditionTester {

    private static boolean isDevEnvironment() {
        return (boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
    }

    @Override
    public boolean isSatisfied(String mixinClassName) {
        return isDevEnvironment();
    }
}
