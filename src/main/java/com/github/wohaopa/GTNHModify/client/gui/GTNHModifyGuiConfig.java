package com.github.wohaopa.GTNHModify.client.gui;

import cn.elytra.gtnh.cutcorners.config.Config;
import com.github.wohaopa.GTNHModify.GTNHModifyMod;
import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

public class GTNHModifyGuiConfig extends GuiConfig {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public GTNHModifyGuiConfig(GuiScreen guiScreen) {
        super(
            guiScreen,
            new ConfigElement(Config.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
            GTNHModifyMod.MOD_ID,
            true,
            false,
            GuiConfig.getAbridgedConfigPath(Config.config.toString()));
    }
}
