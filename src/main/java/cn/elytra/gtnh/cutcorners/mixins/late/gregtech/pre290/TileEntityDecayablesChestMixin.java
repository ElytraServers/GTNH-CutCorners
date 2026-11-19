package cn.elytra.gtnh.cutcorners.mixins.late.gregtech.pre290;

import cn.elytra.gtnh.cutcorners.CutCorners;
import cn.elytra.gtnh.cutcorners.internal.GTPPTickableItemHook;
import cn.elytra.gtnh.cutcorners.util.ModConst;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import gtPlusPlus.core.tileentities.general.TileEntityDecayablesChest;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import org.spongepowered.asm.mixin.Mixin;

@SuppressWarnings("SpellCheckingInspection") // decayables
@Mixin(value = TileEntityDecayablesChest.class, remap = false)
@Restriction(require = @Condition(ModConst.ModId.GregTech))
public abstract class TileEntityDecayablesChestMixin {

    @WrapMethod(method = "updateEntity", remap = true)
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
