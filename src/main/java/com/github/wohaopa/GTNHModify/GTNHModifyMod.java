package com.github.wohaopa.GTNHModify;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
    modid = GTNHModifyMod.MOD_ID,
    version = Tags.VERSION,
    guiFactory = "com.github.wohaopa.GTNHModify.client.gui.GuiFactory",
    name = "GTNHModify",
    acceptedMinecraftVersions = "[1.7.10]",
    acceptableRemoteVersions = "*",
    dependencies = "after:gregtech;after:Thaumcraft;")
public class GTNHModifyMod {

    public static final String MOD_ID = "GTNHModify_CutCorners";
    public static final String OLD_MOD_ID = "GTNHModify";

    public static final Logger LOG = LogManager.getLogger(MOD_ID);

    @SidedProxy(
        clientSide = "com.github.wohaopa.GTNHModify.ClientProxy",
        serverSide = "com.github.wohaopa.GTNHModify.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        if(Loader.isModLoaded(OLD_MOD_ID)) {
            throw new IllegalStateException("Don't install both CutCorners and GTNHModify!");
        }

        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Mod.EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
        proxy.loadComplete(event);
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
    }
}
