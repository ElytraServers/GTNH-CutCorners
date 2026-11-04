package cn.elytra.gtnh.cutcorners.network;

import cn.elytra.gtnh.cutcorners.api.CutCornersAPI;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;

/**
 * Client -&gt; server channel for the runtime speedup switch. The affected mixins run on the logical server, so a key
 * press has to be forwarded; flipping the client's own copy of the config field would have no effect.
 */
public final class CutCornersNetwork {

    private static final String CHANNEL_NAME = "CutCorners";

    public static final SimpleNetworkWrapper CHANNEL = NetworkRegistry.INSTANCE.newSimpleChannel(CHANNEL_NAME);

    private CutCornersNetwork() {}

    public static void init() {
        CHANNEL.registerMessage(ToggleHandler.class, ToggleMessage.class, 0, Side.SERVER);
    }

    public static void sendToggle() {
        CHANNEL.sendToServer(new ToggleMessage());
    }

    /** Empty payload: the server owns the state and simply flips it. */
    public static final class ToggleMessage implements IMessage {

        public ToggleMessage() {}

        @Override
        public void fromBytes(ByteBuf buf) {}

        @Override
        public void toBytes(ByteBuf buf) {}
    }

    public static final class ToggleHandler implements IMessageHandler<ToggleMessage, IMessage> {

        @Override
        public IMessage onMessage(ToggleMessage message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().playerEntity;
            if (player == null) return null;

            if (!canToggle(player)) {
                player.addChatMessage(new ChatComponentTranslation("cutcorners.speedup.denied"));
                return null;
            }

            // The toggle only writes a volatile boolean, so doing it on the network thread is safe; the chat packet
            // is delivered through the Netty channel, which is thread-safe as well.
            boolean enabled = CutCornersAPI.toggleSpeedup();
            player.addChatMessage(
                new ChatComponentTranslation(enabled ? "cutcorners.speedup.enabled" : "cutcorners.speedup.disabled"));
            return null;
        }

        private static boolean canToggle(EntityPlayerMP player) {
            MinecraftServer server = MinecraftServer.getServer();
            if (server != null && server.isSinglePlayer()) return true;
            return player.canCommandSenderUseCommand(2, "cutcorners");
        }
    }
}
