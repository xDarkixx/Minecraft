package de.xdarkixx.minecraft.opencomputers.runtime;

import de.xdarkixx.minecraft.opencomputers.api.ComponentRegistry;
import de.xdarkixx.minecraft.opencomputers.hardware.ComputerCpu;
import de.xdarkixx.minecraft.opencomputers.hardware.ComputerEnergy;
import de.xdarkixx.minecraft.opencomputers.hardware.ComputerMemory;
import de.xdarkixx.minecraft.opencomputers.hardware.EepromMemory;
import de.xdarkixx.minecraft.opencomputers.hardware.HardDrive;
import java.util.concurrent.TimeUnit;

/** Aggregates server-owned computer capabilities; Lua receives adapters, never this object. */
public final class ComputerRuntime {
    private final ComponentRegistry components = new ComponentRegistry();
    private final ComputerEventQueue events = new ComputerEventQueue();
    private final ComputerCpu cpu = new ComputerCpu(1);
    private final ComputerMemory memory = new ComputerMemory(512 * 1024);
    private final ComputerEnergy energy = new ComputerEnergy(100_000);
    private final EepromMemory eeprom = new EepromMemory(4 * 1024);
    private final HardDrive hardDrive = new HardDrive(4 * 1024 * 1024);
    private boolean running = true;

    public ComponentRegistry components() { return components; }
    public de.xdarkixx.minecraft.opencomputers.hardware.VirtualFileSystem filesystem() { return hardDrive.filesystem(); }
    public ComputerEventQueue events() { return events; }
    public ComputerCpu cpu() { return cpu; }
    public ComputerMemory memory() { return memory; }
    public ComputerEnergy energy() { return energy; }
    public EepromMemory eeprom() { return eeprom; }
    public HardDrive hardDrive() { return hardDrive; }

    public boolean isRunning() { return running; }
    public void shutdown() { running = false; }
    public void reboot() { events.clear(); running = true; }
    public long energyStored() { return energy.stored(); }
    public long energyCapacity() { return energy.capacity(); }
    public void queueSignal(String name, Object... args) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("signal name is required");
        events.offer(new ComputerSignal(name, args));
    }
    public Object[] pollSignal(String filter) {
        ComputerSignal signal = events.poll();
        while (signal != null && filter != null && !filter.equals(signal.name())) signal = events.poll();
        if (signal == null) return null;
        Object[] result = new Object[signal.arguments().length + 1];
        result[0] = signal.name();
        System.arraycopy(signal.arguments(), 0, result, 1, signal.arguments().length);
        return result;
    }
    public Object[] pollSignal(String filter, long timeout, TimeUnit unit) throws InterruptedException {
        ComputerSignal signal = events.poll(timeout, unit);
        while (signal != null && filter != null && !filter.equals(signal.name())) signal = events.poll();
        if (signal == null) return null;
        Object[] result = new Object[signal.arguments().length + 1];
        result[0] = signal.name();
        System.arraycopy(signal.arguments(), 0, result, 1, signal.arguments().length);
        return result;
    }
}
