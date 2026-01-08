package cn.elytra.gtnh.cutcorners.mixins.late.gregtech;

import cn.elytra.gtnh.cutcorners.CutCorners;
import gregtech.api.interfaces.ITexture;
import gregtech.api.metatileentity.implementations.MTEBasicMachine;
import gregtech.common.tileentities.machines.basic.MTERockBreaker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MTERockBreaker.class, remap = false)
public abstract class MTERockBreakerMixin extends MTEBasicMachine {

    public MTERockBreakerMixin(int aID, String aName, String aNameRegional, int aTier, int aAmperage,
        String aDescription, int aInputSlotCount, int aOutputSlotCount, ITexture... aOverlays) {
        super(aID, aName, aNameRegional, aTier, aAmperage, aDescription, aInputSlotCount, aOutputSlotCount, aOverlays);
    }

    @Inject(
        method = "checkRecipe", at = @At(
        value = "INVOKE",
        target = "Lgregtech/common/tileentities/machines/basic/MTERockBreaker;calculateOverclockedNess(II)V",
        shift = At.Shift.AFTER))
    private void gtnhcc$modifyTime(CallbackInfoReturnable<Integer> cir) {
        mMaxProgresstime = CutCorners.getStrategy().getMaxProgressTime(this, mMaxProgresstime);
    }

}
