package cn.elytra.gtnh.cutcorners.mixins.late.galacticraft;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import cn.elytra.gtnh.cutcorners.CutCorners;
import cn.elytra.gtnh.cutcorners.util.ModConst;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import micdoodle8.mods.galacticraft.core.tile.TileEntityOxygenCompressor;

@Mixin(value = TileEntityOxygenCompressor.class, remap = false)
@Restriction(require = @Condition(ModConst.ModId.Galacticraft))
public class TEOxygenCompressorMixin {

    @ModifyExpressionValue(
        method = "updateEntity",
        at = @At(value = "CONSTANT", args = "intValue=2"),
        slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItemDamage()I")),
        remap = true)
    private int cc$oxygenChargeRate(int original) {
        // NOTE: the original logic of Galacticraft doesn't check if the consumed amount is more than needed,
        // so it's NOT a bug that all oxygen are consumed at once even if the container is nearly full.
        return CutCorners.getStrategy().getGalacticraftOxygenCompressorChargeRate(original);
    }

}
