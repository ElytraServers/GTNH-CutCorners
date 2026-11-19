package cn.elytra.gtnh.cutcorners.mixinloader;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

public class ExtraMixinConfig {

    public static final Configuration CONF = new Configuration(new File("config/CutCorners-early.cfg"));

    public static final boolean ENABLE_ICE_CREAM_PUNCHING = CONF.getBoolean("ice-cream-punching", CATEGORY_GENERAL, true, "whether allow \"fix\" the problems of Ice Cream Machine by punching");

    static {
        CONF.save();
    }

}
