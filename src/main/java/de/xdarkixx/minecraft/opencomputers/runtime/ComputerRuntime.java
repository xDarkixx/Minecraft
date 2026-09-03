package de.xdarkixx.minecraft.opencomputers.runtime;

import de.xdarkixx.minecraft.opencomputers.hardware.ComputerCpu;
import de.xdarkixx.minecraft.opencomputers.hardware.ComputerEnergy;
import de.xdarkixx.minecraft.opencomputers.hardware.ComputerMemory;
import de.xdarkixx.minecraft.opencomputers.hardware.VirtualFileSystem;
import de.xdarkixx.minecraft.opencomputers.api.ComponentRegistry;

/** Aggregates server-owned computer capabilities; Lua receives adapters, never this object. */
public final class ComputerRuntime {
    private final ComponentRegistry components = new ComponentRegistry();
    private final VirtualFileSystem filesystem = new VirtualFileSystem();
    private final ComputerEventQueue events = new ComputerEventQueue();
    private final ComputerCpu cpu = new ComputerCpu(1);
    private final ComputerMemory memory = new ComputerMemory(512 * 1024);
    private final ComputerEnergy energy = new ComputerEnergy(100_000);

    public ComponentRegistry components() { return components; }
    public VirtualFileSystem filesystem() { return filesystem; }
    public ComputerEventQueue events() { return events; }
    public ComputerCpu cpu() { return cpu; }
    public ComputerMemory memory() { return memory; }
    public ComputerEnergy energy() { return energy; }
}
