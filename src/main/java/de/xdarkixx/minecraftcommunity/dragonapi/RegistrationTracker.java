package de.xdarkixx.minecraftcommunity.dragonapi;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Loader-independent registration index used by the community port.
 * Minecraft/NeoForge registry objects can be attached later without coupling
 * this foundation to a particular registry API.
 */
public final class RegistrationTracker {
    private final Map<CommunityId, Object> entries = new LinkedHashMap<>();

    public synchronized <T> T register(CommunityId id, T value) {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(value, "value");
        if (entries.containsKey(id)) {
            throw new IllegalStateException("Duplicate registration: " + id);
        }
        entries.put(id, value);
        return value;
    }

    public synchronized boolean contains(CommunityId id) {
        return entries.containsKey(Objects.requireNonNull(id, "id"));
    }

    public synchronized Object get(CommunityId id) {
        return entries.get(Objects.requireNonNull(id, "id"));
    }

    public synchronized Map<CommunityId, Object> snapshot() {
        return Collections.unmodifiableMap(new LinkedHashMap<>(entries));
    }
}
