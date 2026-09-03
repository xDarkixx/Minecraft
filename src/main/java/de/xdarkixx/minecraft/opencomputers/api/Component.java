package de.xdarkixx.minecraft.opencomputers.api;

import java.util.Set;

/** A device component exposed to a computer. */
public interface Component {
    String type();
    Set<String> methods();
}
