package cn.elytra.gtnh.cutcorners.mixins.late.gregtech.misc;

import cn.elytra.gtnh.cutcorners.util.ModConst;
import gregtech.api.metatileentity.BaseMetaTileEntity;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = BaseMetaTileEntity.class, remap = false)
@Restriction(require = @Condition(ModConst.ModId.GregTech))
public interface BaseMetaTileEntityAccessor {

    @Accessor("mWorkUpdate")
    void setWorkUpdate(boolean value);

}
