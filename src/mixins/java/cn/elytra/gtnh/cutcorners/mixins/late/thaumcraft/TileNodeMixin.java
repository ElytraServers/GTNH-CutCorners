package cn.elytra.gtnh.cutcorners.mixins.late.thaumcraft;

import cn.elytra.gtnh.cutcorners.CutCorners;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import thaumcraft.common.tiles.TileNode;

@Mixin(value = TileNode.class, remap = false)
public abstract class TileNodeMixin {

    @Shadow
    int regeneration;

    @Redirect(
        method = "handleRecharge",
        at = @At(
            value = "FIELD",
            target = "Lthaumcraft/common/tiles/TileNode;regeneration:I",
            opcode = Opcodes.PUTFIELD))
    private void injectedHandleRecharge(TileNode tileNode, int regeneration) {
        // this.regeneration = ThaumcraftHandler.handle(tileNode, regeneration);
        this.regeneration = CutCorners.getStrategy().getThaumcraftNodeRegenerationTime(regeneration);
    }
}
