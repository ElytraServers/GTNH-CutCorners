package cn.elytra.gtnh.cutcorners.mixins.late.railcraft;

import cn.elytra.gtnh.cutcorners.util.ModConst;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import mods.railcraft.common.blocks.machine.alpha.TileCokeOven;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = TileCokeOven.class, remap = false)
@Restriction(require = @Condition(ModConst.ModId.Railcraft))
public class TileCokeOvenMixin {

    @ModifyConstant(method = "updateEntity", constant = @Constant(intValue = 50))
    private int gtnhcc$updateCookStepLength(int value) {
        return 1;
    }

}
