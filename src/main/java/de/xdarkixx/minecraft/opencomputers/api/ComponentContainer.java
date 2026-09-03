package de.xdarkixx.minecraft.opencomputers.api;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/** Deterministic component inventory with stable UUID addresses. */
public final class ComponentContainer {
    private final Map<UUID, Component> components = new LinkedHashMap<>();

    public UUID attach(Component component) {
        UUID address = UUID.randomUUID();
        components.put(address, component);
        return address;
    }

    public boolean detach(UUID address) {
        return components.remove(address) != null;
    }

    public Optional<Component> get(UUID address) {
        return Optional.ofNullable(components.get(address));
    }

    public Optional<UUID> findAddress(String type) {
        return components.entrySet().stream()
                .filter(entry -> entry.getValue().type().equals(type))
                .map(Map.Entry::getKey)
                .findFirst();
    }

    public Collection<Map.Entry<UUID, Component>> entries() {
        return components.entrySet();
    }

    public void clear() {
        components.clear();
    }
}
