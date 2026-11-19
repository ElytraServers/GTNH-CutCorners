package cn.elytra.gtnh.cutcorners.mixins.late.galacticraft;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import cn.elytra.gtnh.cutcorners.CutCorners;
import cn.elytra.gtnh.cutcorners.util.ModConst;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import micdoodle8.mods.galacticraft.core.tile.TileEntityFuelLoader;

@Mixin(value = TileEntityFuelLoader.class, remap = false)
@Restriction(require = @Condition(ModConst.ModId.Galacticraft))
public class TEFuelLoaderMixin {

    @Definition(
        id = "rocketFuelFactor",
        field = "Lmicdoodle8/mods/galacticraft/core/util/ConfigManagerCore;rocketFuelFactor:I")
    @Expression("2 * rocketFuelFactor")
    @ModifyExpressionValue(method = "updateEntity", at = @At("MIXINEXTRAS:EXPRESSION"), remap = true)
    private int cc$fuelChargeRate(int original) {
        return CutCorners.getStrategy().getGalacticraftFuelLoaderRate(original);
    }

}
