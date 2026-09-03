package de.xdarkixx.minecraft.opencomputers.api;

import java.util.Set;

/** Lightweight component implementation for built-in hardware. */
public record BasicComponent(String type, Set<String> methods) implements Component {
    public BasicComponent {
        if (type == null || type.isBlank()) throw new IllegalArgumentException("Component type is required");
        methods = Set.copyOf(methods == null ? Set.of() : methods);
    }
}
