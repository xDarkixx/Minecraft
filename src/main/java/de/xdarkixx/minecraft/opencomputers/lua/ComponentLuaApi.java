package de.xdarkixx.minecraft.opencomputers.lua;

import de.xdarkixx.minecraft.opencomputers.api.Component;
import de.xdarkixx.minecraft.opencomputers.api.ComponentAddress;
import de.xdarkixx.minecraft.opencomputers.api.ComponentRegistry;
import java.util.LinkedHashMap;
import java.util.Map;

/** Lua-facing component API compatible with the OpenComputers programming model. */
public final class ComponentLuaApi {
    private final ComponentRegistry registry;

    public ComponentLuaApi(ComponentRegistry registry) {
        if (registry == null) throw new IllegalArgumentException("registry is required");
        this.registry = registry;
    }

    public Map<String, String> list(String filter) {
        Map<String, String> result = new LinkedHashMap<>();
        registry.snapshot().forEach((address, component) -> {
            if (filter == null || filter.isEmpty() || component.type().contains(filter)) {
                result.put(address.toString(), component.type());
            }
        });
        return result;
    }

    public boolean isAvailable(String type) {
        return registry.find(type).isPresent();
    }

    public String get(String abbreviatedAddress, String type) {
        if (abbreviatedAddress == null || abbreviatedAddress.isBlank()) {
            return registry.find(type).map(ComponentAddress::toString).orElse(null);
        }
        return registry.snapshot().entrySet().stream()
                .filter(e -> e.getKey().toString().startsWith(abbreviatedAddress))
                .filter(e -> type == null || type.isEmpty() || e.getValue().type().equals(type))
                .map(e -> e.getKey().toString())
                .findFirst().orElse(null);
    }

    public Component component(String address) {
        if (address == null) return null;
        try {
            return registry.get(new ComponentAddress(java.util.UUID.fromString(address))).orElse(null);
        } catch (IllegalArgumentException ignored) {
            return null;
        }
    }
}
