package com.github.wohaopa.GTNHModify.client.key;

import cn.elytra.gtnh.cutcorners.network.CutCornersNetwork;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

/** Registers the client-side key binding and forwards presses to the logical server. */
public class SpeedupKeyHandler {

    public static final String CATEGORY = "key.categories.cutcorners";

    /** Comma by default; GTNH has a lot of mods, so rebind in Options -> Controls if it clashes. */
    public static final KeyBinding TOGGLE_SPEEDUP = new KeyBinding(
        "key.cutcorners.toggleSpeedup",
        Keyboard.KEY_COMMA,
        CATEGORY);

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (!TOGGLE_SPEEDUP.isPressed()) return;

        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null || mc.currentScreen != null) return;

        CutCornersNetwork.sendToggle();
    }
}
