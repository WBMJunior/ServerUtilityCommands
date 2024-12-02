package net.wbmjunior.serverutilitycommands.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.wbmjunior.serverutilitycommands.events.TpaManager;

public class TpaCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("tpa")
                .then(CommandManager.argument("player", EntityArgumentType.player())
                        .executes(TpaCommand::run)));
    }

    public static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity requester = context.getSource().getPlayer();
        ServerPlayerEntity target = EntityArgumentType.getPlayer(context, "player");
        assert requester != null && target != null;

        if (requester.equals(target)) {
            context.getSource().sendFeedback(() -> Text.translatable("command.tpa.self_error"), false);
            return -1;
        }
        if (TpaManager.getRequest(requester.getUuid()) != null) {
            context.getSource().sendFeedback(() -> Text.translatable("command.tpa.existing_request"), false);
            return -1;
        }

        TpaManager.addRequest(requester, target);
        target.sendMessage(Text.translatable("command.tpa.request", requester.getName()));
        requester.sendMessage(Text.translatable("command.tpa.sent", target.getName()));
        MinecraftServer server = context.getSource().getServer();
        server.execute(() -> {
            try {
                Thread.sleep(10000);
                TpaManager.TpaRequest expired = TpaManager.getRequest(requester.getUuid());
                if (expired != null) {
                    TpaManager.removeRequest(requester.getUuid());
                    requester.sendMessage(Text.translatable("command.tpa.expired", target.getName()));
                    target.sendMessage(Text.translatable("command.tpa.expired_target", target.getName()));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        return 1;
    }

}
