package cn.elytra.gtnh.cutcorners.mixins.late.gregtech;

import cn.elytra.gtnh.cutcorners.config.CutCornersConfig;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.MTEMultiBlockBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = MTEMultiBlockBase.class, remap = false)
public class MTEMultiBlockBaseMixin {

    @Shadow
    public int mMaxProgresstime;
    @Unique
    private static List<Class<?>> gtnhcc$runMachineApplied;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void gtnhcc$initClassList(CallbackInfo ci) {
        CutCornersConfig.onUpdateAndNow(() -> gtnhcc$runMachineApplied = CutCornersConfig.instance.getMaxProgressTimeRunMachineClasses());
    }

    @Inject(method = "runMachine", at = @At("HEAD"))
    private void gtnhcc$hookRunMachine(IGregTechTileEntity aBaseMetaTileEntity, long aTick, CallbackInfo ci) {
        if (mMaxProgresstime > 0) {
            if (gtnhcc$runMachineApplied != null && gtnhcc$runMachineApplied.contains(getClass())) {
                mMaxProgresstime = 1;
            }
        }
    }
}

