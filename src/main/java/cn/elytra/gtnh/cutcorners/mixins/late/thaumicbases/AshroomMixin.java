package cn.elytra.gtnh.cutcorners.mixins.late.thaumicbases;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import tb.common.block.BlockAshroom;

@Mixin(value = BlockAshroom.class, remap = false)
public abstract class AshroomMixin {

    // Boost each aspect orb spawned when a fully grown Ashroom is harvested: value 1 -> 100.
    @ModifyConstant(
        method = "onBlockHarvested",
        slice = @Slice(from = @At(value = "NEW", target = "thaumcraft/common/entities/EntityAspectOrb")),
        constant = @Constant(intValue = 1))
    private int cc$boostAshroomOrbValue(int original) {
        return 100;
    }

    @ModifyConstant(method = "onBlockHarvested", constant = @Constant(intValue = 8))
    private int cc$boostAshroomDropCountValue(int original) {
        return original * 6;
    }

    @Redirect(method = "onBlockHarvested", at = @At(value = "INVOKE", target = "Ljava/lang/Math;round(D)J"))
    private static long cc$boostAshroomDropCountRound(double value) {
        return Math.round(value) * 6;
    }
}
