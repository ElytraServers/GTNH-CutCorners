package com.github.wohaopa.GTNHModify;

import net.minecraftforge.client.ClientCommandHandler;

import com.github.wohaopa.GTNHModify.client.command.GTNHModifyCommand;
import com.github.wohaopa.GTNHModify.client.key.SpeedupKeyHandler;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;

public class ClientProxy extends CommonProxy {

    // Override CommonProxy methods here, if you want a different behaviour on the client (e.g. registering renders).
    // Don't forget to call the super methods as well.

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);

        ClientRegistry.registerKeyBinding(SpeedupKeyHandler.TOGGLE_SPEEDUP);
        FMLCommonHandler.instance().bus().register(new SpeedupKeyHandler());

        ClientCommandHandler.instance.registerCommand(new GTNHModifyCommand());
    }
}
