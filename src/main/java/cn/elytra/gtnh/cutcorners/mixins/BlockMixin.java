package cn.elytra.gtnh.cutcorners.mixins;

import cn.elytra.gtnh.cutcorners.CutCorners;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Block.class)
public class BlockMixin {

    @ModifyReturnValue(method = "canHarvestBlock", at = @At("RETURN"), remap = false) // Forge method
    private boolean cc$harvestCheck(boolean original) {
        return CutCorners.getStrategy().canHarvestBlock(original);
    }

}
