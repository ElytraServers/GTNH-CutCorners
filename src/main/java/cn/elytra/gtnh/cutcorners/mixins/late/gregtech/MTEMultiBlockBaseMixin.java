package cn.elytra.gtnh.cutcorners.mixins.late.gregtech;

import cn.elytra.gtnh.cutcorners.config.CutCornersConfig;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.MTEMultiBlockBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MTEMultiBlockBase.class, remap = false)
public class MTEMultiBlockBaseMixin {

    @Shadow
    public int mMaxProgresstime;

    @Inject(method = "runMachine", at = @At("HEAD"))
    private void gtnhcc$hookRunMachine(IGregTechTileEntity aBaseMetaTileEntity, long aTick, CallbackInfo ci) {
        if (mMaxProgresstime > 0) {
            if (CutCornersConfig.MAX_PROGRESS_TIME_RUN_MACHINE_CLASSES.get().contains(getClass())) {
                mMaxProgresstime = 1;
            }
        }
    }

}
