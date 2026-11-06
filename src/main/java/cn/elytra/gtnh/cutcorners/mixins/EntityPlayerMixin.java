package cn.elytra.gtnh.cutcorners.mixins;

import cn.elytra.gtnh.cutcorners.CutCorners;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.player.EntityPlayer;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityPlayer.class)
public class EntityPlayerMixin {

    @ModifyExpressionValue(method = "getBreakSpeed(Lnet/minecraft/block/Block;ZIIII)F", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/EntityPlayer;onGround:Z", opcode = Opcodes.GETFIELD))
    private boolean cc$isOnGroundCheck(boolean original) {
        // always return true as if we're on ground when enabled.
        return CutCorners.getStrategy().isNoFlyingDigPenalty() || original;
    }

}
