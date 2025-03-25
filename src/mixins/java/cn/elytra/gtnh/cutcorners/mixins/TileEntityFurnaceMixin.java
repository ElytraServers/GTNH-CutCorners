package cn.elytra.gtnh.cutcorners.mixins;

import cn.elytra.gtnh.cutcorners.CutCorners;
import net.minecraft.tileentity.TileEntityFurnace;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(TileEntityFurnace.class)
public abstract class TileEntityFurnaceMixin {

    @ModifyConstant(method = "updateEntity", constant = @Constant(intValue = 200))
    private int gtnhcc$getMaxSmeltingTime(int value) {
        return CutCorners.getStrategy().getMaxFurnaceSmeltingTime(value);
    }
}
