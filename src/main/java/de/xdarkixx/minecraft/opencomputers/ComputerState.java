package de.xdarkixx.minecraft.opencomputers;

import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

/** Persistent state for a modern OpenComputers computer. */
public final class ComputerState {
    private boolean running;
    private long ticks;
    private String label = "";

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public long ticks() {
        return ticks;
    }

    public void tick() {
        ticks++;
    }

    public String label() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label == null ? "" : label.substring(0, Math.min(32, label.length()));
    }

    public void save(ValueOutput output) {
        output.putBoolean("Running", running);
        output.putLong("Ticks", ticks);
        output.putString("Label", label);
    }

    public void load(ValueInput input) {
        running = input.getBooleanOr("Running", false);
        ticks = input.getLongOr("Ticks", 0L);
        label = input.getStringOr("Label", "");
    }
}
