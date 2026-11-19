package cn.elytra.gtnh.cutcorners.mixins.late.gregtech.pre290;

import cn.elytra.gtnh.cutcorners.CutCorners;
import cn.elytra.gtnh.cutcorners.util.ModConst;
import gregtech.common.tileentities.machines.basic.MTEScanner;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = MTEScanner.class, remap = false)
@Restriction(require = @Condition(ModConst.ModId.GregTech))
public abstract class GT_MetaTileEntity_ScannerMixin {

    @ModifyArg(method = "checkRecipe", at = @At(value = "INVOKE", target = "Lgregtech/common/tileentities/machines/basic/MTEScanner;calculateOverclockedNess(II)V"), index = 1)
    private int gtnhcc$overwriteOverclockedNessCall(int value) {
        return CutCorners.getStrategy().getMaxProgressTime(this, value);
    }
}
