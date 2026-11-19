package cn.elytra.gtnh.cutcorners.mixinloader;

import com.gtnewhorizon.gtnhmixins.ILateMixinLoader;
import com.gtnewhorizon.gtnhmixins.LateMixin;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
@LateMixin
public class CCMixinLoader implements ILateMixinLoader {

    @Override
    public String getMixinConfig() {
        return "mixins.CutCorners.late.json";
    }

    @Override
    public @NotNull List<String> getMixins(Set<String> loadedMods) {
        return Collections.emptyList();
    }
}
