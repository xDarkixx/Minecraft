package de.xdarkixx.minecraft.opencomputers;

import net.minecraft.nbt.CompoundTag;

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

    public void save(CompoundTag tag) {
        tag.putBoolean("Running", running);
        tag.putLong("Ticks", ticks);
        tag.putString("Label", label);
    }

    public void load(CompoundTag tag) {
        running = tag.getBoolean("Running").orElse(false);
        ticks = tag.getLong("Ticks").orElse(0L);
        label = tag.getString("Label").orElse("");
    }
}
