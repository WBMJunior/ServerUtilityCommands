package net.wbmjunior.serverutilitycommands.events;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TpaManager {
    private static final Map<UUID, TpaRequest> pendingRequests = new HashMap<>();
    private static int ticks = 0;

    public static class TpaRequest {
        public final UUID requester;
        public final UUID target;
        public final long expiryTime;
        public final boolean here;

        public TpaRequest(UUID requester, UUID target, boolean here) {
            this.requester = requester;
            this.target = target;
            this.here = here;
            // ticks per second * seconds = ticks
            final int TIMEOUT = 20 * 10;
            this.expiryTime = TpaManager.getCurrentTicks() + TIMEOUT;
        }
    }

    public static void registerTickHandler() {
        ServerTickEvents.START_SERVER_TICK.register(server -> {
            ticks++;
            for (Map.Entry<UUID, TpaRequest> entry : pendingRequests.entrySet()) {
                TpaRequest request = entry.getValue();
                if (request.expiryTime < ticks) {
                    ServerPlayerEntity requester = server.getPlayerManager().getPlayer(request.requester);
                    ServerPlayerEntity target = server.getPlayerManager().getPlayer(request.target);
                    assert requester != null && target != null;
                    requester.sendMessage(Text.translatable("command.tpa.expired", target.getName()));
                    target.sendMessage(Text.translatable("command.tpa.expired_target", requester.getName()));
                    TpaManager.removeRequest(entry.getKey());
                }
            }
        });
    }

    public static int getCurrentTicks () {
        return ticks;
    }

    public static void addRequest(ServerPlayerEntity requester, ServerPlayerEntity target, boolean here) {
        TpaRequest request = new TpaRequest(requester.getUuid(), target.getUuid(), here);
        pendingRequests.put(requester.getUuid(), request);
    }

    public static TpaRequest getRequest(UUID requesterId) {
        return pendingRequests.get(requesterId);
    }

    public static void removeRequest(UUID requesterId) {
        pendingRequests.remove(requesterId);
    }

}
