package cn.elytra.gtnh.cutcorners.mixins.late.tconstruct;

import cn.elytra.gtnh.cutcorners.util.ModConst;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.block.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import tconstruct.items.tools.Excavator;

@Mixin(value = Excavator.class, remap = false)
@Restriction(require = @Condition(ModConst.ModId.TinkersConstruct))
public interface ExcavatorAccessor {

    @Accessor("materials")
    static Material[] getMaterials() {
        throw new AssertionError();
    }

}
