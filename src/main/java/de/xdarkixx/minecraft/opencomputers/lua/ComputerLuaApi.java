package de.xdarkixx.minecraft.opencomputers.lua;

import de.xdarkixx.minecraft.opencomputers.runtime.ComputerRuntime;

/** computer.* compatibility facade exposed to the embedded Lua environment. */
public final class ComputerLuaApi {
    private final ComputerRuntime runtime;

    public ComputerLuaApi(ComputerRuntime runtime) {
        if (runtime == null) throw new IllegalArgumentException("runtime is required");
        this.runtime = runtime;
    }

    public boolean isRunning() { return runtime.isRunning(); }
    public void shutdown() { runtime.shutdown(); }
    public void reboot() { runtime.reboot(); }
    public long energy() { return runtime.energyStored(); }
    public long maxEnergy() { return runtime.energyCapacity(); }
    public void pushSignal(String name, Object... args) { runtime.queueSignal(name, args); }
}
