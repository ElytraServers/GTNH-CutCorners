package com.github.wohaopa.GTNHModify;

import cn.elytra.gtnh.cutcorners.CutCorners;
import cn.elytra.gtnh.cutcorners.config.CutCornersConfig;
import cn.elytra.gtnh.cutcorners.strate.impl.conf.NewConfigStrategy;
import cpw.mods.fml.common.event.*;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class CommonProxy {

    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry." (Remove if not needed)
    public void preInit(FMLPreInitializationEvent event) {
        CutCorners.setStrategy(new NewConfigStrategy(new CutCornersConfig(new Configuration(new File(event.getModConfigurationDirectory(), "CutCorners.cfg")))));
    }

    // load "Do your mod setup. Build whatever data structures you care about. Register recipes." (Remove if not needed)
    public void init(FMLInitializationEvent event) {}

    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {}

    public void loadComplete(FMLLoadCompleteEvent event) {
        CutCorners.LOG.info("Cutting and initializing all recipes");
        CutCorners.init();
    }

    // register server commands in this event handler (Remove if not needed)
    public void serverStarting(FMLServerStartingEvent event) {}
}
