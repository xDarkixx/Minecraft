package de.xdarkixx.minecraft.opencomputers.lua;

/**
 * Defines the security boundary for computer programs.
 * Filesystem, network, Minecraft and host-JVM access are deliberately not exposed here.
 */
public final class LuaSandbox {
    private final int instructionLimit;

    public LuaSandbox() {
        this(100_000);
    }

    public LuaSandbox(int instructionLimit) {
        if (instructionLimit < 1) throw new IllegalArgumentException("instructionLimit must be positive");
        this.instructionLimit = instructionLimit;
    }

    public int instructionLimit() {
        return instructionLimit;
    }
}
