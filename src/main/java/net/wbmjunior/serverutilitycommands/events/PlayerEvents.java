package net.wbmjunior.serverutilitycommands.events;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.wbmjunior.serverutilitycommands.util.IEntityDataSaver;

public class PlayerEvents implements ServerPlayerEvents.CopyFrom {

    @Override
    public void copyFromPlayer(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
        IEntityDataSaver original = ((IEntityDataSaver) oldPlayer);
        IEntityDataSaver player = ((IEntityDataSaver) newPlayer);

        player.getPersistentData().putDouble("homex", original.getPersistentData().getDouble("homex"));
        player.getPersistentData().putDouble("homey", original.getPersistentData().getDouble("homey"));
        player.getPersistentData().putDouble("homez", original.getPersistentData().getDouble("homez"));
        player.getPersistentData().putDouble("homedimension", original.getPersistentData().getDouble("homedimension"));
    }
}
