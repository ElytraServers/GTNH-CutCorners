package cn.elytra.gtnh.cutcorners.mixins.late.gregtech;

import cn.elytra.gtnh.cutcorners.CutCorners;
import cn.elytra.gtnh.cutcorners.internal.GTPPTickableItemHook;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import gtPlusPlus.core.tileentities.general.TileEntityDecayablesChest;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = TileEntityDecayablesChest.class, remap = false)
public class TileEntityDecayablesChestMixin {

    @WrapMethod(method = "updateEntity")
    private void gtnhcc$inject(Operation<Void> original) {
        if (CutCorners.getStrategy().isImmediateMode()) {
            // when in immediate mode, we set the context to "max tick = 1".
            // so that the item will decay immediately.
            try {
                GTPPTickableItemHook.setShouldOverrideMaxTickTime(true);
                original.call();
            } finally {
                GTPPTickableItemHook.setShouldOverrideMaxTickTime(false);
            }
        } else {
            original.call();
        }
    }

}
