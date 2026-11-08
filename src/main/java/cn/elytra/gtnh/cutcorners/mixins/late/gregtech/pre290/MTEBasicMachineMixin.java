package cn.elytra.gtnh.cutcorners.mixins.late.gregtech.pre290;

import cn.elytra.gtnh.cutcorners.CutCorners;
import cn.elytra.gtnh.cutcorners.api.CutCornersAPI;
import cn.elytra.gtnh.cutcorners.util.ModConst;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.MTEBasicMachine;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MTEBasicMachine.class, remap = false)
@Restriction(require = @Condition(ModConst.ModId.GregTech))
public class MTEBasicMachineMixin {

    @Shadow
    public int mMaxProgresstime;

    @ModifyConstant(method = "onPostTick", constant = @Constant(intValue = 1000))
    private int gtnhcc$modifyAutoOutputFluidAmount(int constant) {
        if (CutCorners.getStrategy().isImmediateMode()) {
            return Integer.MAX_VALUE;
        }
        return constant;
    }

    @Inject(method = "onPostTick", at = @At("HEAD"))
    private void gtnhcc$hookRunMachine(IGregTechTileEntity aBaseMetaTileEntity, long aTick, CallbackInfo ci) {
        if (mMaxProgresstime > 0) {
            if (CutCornersAPI.isMTEAccelerated(getClass())) {
                mMaxProgresstime = 1;
            }
        }
    }

}
