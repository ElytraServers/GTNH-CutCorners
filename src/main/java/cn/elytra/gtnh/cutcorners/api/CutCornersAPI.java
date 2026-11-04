package cn.elytra.gtnh.cutcorners.api;

import cn.elytra.gtnh.cutcorners.config.NewConfig;
import com.gtnewhorizon.gtnhlib.config.ConfigurationManager;
import net.minecraft.launchwrapper.Launch;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class CutCornersAPI {
    private static final Class<?> CONFIG_CLASS = NewConfig.class;
    private static final Logger LOG = LogManager.getLogger();

    /// the reload id of runtime-reloadable fields.
    public static final String CONFIGURATION_RELOAD_RUNTIME = "runtime";

    static {
        if (Boolean.getBoolean("CC_API_LOADED_BY")) {
            new Exception("classload stacktrace").printStackTrace(System.err);
        }
        // initialize everything!
        LOG.info("CutCornersAPI!");
        NewConfig.init();
    }

    public static boolean isDev() {
        return (boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
    }

    public static void reloadConfig() {
        ConfigurationManager.reloadConfig(CONFIG_CLASS, CONFIGURATION_RELOAD_RUNTIME);
    }

    public static void saveConfig() {
        ConfigurationManager.save(CONFIG_CLASS);
    }

    /**
     * Runtime master switch for the bee work-cycle and crop-stick speedups. This state is intentionally not persisted:
     * it lives only for the current session, so toggling it never rewrites the config file. {@link #reloadConfig()}
     * restores the value from the config.
     */
    public static boolean isSpeedupEnabled() {
        return NewConfig.beeCropSpeedupEnabled;
    }

    public static boolean toggleSpeedup() {
        NewConfig.beeCropSpeedupEnabled = !NewConfig.beeCropSpeedupEnabled;
        return NewConfig.beeCropSpeedupEnabled;
    }

    public static boolean isMTEAccelerated(Class<?> c) {
        return NewConfig.MTE_ACCELERATION_CLASS.get().contains(c);
    }

    public static void addMTEAccelerated(Class<?> c) {
        NewConfig.mteAccelerationClass = ArrayUtils.add(NewConfig.mteAccelerationClass, c.getCanonicalName());
        saveConfig();
    }

    public static void removeMTEAccelerated(Class<?> c) {
        NewConfig.mteAccelerationClass = ArrayUtils.removeElements(NewConfig.mteAccelerationClass, c.getCanonicalName());
        saveConfig();
    }

    public static Class<?>[] getMTEAccelerated() {
        return NewConfig.MTE_ACCELERATION_CLASS.get().toArray(Class[]::new);
    }
}
