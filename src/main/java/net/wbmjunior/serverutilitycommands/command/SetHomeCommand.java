package net.wbmjunior.serverutilitycommands.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.wbmjunior.serverutilitycommands.util.IEntityDataSaver;

public class SetHomeCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("sethome").executes(SetHomeCommand::run));
    }

    public static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity actual_player = context.getSource().getPlayer();
        IEntityDataSaver player = (IEntityDataSaver) actual_player;
        assert actual_player != null;
        double x = actual_player.getX(), y = actual_player.getY(), z = actual_player.getZ();
        RegistryKey<World> dimension = context.getSource().getPlayer().getWorld().getRegistryKey();

        player.getPersistentData().putDouble("homex", x);
        player.getPersistentData().putDouble("homey", y);
        player.getPersistentData().putDouble("homez", z);
        player.getPersistentData().putString("homedimension", dimension.getValue().toString());

        context.getSource().sendFeedback(() -> Text.translatable("command.sethome.success", (int) x, (int) y, (int) z), false);

        return 1;
    }
}
