package net.wbmjunior.serverutilitycommands.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.wbmjunior.serverutilitycommands.util.IEntityDataSaver;

public class HomeCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("home").executes(HomeCommand::run));
    }

    public static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity actual_player = context.getSource().getPlayer();
        IEntityDataSaver player = (IEntityDataSaver) actual_player;
        assert actual_player != null;
        MinecraftServer server = actual_player.getServer();
        assert server != null;
        NbtCompound playerInfo = player.getPersistentData();
        if (!playerInfo.contains("homex")) {
            context.getSource().sendFeedback(() -> Text.translatable("command.home.failure"), false);
            return -1;
        }
        double x = playerInfo.getDouble("homex");
        double y = playerInfo.getDouble("homey");
        double z = playerInfo.getDouble("homez");
        String dimension = playerInfo.getString("homedimension");

        RegistryKey<World> dimensionKey = RegistryKey.of(
                RegistryKeys.WORLD,
                new Identifier(dimension)
        );

        ServerWorld world = server.getWorld(dimensionKey);

        actual_player.teleport(world, x, y, z, actual_player.getYaw(), actual_player.getPitch());

        return 1;
    }
}
