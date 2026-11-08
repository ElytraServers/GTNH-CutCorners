package cn.elytra.gtnh.cutcorners.mixinloader;

import cn.elytra.gtnh.cutcorners.api.CutCornersAPI;
import cn.elytra.gtnh.cutcorners.util.ChatHelper;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

import java.util.ArrayList;
import java.util.List;

public final class CutCornersMixinRecorder {

    private CutCornersMixinRecorder() {
    }

    private record NotApplied(String mixinClass, String reason) {
        private static final String CC_MIXIN_PACKAGE = "cn.elytra.gtnh.cutcorners.mixins.";
        private static final String CC_DEV_MIXIN_PACKAGE = "cn.elytra.gtnh.cutcorners.mixins.late._DEV.";

        public boolean isDevMixin() {
            return mixinClass.startsWith(CC_DEV_MIXIN_PACKAGE);
        }

        public String shortenedMixinClass() {
            return shortenedMixinClass(CC_MIXIN_PACKAGE);
        }

        /// - `a111.b222.c333.D444` + `a111.b222.` => `a.b.c333.D444`
        public String shortenedMixinClass(String commonPackage) {
            if (mixinClass.startsWith(commonPackage)) {
                String shortenedPackage = shrinkStringBetweenDots(commonPackage);
                String detailedClassName = mixinClass.substring(commonPackage.length());
                return shortenedPackage + detailedClassName;
            }
            return mixinClass;
        }

        /// - `a111.b222.c333`  => `a.b.c`
        /// - `a111.b222.c333.` => `a.b.c.`
        private static String shrinkStringBetweenDots(String s) {
            if (s == null || s.isEmpty()) return "";

            StringBuilder sb = new StringBuilder();
            int start = 0;
            while (start < s.length()) {
                char ch = s.charAt(start);
                int end = s.indexOf('.', start);
                if (end == -1) { // no trailing dot
                    end = s.length();
                    sb.append(ch);
                } else {
                    sb.append(ch).append('.');
                }
                start = end + 1;
            }
            return sb.toString();
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

            ChatHelper.Dsl dsl = ChatHelper.translate("%s Mixin %s is not applied because %s",
                    ChatHelper.text("[CutCorners]")
                        .color(EnumChatFormatting.GOLD)
                        .bold(true),
                    ChatHelper.text(nar.shortenedMixinClass())
                        .color(EnumChatFormatting.BLUE)
                        .clickEventSendMessage(nar.mixinClass()),
                    ChatHelper.text(nar.reason())
                        .color(EnumChatFormatting.GRAY)
                        .underlined(true))
                .color(EnumChatFormatting.RED);
            messages.add(dsl.build());
        }
        // clean up
        notApplied = null;
        CutCornersMixinRecorder.messages = messages;
        return messages;
    }

}
