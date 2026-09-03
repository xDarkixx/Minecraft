package de.xdarkixx.minecraft.opencomputers.lua;

import de.xdarkixx.minecraft.opencomputers.ComputerRuntime;

/** Event/signal facade used by event.pull in OpenOS-compatible Lua programs. */
public final class EventLuaApi {
    private final ComputerRuntime runtime;

    public EventLuaApi(ComputerRuntime runtime) {
        this.runtime = runtime;
    }

    public Object[] pull() {
        return runtime.pollSignal(null);
    }

    public Object[] pull(String filter) {
        return runtime.pollSignal(filter);
    }

    public void push(String name, Object... args) {
        runtime.queueSignal(name, args);
    }
}
