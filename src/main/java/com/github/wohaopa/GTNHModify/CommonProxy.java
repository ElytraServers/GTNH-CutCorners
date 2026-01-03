package com.github.wohaopa.GTNHModify;

import cn.elytra.gtnh.cutcorners.CutCorners;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
    }

    public void init(FMLInitializationEvent event) {
    }

    public void postInit(FMLPostInitializationEvent event) {
        CutCorners.postInit();
    }

    public void loadComplete(FMLLoadCompleteEvent event) {
        CutCorners.LOG.info("Cutting and initializing all recipes");
        CutCorners.loadComplete();
    }

    public void serverStarting(FMLServerStartingEvent event) {
    }
}
