package cn.elytra.gtnh.cutcorners.mixinloader;

import com.google.common.collect.Lists;
import com.gtnewhorizon.gtnhmixins.ILateMixinLoader;
import com.gtnewhorizon.gtnhmixins.LateMixin;

import java.util.List;
import java.util.Set;

@LateMixin
public class CCMixinLoader implements ILateMixinLoader {

    @Override
    public String getMixinConfig() {
        return "mixins.GTNHModify_CutCorners.late.json";
    }

    @Override
    public List<String> getMixins(Set<String> loadedMods) {
        // read the mod init states
        Mods.init(loadedMods);

        var mixins = Lists.<String>newArrayList();

        if (Mods.GregTech.isLoaded()) {
            // GregTech
            mixins.add("gregtech.GT_MetaTileEntity_ScannerMixin");
            mixins.add("gregtech.GT_MetaTileEntity_MinerMixin");
            mixins.add("gregtech.GT_MetaTileEntity_MultiFurnaceMixin");
            mixins.add("gregtech.GT_MetaTileEntity_DrillerBaseMixin");
            mixins.add("gregtech.EyeOfHarmonyRecipeAccessor");
            mixins.add("gregtech.EyeOfHarmonyFrontendFixMixin");
        }
        if (Mods.Thaumcraft.isLoaded()) {
            mixins.add("thaumcraft.TileAlchemyFurnaceMixin");
            mixins.add("thaumcraft.TileNodeMixin");
        }
        if (Mods.GtnhIntergalactic.isLoaded()) {
            mixins.add("gtnhintergalactic.TileEntityModuleMinerMixin");
        }
        if (Mods.Botania.isLoaded()) {
            mixins.add("botania.TileSpreaderMixin");
        }
        if (Mods.Railcraft.isLoaded()) {
            mixins.add("railcraft.CokeOvenRecipeAccessor");
            mixins.add("railcraft.BlastFurnaceRecipeAccessor");
        }

        return mixins;
    }
}
