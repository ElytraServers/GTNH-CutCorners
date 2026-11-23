package cn.elytra.gtnh.cutcorners.mixins.late.et_futurum_requiem;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import cn.elytra.gtnh.cutcorners.CutCorners;
import cn.elytra.gtnh.cutcorners.util.ModConst;
import ganymedes01.etfuturum.tileentities.TileEntityBlastFurnace;
import ganymedes01.etfuturum.tileentities.TileEntitySmoker;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;

@Mixin(value = { TileEntitySmoker.class, TileEntityBlastFurnace.class }, remap = false)
@Restriction(require = @Condition(ModConst.ModId.EFR))
public class BlastFurnaceAndSmoker_Mixin {

    @ModifyExpressionValue(method = "updateEntity", at = @At(value = "CONSTANT", args = "intValue=100"))
    private int gtnhcc$getMaxSmeltingTime(int value) {
        return Math.max(1, CutCorners.getStrategy().getMaxFurnaceSmeltingTime(value) / 2);
    }

}
