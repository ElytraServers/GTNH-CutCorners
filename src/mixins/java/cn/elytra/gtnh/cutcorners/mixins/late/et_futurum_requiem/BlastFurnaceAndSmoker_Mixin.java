package cn.elytra.gtnh.cutcorners.mixins.late.et_futurum_requiem;

import cn.elytra.gtnh.cutcorners.CutCorners;
import ganymedes01.etfuturum.tileentities.TileEntityBlastFurnace;
import ganymedes01.etfuturum.tileentities.TileEntitySmoker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = {TileEntityBlastFurnace.class, TileEntitySmoker.class}, remap = false)
public class BlastFurnaceAndSmoker_Mixin {

    @ModifyConstant(method = "updateEntity", constant = @Constant(intValue = 100))
    private int gtnhcc$getMaxSmeltingTime(int value) {
        return CutCorners.getStrategy().getMaxFurnaceSmeltingTime(value);
    }

}
