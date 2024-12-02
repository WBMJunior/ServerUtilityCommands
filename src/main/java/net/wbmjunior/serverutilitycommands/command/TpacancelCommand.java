package net.wbmjunior.serverutilitycommands.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.wbmjunior.serverutilitycommands.events.TpaManager;

public class TpacancelCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("tpacancel").executes(TpacancelCommand::run));
    }

    public static int run(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity requester = context.getSource().getPlayer();

        assert requester != null;
        TpaManager.TpaRequest request = TpaManager.getRequest(requester.getUuid());

        if (request == null) {
            context.getSource().sendMessage(Text.translatable("command.tpacancel.no_request", requester.getName()));
            return -1;
        }

        return 1;
    }

}
