package net.wbmjunior.serverutilitycommands.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.wbmjunior.serverutilitycommands.events.TpaManager;

public class TpacceptCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("tpaccept")
                .then(CommandManager.argument("player", EntityArgumentType.player())
                        .executes(TpacceptCommand::run)));
    }

    public static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity target = context.getSource().getPlayer();
        ServerPlayerEntity requester = EntityArgumentType.getPlayer(context, "player");

        TpaManager.TpaRequest request = TpaManager.getRequest(requester.getUuid());

        if (request == null) {
            context.getSource().sendMessage(Text.translatable("command.tpaccept.no_request", requester.getName()));
            return -1;
        }

        assert target != null;
        if (request.here) {
            target.teleport(requester.getServerWorld(), target.getX(), target.getY(), target.getZ(), target.getYaw(), target.getPitch());

        } else {
            requester.teleport(target.getServerWorld(), target.getX(), target.getY(), target.getZ(), target.getYaw(), target.getPitch());
        }
        TpaManager.removeRequest(requester.getUuid());
        context.getSource().sendMessage(Text.translatable("command.tpaccept.accepted", requester.getName()));
        requester.sendMessage(Text.translatable("command.tpa.accepted", target.getName()));

        return 1;
    }

}
