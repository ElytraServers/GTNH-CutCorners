package cn.elytra.gtnh.cutcorners.mixins.late.gregtech;

import cn.elytra.gtnh.cutcorners.internal.GTPPTickableItemHook;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import gtPlusPlus.core.item.base.BaseItemTickable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = BaseItemTickable.class, remap = false)
public class BaseItemTickableMixin {

    @ModifyReturnValue(method = "getMaxTicks", at = @At("RETURN"))
    private int gtnhcc$overrideMaxTicks(int original) {
        return GTPPTickableItemHook.shouldOverrideMaxTickTime() ? 1 : original;
    }

}
