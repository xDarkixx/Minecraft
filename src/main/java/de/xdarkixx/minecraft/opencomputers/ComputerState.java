package de.xdarkixx.minecraft.opencomputers;

import de.xdarkixx.minecraft.opencomputers.api.Component;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.UUID;

/** Persistent, server-authoritative state and hardware bundle for an OC computer. */
public final class ComputerState {
    private boolean running;
    private long ticks;
    private String label = "";
    private final OCSystems.Energy energy = new OCSystems.Energy(100_000);
    private final OCSystems.Inventory inventory = new OCSystems.Inventory(16, 64);
    private final OCSystems.Network network = new OCSystems.Network();
    private final OCSystems.Keyboard keyboard = new OCSystems.Keyboard();
    private final OCSystems.Screen screen = new OCSystems.Screen(80, 25);
    private final OCSystems.Robot robot = new OCSystems.Robot();
    private final OCSystems.Storage storage = new OCSystems.Storage(4096, 4 * 1024 * 1024);
    private final OCSystems.Components components = new OCSystems.Components();

    public ComputerState() { components.attachDefaults(); }
    public boolean isRunning() { return running; }
    public void setRunning(boolean running) { this.running = running; }
    public long ticks() { return ticks; }
    public void tick() { if (running && energy.consume(10)) ticks++; else if (running) running = false; }
    public String label() { return label; }
    public void setLabel(String label) { this.label = label == null ? "" : label.substring(0, Math.min(32, label.length())); }
    public OCSystems.Energy energy() { return energy; }
    public OCSystems.Inventory inventory() { return inventory; }
    public OCSystems.Network network() { return network; }
    public OCSystems.Keyboard keyboard() { return keyboard; }
    public OCSystems.Screen screen() { return screen; }
    public OCSystems.Robot robot() { return robot; }
    public OCSystems.Storage storage() { return storage; }
    public OCSystems.Components components() { return components; }

    public String findComponent(String type) {
        return components.container().findAddress(type).map(UUID::toString).orElse(null);
    }

    public void save(ValueOutput output) {
        output.putBoolean("Running", running);
        output.putLong("Ticks", ticks);
        output.putString("Label", label);
        output.putLong("Energy", energy.stored());
        output.putString("Filesystem", storage.filesystem().serialize());
    }

    public void load(ValueInput input) {
        running = input.getBooleanOr("Running", false);
        ticks = input.getLongOr("Ticks", 0L);
        label = input.getStringOr("Label", "");
        long stored = input.getLongOr("Energy", energy.capacity());
        energy.extract(energy.stored());
        energy.insert(Math.max(0, Math.min(energy.capacity(), stored)));
        storage.filesystem().deserialize(input.getStringOr("Filesystem", ""));
    }
}
