package com.github.wohaopa.GTNHModify.client.gui;

import cn.elytra.gtnh.cutcorners.config.NewConfig;
import com.github.wohaopa.GTNHModify.GTNHModifyMod;
import com.gtnewhorizon.gtnhlib.config.ConfigurationManager;
import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

import java.util.function.Supplier;

public class GTNHModifyGuiConfig extends GuiConfig {

    private static final Supplier<Configuration> CONF = () -> ConfigurationManager.getConfig(NewConfig.class);

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public GTNHModifyGuiConfig(GuiScreen guiScreen) {
        super(
            guiScreen,
            new ConfigElement(CONF.get().getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
            GTNHModifyMod.MOD_ID,
            true,
            true,
            GuiConfig.getAbridgedConfigPath(CONF.get().toString()));
    }
}
