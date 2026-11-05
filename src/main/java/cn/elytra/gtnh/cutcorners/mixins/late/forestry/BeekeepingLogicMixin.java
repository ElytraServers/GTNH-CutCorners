package cn.elytra.gtnh.cutcorners.mixins.late.forestry;

import cn.elytra.gtnh.cutcorners.CutCorners;
import cn.elytra.gtnh.cutcorners.util.ModConst;
import forestry.apiculture.BeekeepingLogic;
import forestry.plugins.PluginApiculture;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = BeekeepingLogic.class, remap = false)
@Restriction(require = @Condition(ModConst.ModId.Forestry))
public class BeekeepingLogicMixin {

    @ModifyConstant(method = "queenWorkTick", constant = @Constant(intValue = PluginApiculture.ticksPerBeeWorkCycle))
    private int cc$queenInterval(int constant) {
        return CutCorners.getStrategy().getForestryQueenInterval(constant);
    }

}
