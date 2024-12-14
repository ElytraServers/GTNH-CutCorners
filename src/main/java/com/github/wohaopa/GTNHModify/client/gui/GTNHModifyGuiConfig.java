package com.github.wohaopa.GTNHModify.client.gui;

import cn.elytra.gtnh.cutcorners.config.CutCornersConfig;
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
            new ConfigElement(CutCornersConfig.instance.getConfig().getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
            GTNHModifyMod.MOD_ID,
            true,
            true,
            GuiConfig.getAbridgedConfigPath(CutCornersConfig.instance.getConfig().toString()));
    }
}
