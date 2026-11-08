package cn.elytra.gtnh.cutcorners.mixins.late.gregtech.pre290;

import cn.elytra.gtnh.cutcorners.internal.GTPPTickableItemHook;
import cn.elytra.gtnh.cutcorners.util.ModConst;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import gtPlusPlus.core.item.base.BaseItemTickable;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = BaseItemTickable.class, remap = false)
@Restriction(require = @Condition(ModConst.ModId.GregTech))
public class BaseItemTickableMixin {

    @ModifyReturnValue(method = "getMaxTicks", at = @At("RETURN"))
    private int gtnhcc$overrideMaxTicks(int original) {
        return GTPPTickableItemHook.shouldOverrideMaxTickTime() ? 1 : original;
    }

}
