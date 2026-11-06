package cn.elytra.gtnh.cutcorners.mixins;

import cn.elytra.gtnh.cutcorners.CutCorners;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraftforge.common.ForgeHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = ForgeHooks.class, remap = false)
public class ForgeHooksMixin {

    @ModifyReturnValue(method = "blockStrength", at = @At(value = "RETURN", ordinal = 1))
    private static float cc$blockStrengthNonHarvest(float original) {
        // this is used when the tool mismatches.
        // normally expected a slower results.
        return CutCorners.getStrategy().getBlockStrength(original, false);
    }

    @ModifyReturnValue(method = "blockStrength", at = @At(value = "RETURN", ordinal = 2))
    private static float cc$blockStrengthHarvest(float original) {
        // this is used when the tool matches, or doesn't require tools.
        // should be faster.
        return CutCorners.getStrategy().getBlockStrength(original, true);
    }

}
