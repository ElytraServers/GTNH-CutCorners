package cn.elytra.gtnh.cutcorners.mixins.late.botania;

import cn.elytra.gtnh.cutcorners.CutCorners;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import vazkii.botania.common.block.tile.mana.TileSpreader;

@Mixin(value = TileSpreader.class, remap = false)
public abstract class TileSpreaderMixin {

//    @Redirect(
//        method = "pingback",
//        at = @At(
//            value = "FIELD",
//            target = "Lvazkii/botania/common/block/tile/mana/TileSpreader;pingbackTicks:I",
//            opcode = Opcodes.PUTFIELD))
//    private void injectedPingback(TileSpreader tileSpreader, int pingbackTicks) {
//        tileSpreader.pingbackTicks = ThaumcraftHandler.handle(tileSpreader, pingbackTicks);
//    }

    @ModifyConstant(method = "pingback", constant = @Constant(intValue = 20 /* TICKS_ALLOWED_WITHOUT_PINGBACK */))
    private int gtnhcc$updatePingbackTicks(int value) {
        return CutCorners.getStrategy().getBotaniaSpreaderPingbackTicks(value);
    }

}
