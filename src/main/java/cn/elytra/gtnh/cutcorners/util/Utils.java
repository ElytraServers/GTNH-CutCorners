package cn.elytra.gtnh.cutcorners.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.launchwrapper.Launch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class Utils {

    @SuppressWarnings("unchecked")
    public static <T> List<T> toList(Collection<T> collection) {
        if (collection instanceof List<?> list) {
            return (List<T>) list;
        }
        return new ArrayList<>(collection);
    }

    public static boolean isDev() {
        return (boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
    }

    public static boolean isOp(EntityPlayer player) {
        return player instanceof EntityPlayerMP && player.canCommandSenderUseCommand(2, "op");
    }
}
