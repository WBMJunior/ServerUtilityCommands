package net.wbmjunior.serverutilitycommands;

import net.fabricmc.api.ModInitializer;

import net.wbmjunior.serverutilitycommands.events.TpaManager;
import net.wbmjunior.serverutilitycommands.util.ModCommandRegister;
import net.wbmjunior.serverutilitycommands.util.ModEventsRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerUtilityCommands implements ModInitializer {
	public static final String MOD_ID = "serverutilitycommands";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModCommandRegister.registerCommands();
		ModEventsRegister.registerEvents();
		TpaManager.registerTickHandler();
	}
}