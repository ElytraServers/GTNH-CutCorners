package cn.elytra.gtnh.cutcorners.core;

import java.util.function.Consumer;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

import cn.elytra.gtnh.cutcorners.api.CutCornersAPI;
import cn.elytra.gtnh.cutcorners.core.util.ClassNameUtil;
import cn.elytra.gtnh.cutcorners.util.ChatHelper;
import codechicken.lib.raytracer.RayTracer;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.interfaces.tileentity.RecipeMapWorkable;
import gregtech.api.metatileentity.implementations.MTEBasicMachine;
import gregtech.api.metatileentity.implementations.MTEMultiBlockBase;
import gregtech.api.recipe.RecipeMap;
import net.minecraft.block.Block;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public final class CutCornersCommand {

    public static void registerCommand(CommandDispatcher<ICommandSender> dispatcher) {
        // @formatter:off
        dispatcher.register(
            literal("cutcorners")
                .then(
                    literal("inspect").executes(wrap(CutCornersCommand::inspect))
                )
                .then(
                    literal("accelerate")
                        .then(literal("add").then(
                            required("cls", StringArgumentType.greedyString())
                                    .executes(wrap(CutCornersCommand::addAccelerate))))
                        .then(literal("remove").then(
                                required("cls", StringArgumentType.greedyString())
                                    .executes(wrap(CutCornersCommand::removeAccelerate))))
                )
        );
        // @formatter:on
    }

    private static void inspect(CommandContext<ICommandSender> c) {
        ICommandSender source = c.getSource();
        try (ChatHelper.CommandSenderScope chat = ChatHelper.ofContext(source)) {
            if (source instanceof EntityPlayer player) {
                World world = player.getEntityWorld();
                MovingObjectPosition trace = RayTracer.reTrace(world, player, 10.0);
                if (trace.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK) {
                    String what = trace.typeOfHit == MovingObjectPosition.MovingObjectType.MISS ? "nothing" : "entity";
                    chat.text("You're not targeting to a block? traced " + what + " instead.")
                        .color(EnumChatFormatting.RED);
                    return;
                }

                int bX = trace.blockX, bY = trace.blockY, bZ = trace.blockZ;
                Block block = world.getBlock(bX, bY, bZ);

                chat.text("=====[ BLOCK INSPECT ]=====");
                // block in world
                chat.text("Position: ")
                    .color(EnumChatFormatting.GRAY)
                    .appendText(String.format("%s/%s/%s", bX, bY, bZ))
                    .underlined(true)
                    .clickEventSuggestMessage(String.format("/tp @p %s %s %s", bX, bY, bZ));
                // block name
                chat.text("Block Name: ")
                    .color(EnumChatFormatting.GRAY)
                    .appendTranslatable(block.getUnlocalizedName() + ".name")
                    .color(EnumChatFormatting.WHITE);
                // block in item form
                try {
                    ItemStack item = block.getPickBlock(null, world, bX, bY, bZ, player);
                    if (item != null) {
                        chat.text("Item Name: ")
                            .color(EnumChatFormatting.GRAY)
                            .appendTranslatable(item.getUnlocalizedName() + ".name")
                            .color(EnumChatFormatting.WHITE);
                    }
                } catch (Exception ignored) {}
                // block in TE perspective
                TileEntity te = world.getTileEntity(bX, bY, bZ);
                if (te != null) {
                    if (te instanceof IGregTechTileEntity gte) {
                        IMetaTileEntity mte = gte.getMetaTileEntity();
                        chat.text("MTE Name: ")
                            .color(EnumChatFormatting.GRAY)
                            .appendText(mte.getMetaName())
                            .color(EnumChatFormatting.WHITE);
                        String canonicalName = mte.getClass().getCanonicalName();
                        chat.text("MTE Class: ")
                            .color(EnumChatFormatting.GRAY)
                            .appendText(ClassNameUtil.getClassNameWithShortenedPackage(canonicalName))
                            .color(EnumChatFormatting.WHITE)
                            .underlined(true)
                            .clickEventSuggestMessage(canonicalName);
                        if (mte instanceof RecipeMapWorkable workable) {
                            ChatHelper.Dsl dsl = chat.text("MTE Recipe Map: ").color(EnumChatFormatting.GRAY);
                            RecipeMap<?> currentMap = workable.getRecipeMap();
                            for (RecipeMap<?> recipeMap : workable.getAvailableRecipeMaps()) {
                                dsl.appendTranslatable(recipeMap.unlocalizedName)
                                    .color(
                                        recipeMap == currentMap ? EnumChatFormatting.GOLD : EnumChatFormatting.WHITE);
                            }
                        }
                        if (mte instanceof MTEBasicMachine || mte instanceof MTEMultiBlockBase) {
                            Class<? extends IMetaTileEntity> cls = mte.getClass();
                            boolean listed = CutCornersAPI.isMTEAccelerated(cls);
                            chat.text("Acceleration: ")
                                .color(EnumChatFormatting.GRAY)
                                .appendText(listed ? "Listed" : "Not listed")
                                .color(listed ? EnumChatFormatting.GREEN : EnumChatFormatting.BLUE)
                                .appendText(" ")
                                .appendText(!listed ? "(add)" : "(remove)")
                                .color(!listed ? EnumChatFormatting.GREEN : EnumChatFormatting.RED)
                                .underlined(true)
                                .clickEventSuggestMessage(
                                    !listed ? String.format("/cutcorners accelerate add %s", cls.getCanonicalName())
                                        : String.format("/cutcorners accelerate remove %s", cls.getCanonicalName()));
                        }
                    } else {
                        chat.text("TE Class: ")
                            .color(EnumChatFormatting.GRAY)
                            .appendText(te.getClass().getCanonicalName());
                    }
                }
            } else {
                chat.text("This command can only be used as a player.").color(EnumChatFormatting.RED);
            }
        }
    }

    private static void addAccelerate(CommandContext<ICommandSender> ctx) {
        ICommandSender source = ctx.getSource();
        try (ChatHelper.CommandSenderScope chat = ChatHelper.ofContext(source)) {
            if (!source.canCommandSenderUseCommand(4, "op")) {
                chat.text("Command not allowed").color(EnumChatFormatting.RED);
                return;
            }

            String cls = StringArgumentType.getString(ctx, "cls");
            try {
                Class<?> c = Class.forName(cls);
                CutCornersAPI.addMTEAccelerated(c);
                chat.text("OK!").color(EnumChatFormatting.GREEN);
            } catch (ClassNotFoundException e) {
                chat.text(String.format("Class %s wasn't found", cls)).color(EnumChatFormatting.RED);
            }
        }
    }

    private static void removeAccelerate(CommandContext<ICommandSender> ctx) {
        ICommandSender source = ctx.getSource();
        try (ChatHelper.CommandSenderScope chat = ChatHelper.ofContext(source)) {
            if (!source.canCommandSenderUseCommand(4, "op")) {
                chat.text("Command not allowed").color(EnumChatFormatting.RED);
                return;
            }

            String cls = StringArgumentType.getString(ctx, "cls");
            try {
                Class<?> c = Class.forName(cls);
                CutCornersAPI.removeMTEAccelerated(c);
                chat.text("OK!").color(EnumChatFormatting.GREEN);
            } catch (ClassNotFoundException e) {
                chat.text(String.format("Class %s wasn't found", cls)).color(EnumChatFormatting.RED);
            }
        }
    }

    private static Command<ICommandSender> wrap(Consumer<CommandContext<ICommandSender>> command) {
        return ctx -> {
            try {
                command.accept(ctx);
                return 1;
            } catch (Exception e) {
                // simply rethrow the CommandException.
                if (e instanceof CommandException) throw e;
                // or wrap the other type to CommandException with some message.
                throw new CommandException("Command ran into problem: %s", e.getMessage());
            }
        };
    }

    private static LiteralArgumentBuilder<ICommandSender> literal(String s) {
        return LiteralArgumentBuilder.literal(s);
    }

    private static <T> RequiredArgumentBuilder<ICommandSender, T> required(String name, ArgumentType<T> type) {
        return RequiredArgumentBuilder.argument(name, type);
    }

}
