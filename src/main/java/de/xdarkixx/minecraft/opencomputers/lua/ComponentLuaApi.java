package de.xdarkixx.minecraft.opencomputers.lua;

import de.xdarkixx.minecraft.opencomputers.ComponentRegistry;
import java.util.LinkedHashMap;
import java.util.Map;

/** Lua-facing component API compatible with the OpenComputers programming model. */
public final class ComponentLuaApi {
    private final ComponentRegistry registry;

    public ComponentLuaApi(ComponentRegistry registry) {
        this.registry = registry;
    }

    public Map<String, String> list(String filter) {
        Map<String, String> result = new LinkedHashMap<>();
        registry.list().forEach((address, component) -> {
            if (filter == null || filter.isEmpty() || component.type().contains(filter)) {
                result.put(address, component.type());
            }
        });
        return result;
    }

    public boolean isAvailable(String type) {
        return registry.primary(type).isPresent();
    }

    public String get(String abbreviatedAddress, String type) {
        return registry.resolve(abbreviatedAddress, type).orElse(null);
    }

    public ComponentRegistry.Component component(String address) {
        return registry.get(address).orElse(null);
    }
}
