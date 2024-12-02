package net.wbmjunior.serverutilitycommands.util;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.wbmjunior.serverutilitycommands.command.*;

public class ModCommandRegister {
    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(SetHomeCommand::register);
        CommandRegistrationCallback.EVENT.register(HomeCommand::register);
        CommandRegistrationCallback.EVENT.register(TpaCommand::register);
        CommandRegistrationCallback.EVENT.register(TpahereCommand::register);
        CommandRegistrationCallback.EVENT.register(TpacceptCommand::register);
        CommandRegistrationCallback.EVENT.register(TpacancelCommand::register);
    }
}
