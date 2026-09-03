package de.xdarkixx.minecraft.opencomputers.runtime;

import de.xdarkixx.minecraft.opencomputers.api.ComponentRegistry;
import de.xdarkixx.minecraft.opencomputers.hardware.ComputerCpu;
import de.xdarkixx.minecraft.opencomputers.hardware.ComputerEnergy;
import de.xdarkixx.minecraft.opencomputers.hardware.ComputerMemory;
import de.xdarkixx.minecraft.opencomputers.hardware.EepromMemory;
import de.xdarkixx.minecraft.opencomputers.hardware.HardDrive;

/** Aggregates server-owned computer capabilities; Lua receives adapters, never this object. */
public final class ComputerRuntime {
    private final ComponentRegistry components = new ComponentRegistry();
    private final ComputerEventQueue events = new ComputerEventQueue();
    private final ComputerCpu cpu = new ComputerCpu(1);
    private final ComputerMemory memory = new ComputerMemory(512 * 1024);
    private final ComputerEnergy energy = new ComputerEnergy(100_000);
    private final EepromMemory eeprom = new EepromMemory(4 * 1024);
    private final HardDrive hardDrive = new HardDrive(4 * 1024 * 1024);

    public ComponentRegistry components() { return components; }
    public de.xdarkixx.minecraft.opencomputers.hardware.VirtualFileSystem filesystem() { return hardDrive.filesystem(); }
    public ComputerEventQueue events() { return events; }
    public ComputerCpu cpu() { return cpu; }
    public ComputerMemory memory() { return memory; }
    public ComputerEnergy energy() { return energy; }
    public EepromMemory eeprom() { return eeprom; }
    public HardDrive hardDrive() { return hardDrive; }
}
