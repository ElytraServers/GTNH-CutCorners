package cn.elytra.gtnh.cutcorners.mixins.late.tconstruct;

import cn.elytra.gtnh.cutcorners.config.NewConfig;
import cn.elytra.gtnh.cutcorners.util.ModConst;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.block.material.Material;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import tconstruct.items.tools.Hammer;
import tconstruct.library.tools.AOEHarvestTool;

@Mixin(value = Hammer.class, remap = false)
@Restriction(require = @Condition(ModConst.ModId.TinkersConstruct))
public abstract class HammerMixin extends AOEHarvestTool {

    public HammerMixin(int baseDamage, int breakRadius, int breakDepth) {
        super(baseDamage, breakRadius, breakDepth);
    }

    @Unique
    private static Material[] CC$HAMMER_AND_EXCAVATOR_MATERIALS;

    @ModifyReturnValue(method = "getEffectiveMaterials", at = @At("RETURN"))
    private Material[] cc$effectiveMaterials(Material[] original) {
        if (NewConfig.ticExtraHammerMaterial) {
            if (CC$HAMMER_AND_EXCAVATOR_MATERIALS == null) {
                CC$HAMMER_AND_EXCAVATOR_MATERIALS = ArrayUtils.addAll(original, ExcavatorAccessor.getMaterials());
            }
            return CC$HAMMER_AND_EXCAVATOR_MATERIALS;
        }
        return original;
    }

}
