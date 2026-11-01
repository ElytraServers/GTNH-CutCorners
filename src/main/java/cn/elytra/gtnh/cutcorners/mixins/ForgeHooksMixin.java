package cn.elytra.gtnh.cutcorners.mixins;

import cn.elytra.gtnh.cutcorners.CutCorners;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraftforge.common.ForgeHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = ForgeHooks.class, remap = false)
public class ForgeHooksMixin {

    @ModifyReturnValue(method = "blockStrength", at = @At("RETURN"))
    private static float cc$blockStrength(float original) {
        return CutCorners.getStrategy().getBlockStrength(original);
    }

    @ModifyReturnValue(method = "canHarvestBlock", at = @At("RETURN"))
    private static boolean cc$canHarvestBlock(boolean original) {
        return CutCorners.getStrategy().canHarvestBlock(original);
    }

}
