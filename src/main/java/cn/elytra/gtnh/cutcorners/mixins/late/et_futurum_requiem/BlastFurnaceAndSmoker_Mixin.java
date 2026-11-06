package cn.elytra.gtnh.cutcorners.mixins.late.et_futurum_requiem;

import cn.elytra.gtnh.cutcorners.CutCorners;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import ganymedes01.etfuturum.tileentities.TileEntityBlastFurnace;
import ganymedes01.etfuturum.tileentities.TileEntitySmoker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = {TileEntitySmoker.class, TileEntityBlastFurnace.class}, remap = false)
public class BlastFurnaceAndSmoker_Mixin {

    @ModifyExpressionValue(method = "updateEntity", at = @At(value = "CONSTANT", args = "intValue=100"))
    private int gtnhcc$getMaxSmeltingTime(int value) {
        return Math.max(1, CutCorners.getStrategy().getMaxFurnaceSmeltingTime(value) / 2);
    }

}
