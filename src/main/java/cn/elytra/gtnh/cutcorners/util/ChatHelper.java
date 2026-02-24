package cn.elytra.gtnh.cutcorners.util;

import net.minecraft.command.ICommandSender;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import org.jetbrains.annotations.TestOnly;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@NullMarked
public final class ChatHelper {

    public static Dsl text(String text) {
        return new Dsl(new ChatComponentText(text));
    }

    public static Dsl translate(String key, @Nullable Object... args) {
        return new Dsl(new ChatComponentTranslation(key, args));
    }

    public static Dsl textFormatted(String format, @Nullable Object... args) {
        return text(String.format(format, args));
    }

    public static CommandSenderScope ofContext(ICommandSender sender) {
        return new CommandSenderScope(sender);
    }

    @SuppressWarnings("UnusedReturnValue") // expected unused return value because the scope will send them at the end.
    public static class Dsl {
        private final IChatComponent component;

        private Dsl(IChatComponent component) {
            this.component = component;
        }

        @TestOnly
        public IChatComponent test$peek() {
            return this.component;
        }

        public IChatComponent build() {
            return this.component;
        }

        public Dsl color(EnumChatFormatting color) {
            component.getChatStyle().setColor(color);
            return this;
        }

        public Dsl bold(boolean bold) {
            component.getChatStyle().setBold(bold);
            return this;
        }

        public Dsl italic(boolean italic) {
            component.getChatStyle().setItalic(italic);
            return this;
        }

        public Dsl underlined(boolean underlined) {
            component.getChatStyle().setUnderlined(underlined);
            return this;
        }

        public Dsl strikethrough(boolean strikethrough) {
            component.getChatStyle().setStrikethrough(strikethrough);
            return this;
        }

        public Dsl obfuscated(boolean obfuscated) {
            component.getChatStyle().setObfuscated(obfuscated);
            return this;
        }

        public Dsl clickEventSuggestMessage(String message) {
            component.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, message));
            return this;
        }

        public Dsl clickEventSendMessage(String message) {
            component.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, message));
            return this;
        }

        // append siblings

        private DslWithParent appendSibling(IChatComponent sibling) {
            component.appendSibling(sibling);
            return new DslWithParent(sibling, this);
        }

        public DslWithParent appendText(String text) {
            return appendSibling(new ChatComponentText(text));
        }

        public DslWithParent appendTranslatable(String key, @Nullable Object... args) {
            return appendSibling(new ChatComponentTranslation(key, args));
        }

        // post build helpers
        public void sendTo(ICommandSender sender) {
            sender.addChatMessage(build());
        }
    }

    public static class DslWithParent extends Dsl {
        private final Dsl parent;

        public DslWithParent(IChatComponent component, Dsl parent) {
            super(component);
            this.parent = parent;
        }

        public Dsl parent() {
            return this.parent;
        }

        @Override
        public IChatComponent build() {
            return parent.build();
        }

        @Override
        public DslWithParent appendText(String text) {
            return parent.appendText(text);
        }

        @Override
        public DslWithParent appendTranslatable(String key, @Nullable Object... args) {
            return parent.appendTranslatable(key, args);
        }

        public DslWithParent appendTextAsChild(String text) {
            return super.appendText(text);
        }

        public DslWithParent appendTranslatableAsChild(String key, @Nullable Object... args) {
            return super.appendTranslatable(key, args);
        }
    }

    public static final class CommandSenderScope implements AutoCloseable {
        private final ICommandSender sender;
        private final List<Dsl> messages = new ArrayList<>();

        public CommandSenderScope(ICommandSender sender) {
            this.sender = sender;
        }

        private Dsl addMessage(Dsl message) {
            messages.add(message);
            return message;
        }

        public Dsl text(String text) {
            return addMessage(new Dsl(new ChatComponentText(text)));
        }

        public Dsl translate(String key, @Nullable Object... args) {
            return addMessage(new Dsl(new ChatComponentTranslation(key, args)));
        }

        public Dsl textFormatted(String format, @Nullable Object... args) {
            return text(String.format(format, args));
        }

        @Override
        public void close() {
            messages.forEach(m -> m.sendTo(sender));
        }
    }

}
