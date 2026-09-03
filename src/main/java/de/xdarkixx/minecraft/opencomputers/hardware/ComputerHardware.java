package de.xdarkixx.minecraft.opencomputers.hardware;

import de.xdarkixx.minecraft.opencomputers.api.BasicComponent;
import de.xdarkixx.minecraft.opencomputers.api.ComponentContainer;
import java.util.Set;

/** Built-in hardware exposed by the base computer. */
public final class ComputerHardware {
    private final ComponentContainer components = new ComponentContainer();
    private int memoryBytes = 512 * 1024;
    private int cpuTier = 1;

    public ComputerHardware() {
        components.attach(new BasicComponent("computer", Set.of("start", "stop", "isRunning", "getDeviceInfo")));
        components.attach(new BasicComponent("cpu", Set.of("getTier", "getFrequency")));
        components.attach(new BasicComponent("memory", Set.of("getSize")));
        components.attach(new BasicComponent("filesystem", Set.of("list", "open", "read", "write", "close")));
    }

    public ComponentContainer components() { return components; }
    public int memoryBytes() { return memoryBytes; }
    public void setMemoryBytes(int value) { memoryBytes = Math.max(64 * 1024, Math.min(value, 64 * 1024 * 1024)); }
    public int cpuTier() { return cpuTier; }
    public void setCpuTier(int value) { cpuTier = Math.max(1, Math.min(value, 8)); }
}
