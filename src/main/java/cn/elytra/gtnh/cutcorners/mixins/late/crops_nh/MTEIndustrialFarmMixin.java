package cn.elytra.gtnh.cutcorners.mixins.late.crops_nh;

import cn.elytra.gtnh.cutcorners.CutCorners;
import cn.elytra.gtnh.cutcorners.util.ModConst;
import com.gtnewhorizon.cropsnh.handler.ConfigurationHandler;
import com.gtnewhorizon.cropsnh.tileentity.multi.MTEIndustrialFarm;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = MTEIndustrialFarm.class, remap = false)
@Restriction(require = @Condition(ModConst.ModId.CropsNH))
public class MTEIndustrialFarmMixin {

    @ModifyReturnValue(method = "getGrowthProgressPerCycle", at = @At("RETURN"))
    private double cc$replaceGrowth(double original) {
        // Industrial Farm doesn't apply the multiplier in the configuration.
        // we must explicitly call it to make it work.
        float multiplier = CutCorners.getStrategy().getCropsNHMultiplier(ConfigurationHandler.growthMultiplier);
        return original * multiplier;
    }

}
