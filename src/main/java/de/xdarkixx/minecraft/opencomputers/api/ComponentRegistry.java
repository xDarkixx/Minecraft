package de.xdarkixx.minecraft.opencomputers.api;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/** Server-side component registry with deterministic iteration order. */
public final class ComponentRegistry {
    private final Map<ComponentAddress, Component> components = new LinkedHashMap<>();

    public ComponentAddress attach(Component component) {
        if (component == null) throw new IllegalArgumentException("component is required");
        ComponentAddress address = new ComponentAddress(java.util.UUID.randomUUID());
        components.put(address, component);
        return address;
    }

    public boolean detach(ComponentAddress address) {
        return components.remove(address) != null;
    }

    public Optional<Component> get(ComponentAddress address) {
        return Optional.ofNullable(components.get(address));
    }

    public Optional<ComponentAddress> find(String type) {
        return components.entrySet().stream()
                .filter(e -> e.getValue().type().equals(type))
                .map(Map.Entry::getKey)
                .findFirst();
    }

    public Map<ComponentAddress, Component> snapshot() {
        return Map.copyOf(components);
    }
}
