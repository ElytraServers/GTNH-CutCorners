package cn.elytra.gtnh.cutcorners.mixins.late.gregtech;

import cn.elytra.gtnh.cutcorners.CutCorners;
import cn.elytra.gtnh.cutcorners.config.CutCornersConfig;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.MTEMultiBlockBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collections;
import java.util.List;

@Mixin(value = MTEMultiBlockBase.class, remap = false)
public class MTEMultiBlockBaseMixin {

    @Shadow
    public int mMaxProgresstime;

    @Unique
    private static List<Class<?>> gtnhcc$runMachineApplied;

    @Unique
    private static boolean gtnhcc$runMachineAppliedInitialized = false;

    @Unique
    private static void gtnhcc$ensureRunMachineAppliedInitialized() {
        if (gtnhcc$runMachineAppliedInitialized) return;
        gtnhcc$runMachineAppliedInitialized = true;

        // Lazy init: runs the first time any MTEMultiBlockBase calls runMachine.
        // This works around late mixin <clinit> not executing on already-loaded classes.
        try {
            gtnhcc$runMachineApplied = CutCornersConfig.instance.getMaxProgressTimeRunMachineClasses();
        } catch (Exception e) {
            CutCorners.LOG.error("Failed to initialize runMachine acceleration list", e);
            gtnhcc$runMachineApplied = Collections.emptyList();
        }

        // Also register for config updates so changes take effect without restart
        CutCornersConfig.onUpdate(() -> {
            try {
                gtnhcc$runMachineApplied = CutCornersConfig.instance.getMaxProgressTimeRunMachineClasses();
            } catch (Exception e) {
                CutCorners.LOG.error("Failed to update runMachine acceleration list", e);
                gtnhcc$runMachineApplied = Collections.emptyList();
            }
        });
    }

    @Inject(method = "runMachine", at = @At("HEAD"))
    private void gtnhcc$hookRunMachine(IGregTechTileEntity aBaseMetaTileEntity, long aTick, CallbackInfo ci) {
        if (mMaxProgresstime > 0) {
            gtnhcc$ensureRunMachineAppliedInitialized();
            // Defensive null check in case initialization failed silently
            if (gtnhcc$runMachineApplied != null && gtnhcc$runMachineApplied.contains(getClass())) {
                mMaxProgresstime = 1;
            }
        }
    }

}
