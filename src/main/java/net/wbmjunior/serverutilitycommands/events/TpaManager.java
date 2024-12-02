package net.wbmjunior.serverutilitycommands.events;

import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TpaManager {
    private static final Map<UUID, TpaRequest> pendingRequests = new HashMap<>();

    public static class TpaRequest {
        public final UUID requester;
        public final UUID target;
        public final long expiryTime;

        public TpaRequest(UUID requester, UUID target) {
            this.requester = requester;
            this.target = target;
            this.expiryTime = System.currentTimeMillis() + (10000);
        }

        public boolean isExpired() {
            return System.currentTimeMillis() > expiryTime;
        }
    }

    public static void addRequest(ServerPlayerEntity requester, ServerPlayerEntity target) {
        TpaRequest request = new TpaRequest(requester.getUuid(), target.getUuid());
        pendingRequests.put(requester.getUuid(), request);
    }

    public static TpaRequest getRequest(UUID requesterId) {
        return pendingRequests.get(requesterId);
    }

    public static void removeRequest(UUID requesterId) {
        pendingRequests.remove(requesterId);
    }

}
