package cn.elytra.gtnh.cutcorners.mixinloader;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.versioning.ComparableVersion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public enum Mods {

    GregTech("gregtech"),
    Thaumcraft("Thaumcraft"),
    GtnhIntergalactic("gtnhintergalactic"),
    Botania("Botania"),
    Railcraft("Railcraft"),
    ;

    private static final Logger LOG = LogManager.getLogger();

    public final String modid;

    private boolean isLoaded;

    @Nullable
    private ComparableVersion modVersion;

    Mods(String modid) {
        this.modid = modid;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    /**
     * Check if the mod is loaded and its version is equal to the specified version.
     *
     * @param version the version to check
     * @return {@code true} if the version is equal to the specified version, or the version is not obtained successfully.
     */
    public boolean isVersion(@NotNull ComparableVersion version) {
        return this.modVersion == null || this.modVersion.equals(version);
    }

    /**
     * Check if the version of the loaded mod is within the specified range.
     *
     * @param minimumVersionInclusive the minimum version (inclusive)
     * @param maximumVersionExclusive the maximum version (exclusive)
     * @return {@code true} if the version is within the specified range, or the version is not obtained successfully.
     */
    public boolean isInVersionRange(@NotNull ComparableVersion minimumVersionInclusive, @NotNull ComparableVersion maximumVersionExclusive) {
        return this.modVersion == null ||
            (minimumVersionInclusive.compareTo(this.modVersion) <= 0 && this.modVersion.compareTo(maximumVersionExclusive) < 0);
    }

    public static void init(Collection<String> loadedMods) {
        for(Mods value : values()) {
            if(loadedMods.contains(value.modid)) {
                value.isLoaded = true;

                // try to get the mod version.
                // this approach doesn't work for non-FML mods.
                try {
                    for(ModContainer modContainer : Loader.instance().getModList()) {
                        if(modContainer.getModId().equals(value.modid)) {
                            value.modVersion = new ComparableVersion(modContainer.getVersion());
                        }
                    }
                } catch(Exception e) {
                    LOG.warn("Unable to get mod version for mod {} due to an exception.", value.modid, e);
                }
            }
        }
    }
}
