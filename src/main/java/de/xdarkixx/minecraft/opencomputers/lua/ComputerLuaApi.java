package de.xdarkixx.minecraft.opencomputers.lua;

import de.xdarkixx.minecraft.opencomputers.ComputerRuntime;

/** computer.* compatibility facade exposed to the embedded Lua environment. */
public final class ComputerLuaApi {
    private final ComputerRuntime runtime;

    public ComputerLuaApi(ComputerRuntime runtime) {
        this.runtime = runtime;
    }

    public boolean isRunning() { return runtime.isRunning(); }
    public void shutdown() { runtime.shutdown(); }
    public void reboot() { runtime.reboot(); }
    public long energy() { return runtime.energyStored(); }
    public long maxEnergy() { return runtime.energyCapacity(); }
    public void pushSignal(String name, Object... args) { runtime.queueSignal(name, args); }
}
