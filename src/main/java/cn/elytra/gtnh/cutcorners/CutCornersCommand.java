package cn.elytra.gtnh.cutcorners;

import cn.elytra.gtnh.cutcorners.config.CutCornersConfig;
import cn.elytra.gtnh.cutcorners.util.ChatHelper;
import cn.elytra.gtnh.cutcorners.util.Utils;
import codechicken.lib.raytracer.RayTracer;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.interfaces.tileentity.RecipeMapWorkable;
import gregtech.api.metatileentity.implementations.MTEBasicMachine;
import gregtech.api.metatileentity.implementations.MTEMultiBlockBase;
import gregtech.api.recipe.RecipeMap;
import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicReference;

// non-public to make CCC always be CutCornersConfig
class CutCornersCommand extends CommandBase {

    public static final CutCornersCommand INSTANCE = new CutCornersCommand();

    private CutCornersCommand() {
    }

    @Override
    public String getCommandName() {
        return "cutcorners";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "command.cutcorners.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] argsArray) {
        ArrayList<String> args = new ArrayList<>(Arrays.asList(argsArray));
        String s = removeFirst(args);
        try (ChatHelper.CommandSenderScope sc = ChatHelper.ofContext(sender)) {
            if (s != null) {
                if (s.equalsIgnoreCase("getMachineInfo")) {
                    AtomicIntegerArray coordRef = new AtomicIntegerArray(3);
                    AtomicReference<World> worldRef = new AtomicReference<>(null);
                    // parse coordinates
                    parseWorldCoordinate(sender, args, coordRef, worldRef);
                    int x = coordRef.get(0), y = coordRef.get(1), z = coordRef.get(2);
                    World world = worldRef.get();
                    EntityPlayer player = sender instanceof EntityPlayer ep ? ep : null;
                    // title
                    sc.text("=== ")
                        .appendText("Machine Info")
                        .color(EnumChatFormatting.AQUA)
                        .bold(true)
                        .appendText(" ===");
                    // coordinate
                    sc.textFormatted("X/Y/Z: %s/%s/%s", x, y, z)
                        .color(EnumChatFormatting.YELLOW)
                        .underlined(true)
                        .clickEventSuggestMessage(String.format("/tp @p %s %s %s", x, y, z));
                    // block name
                    Block b = world.getBlock(x, y, z);
                    sc.text("[Block Name]").color(EnumChatFormatting.WHITE);
                    sc.translate(b.getUnlocalizedName() + ".name")
                        .color(EnumChatFormatting.GRAY);
                    // block item form
                    try {
                        ItemStack item;
                        // since we passed a null to the target, it's likely to throw.
                        item = b.getPickBlock(null, world, x, y, z, player);
                        if (item != null) {
                            sc.text("[Item Name]").color(EnumChatFormatting.WHITE);
                            sc.translate(item.getUnlocalizedName() + ".name")
                                .color(EnumChatFormatting.GRAY);
                        }
                    } catch (Throwable ignored) {
                    }
                    // tile
                    TileEntity te = world.getTileEntity(x, y, z);
                    if (te instanceof IGregTechTileEntity gte) {
                        IMetaTileEntity mte = gte.getMetaTileEntity();
                        do { // do-while(false) for break to quickly jump out of this section.
                            sc.text("[GregTech: MetaTileEntity]")
                                .color(EnumChatFormatting.WHITE)
                                .bold(true);
                            if (mte == null) {
                                sc.text("This block doesn't have a valid MetaTileEntity instance!")
                                    .color(EnumChatFormatting.RED);
                                break;
                            }
                            sc.text("[MTE Name]").color(EnumChatFormatting.WHITE);
                            sc.translate(mte.getMetaName()).color(EnumChatFormatting.GRAY);
                            Class<? extends IMetaTileEntity> mteClass = mte.getClass();
                            sc.text("[MTE Class]").color(EnumChatFormatting.WHITE);
                            sc.text(mteClass.getCanonicalName())
                                .color(EnumChatFormatting.GRAY)
                                .underlined(true)
                                .clickEventSuggestMessage(mteClass.getCanonicalName());
                            if (mte instanceof RecipeMapWorkable recipeMapWorkable) {
                                sc.text("[MTE Recipe Map Workable]").color(EnumChatFormatting.WHITE);
                                List<RecipeMap<?>> allRecipeMaps =
                                    Utils.toList(recipeMapWorkable.getAvailableRecipeMaps());
                                RecipeMap<?> currRecipeMap = recipeMapWorkable.getRecipeMap();
                                switch (allRecipeMaps.size()) {
                                    case 0 -> sc.text("No available recipe maps?!")
                                        .color(EnumChatFormatting.GRAY)
                                        .italic(true);
                                    case 1 -> sc.translate(allRecipeMaps.get(0).unlocalizedName)
                                        .color(EnumChatFormatting.GRAY)
                                        .bold(true);
                                    default -> {
                                        for (RecipeMap<?> recipeMap : allRecipeMaps) {
                                            sc.translate(recipeMap.unlocalizedName)
                                                .color(EnumChatFormatting.GRAY)
                                                .bold(recipeMap == currRecipeMap);
                                        }
                                    }
                                }
                            }
                            if (mte instanceof MTEBasicMachine || mte instanceof MTEMultiBlockBase) {
                                sc.text("[R.M. Acceleration]").color(EnumChatFormatting.WHITE);
                                if (CutCornersConfig.RunMachineAcceleration.containsClass(mteClass)) {
                                    sc.text("Listed").color(EnumChatFormatting.GREEN);
                                } else {
                                    sc.text("Not listed. ")
                                        .color(EnumChatFormatting.RED)
                                        .appendText("Add")
                                        .color(EnumChatFormatting.YELLOW)
                                        .bold(true)
                                        .underlined(true)
                                        .clickEventSuggestMessage(
                                            String.format("/cutcorners runMachineApplied %s", mteClass.getCanonicalName()));
                                }
                            }
                        } while (false);
                    }
                } else if (s.equalsIgnoreCase("runMachineApplied")) {
                    String cfqn = removeFirst(args);
                    if (cfqn == null) {
                        sc.text("[Currently]").color(EnumChatFormatting.WHITE).bold(true);
                        for (Class<?> c : CutCornersConfig.instance.getMaxProgressTimeRunMachineClasses()) {
                            sc.textFormatted("- %s", c.getCanonicalName()).color(EnumChatFormatting.GRAY).italic(true);
                        }
                    } else {
                        // check first
                        Class<?> clazz;
                        try {
                            clazz = Class.forName(cfqn, false, Thread.currentThread().getContextClassLoader());
                        } catch (ClassNotFoundException e) {
                            throw new CommandException("Class %s is not found", cfqn);
                        }

                        CutCornersConfig.update($ -> CutCornersConfig.RunMachineAcceleration.addClass(clazz));
                        sc.text("Success!").color(EnumChatFormatting.GREEN);
                    }
                }
            } else {
                helpMessage(sender);
            }
        }
    }

    private void helpMessage(ICommandSender sender) {
        String[] messages = {"== CutCorners Commands ==", "/cutcorners getMachineInfo [<x> <y> <z> [dim]]",
            "/cutcorners runMachineApplied <classFullQualifiedName>"};

        for (String message : messages) {
            sender.addChatMessage(new ChatComponentText(message));
        }
    }

    @Nullable
    protected static String removeFirst(List<String> list) {
        return list.isEmpty() ? null : list.remove(0);
    }

    /// parse a world coordinate from either sender or args.
    protected static void parseWorldCoordinate(ICommandSender sender, List<String> args, AtomicIntegerArray coordinate,
                                               AtomicReference<World> world) {
        switch (args.size()) {
            case 0 -> { // no more coordinates, we do raytrace.
                if (sender instanceof EntityPlayer player) {
                    world.set(player.worldObj);
                    MovingObjectPosition mop = RayTracer.reTrace(world.get(), player);
                    if (mop.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK) {
                        sender.addChatMessage(new ChatComponentText("Not a valid block target"));
                        return;
                    }
                    coordinate.set(0, mop.blockX);
                    coordinate.set(1, mop.blockY);
                    coordinate.set(2, mop.blockZ);
                } else {
                    throw new CommandException("You must be a player to use raytrace");
                }
            }
            case 1 -> {
                sender.addChatMessage(new ChatComponentText("Missing y and z"));
            }
            case 2 -> {
                sender.addChatMessage(new ChatComponentText("Missing z"));
            }
            default -> { // more than or equals to 3
                coordinate.set(0, parseInt(sender, removeFirst(args)));
                coordinate.set(1, parseInt(sender, removeFirst(args)));
                coordinate.set(2, parseInt(sender, removeFirst(args)));
                if (!args.isEmpty()) { // optional dim argument
                    int dim = parseInt(sender, removeFirst(args));
                    world.set(MinecraftServer.getServer().worldServerForDimension(dim));
                } else if (sender instanceof EntityPlayer player) {
                    world.set(player.worldObj);
                } else {
                    throw new CommandException(
                        "Unable to determine the dimension. You must either be a player or provide [dim] argument");
                }
            }
        }
    }
}
