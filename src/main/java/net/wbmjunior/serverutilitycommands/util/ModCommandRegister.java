package net.wbmjunior.serverutilitycommands.util;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.wbmjunior.serverutilitycommands.command.HomeCommand;
import net.wbmjunior.serverutilitycommands.command.SetHomeCommand;

public class ModCommandRegister {
    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(SetHomeCommand::register);
        CommandRegistrationCallback.EVENT.register(HomeCommand::register);
    }
}
