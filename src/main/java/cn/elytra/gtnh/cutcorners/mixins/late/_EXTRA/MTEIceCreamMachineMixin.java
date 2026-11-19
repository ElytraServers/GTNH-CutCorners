package cn.elytra.gtnh.cutcorners.mixins.late._EXTRA;

import cn.elytra.gtnh.cutcorners.mixinloader.ExtraMixinTester;
import cn.elytra.gtnh.cutcorners.mixins.late.gregtech.misc.BaseMetaTileEntityAccessor;
import cn.elytra.gtnh.cutcorners.util.ModConst;
import gregtech.api.enums.ParticleFX;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.MTEBasicMachine;
import gregtech.common.render.IMTERenderer;
import gregtech.common.tileentities.machines.basic.MTEIceCreamMachine;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/// once the Ice Cream Machine is broken, punching it will somehow fix it. 🤣
@Mixin(value = MTEIceCreamMachine.class, remap = false)
@Restriction(require = {
    @Condition(ModConst.ModId.GregTech),
    @Condition(type = Condition.Type.TESTER, tester = ExtraMixinTester.class)
})
public abstract class MTEIceCreamMachineMixin extends MTEBasicMachine implements IMTERenderer, IItemRenderer {

    @Shadow
    private boolean mBrokenToday;
    @Shadow
    private int mRepairItemIndex;
    @Shadow
    private int mCraftsToday;

    @Unique // client-only
    private long cc$shakeTime = -1;
    @Unique
    private static final byte SHAKE_EVENT = 2;
    @Unique
    private static final int ANIMATION_DURATION = 30; // 1.5s

    public MTEIceCreamMachineMixin(int aID, String aName, String aNameRegional, int aTier, int aAmperage, String aDescription, int aInputSlotCount, int aOutputSlotCount, ITexture... aOverlays) {
        super(aID, aName, aNameRegional, aTier, aAmperage, aDescription, aInputSlotCount, aOutputSlotCount, aOverlays);
    }

    @Override
    public void onLeftclick(IGregTechTileEntity bmte, EntityPlayer player) {
        super.onLeftclick(bmte, player);
        if (bmte.isServerSide()) {
            if (mBrokenToday) {
                // punch to fix!
                // 10% chance to fix the ice cream machine by punching it.
                int punchToFixChance = 10;
                if (bmte.getRandomNumber(100) < punchToFixChance) {
                    mBrokenToday = false;
                    mRepairItemIndex = -1;
                    // reset the craft count
                    mCraftsToday = 0;
                    // display ok particle
                    if (bmte.getWorld() instanceof WorldServer worldServer) {
                        worldServer.func_147487_a(ParticleFX.HUGE_EXPLOSION.toString(),
                            /* X */ bmte.getXCoord() + 0.5,
                            /* Y */ bmte.getYCoord() + 1.5,
                            /* Z */ bmte.getZCoord() + 0.5,
                            /* count */ 1,
                            /* X offset */ 0.0,
                            /* Y offset */ 0.0,
                            /* Z offset */ 0.0,
                            /* speed */ 0);
                    }
                    // force the ice-cream machine to work next tick.
                    ((BaseMetaTileEntityAccessor) bmte).setWorkUpdate(true);
                } else {
                    // dispatch the shaking event
                    bmte.sendBlockEvent(SHAKE_EVENT, (byte) 1);
                }
            }
        }
    }

    @Override
    public void receiveClientEvent(byte eventID, byte value) {
        super.receiveClientEvent(eventID, value);
        if (eventID == SHAKE_EVENT) {
            cc$shakeTime = Minecraft.getMinecraft().theWorld.getTotalWorldTime();
        }
    }

    @Inject(method = "renderTESR", at = @At(value = "INVOKE", target = "Lcom/gtnewhorizon/gtnhlib/client/renderer/vao/IVertexArrayObject;render()V"))
    private void cc$applyShakingRotation(double x, double y, double z, float timeSinceLastTick, CallbackInfo ci) {
        long shakeStart = cc$shakeTime;
        if (shakeStart >= 0) {
            float worldTime = (float) Minecraft.getMinecraft().theWorld.getTotalWorldTime() + timeSinceLastTick;
            float elapsed = worldTime - shakeStart;
            if (elapsed >= 0 && elapsed < ANIMATION_DURATION) {
                // during animation
                float progress = elapsed / ANIMATION_DURATION;
                float decay = 1.0F - progress;
                float freq = 1.2F;
                float amp = 12.0F * decay;
                float angle = amp * MathHelper.sin(elapsed * freq);

                GL11.glTranslatef(0F, -1.0F, 0F);
                GL11.glRotatef(angle, 1F, 0F, 0F);
                GL11.glTranslatef(0F, 1.0F, 0F);
            }
        }
    }

}
