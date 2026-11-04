package cn.elytra.gtnh.cutcorners.internal;

import cn.elytra.gtnh.cutcorners.config.NewConfig;
import cn.elytra.gtnh.cutcorners.util.ChatHelper;
import com.gtnewhorizon.gtnhlib.eventbus.EventBusSubscriber;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.util.EnumChatFormatting;
import org.apache.commons.lang3.RandomUtils;

@EventBusSubscriber
public class CutCornersMOTD {

    private static final String[] WORDS = {"installed", "aboard", "entered", "joined"};

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!NewConfig.noAboardMessage) {
            try (var chat = ChatHelper.ofContext(event.player)) {
                chat.text("")
                    .appendText("GTNewHorizons: CutCorners").color(EnumChatFormatting.GOLD).bold(true)
                    .appendText(" ")
                    .appendText(WORDS[RandomUtils.nextInt(0, WORDS.length)]);
            }
        }
    }

}
