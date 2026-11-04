package cn.elytra.gtnh.cutcorners.mixins.late.gregtech.cover;

import cn.elytra.gtnh.cutcorners.config.NewConfig;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import gregtech.common.covers.CoverArm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = CoverArm.class, remap = false)
public class CoverArmMixin {

    @ModifyReturnValue(method = "getMinimumTickRate", at = @At("RETURN"))
    private int cc$tickRate(int original) {
        if (NewConfig.maxTickRateRobotArmCover) {
            return 1;
        }
        return original;
    }

}
