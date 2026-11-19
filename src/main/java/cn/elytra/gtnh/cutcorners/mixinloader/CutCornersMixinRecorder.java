package cn.elytra.gtnh.cutcorners.mixinloader;

import java.util.ArrayList;
import java.util.List;

import cn.elytra.gtnh.cutcorners.api.CutCornersAPI;
import cn.elytra.gtnh.cutcorners.core.util.ClassNameUtil;
import cn.elytra.gtnh.cutcorners.util.ChatHelper;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public final class CutCornersMixinRecorder {

    private CutCornersMixinRecorder() {}

    private record NotApplied(String mixinClass, String reason) {

        private static final String CC_MIXIN_PACKAGE = "cn.elytra.gtnh.cutcorners.mixins.";
        private static final String CC_DEV_MIXIN_PACKAGE = "cn.elytra.gtnh.cutcorners.mixins.late._DEV.";

        public boolean isDevMixin() {
            return ClassNameUtil.isClassInPackage(mixinClass, CC_DEV_MIXIN_PACKAGE);
        }

        public String shortenedMixinClass() {
            return shortenedMixinClass(CC_MIXIN_PACKAGE);
        }

        /// - `a111.b222.c333.D444` + `a111.b222.` => `a.b.c333.D444`
        public String shortenedMixinClass(String commonPackage) {
            return ClassNameUtil.getClassNameWithShortenedCommonPackage(mixinClass, commonPackage);
        }
    }

    // only exists before build
    private static List<NotApplied> notApplied = new ArrayList<>();
    // only exists after build
    private static List<IChatComponent> messages;

    static void notApplied(String mixinClass, String reason) {
        notApplied.add(new NotApplied(mixinClass, reason));
    }

    public static List<IChatComponent> build() {
        if (messages != null) return messages;
        // build messages
        List<IChatComponent> messages = new ArrayList<>();
        for (NotApplied nar : notApplied) {
            // skip dev-only mixin warnings.
            if (!CutCornersAPI.isDev() && nar.isDevMixin()) continue;

            ChatHelper.Dsl dsl = ChatHelper
                .translate(
                    "%s Mixin %s is not applied because %s",
                    ChatHelper.text("[CutCorners]").color(EnumChatFormatting.GOLD).bold(true),
                    ChatHelper.text(nar.shortenedMixinClass())
                        .color(EnumChatFormatting.BLUE)
                        .clickEventSendMessage(nar.mixinClass()),
                    ChatHelper.text(nar.reason()).color(EnumChatFormatting.GRAY).underlined(true))
                .color(EnumChatFormatting.RED);
            messages.add(dsl.build());
        }
        // clean up
        notApplied = null;
        CutCornersMixinRecorder.messages = messages;
        return messages;
    }

}
