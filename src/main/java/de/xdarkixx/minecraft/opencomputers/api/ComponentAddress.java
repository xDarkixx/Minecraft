package de.xdarkixx.minecraft.opencomputers.api;

import java.util.UUID;

/** Stable address assigned to a component for its lifetime. */
public record ComponentAddress(UUID value) {
    public ComponentAddress {
        if (value == null) throw new IllegalArgumentException("address is required");
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
