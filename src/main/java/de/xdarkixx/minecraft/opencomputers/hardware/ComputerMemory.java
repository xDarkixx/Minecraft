package de.xdarkixx.minecraft.opencomputers.hardware;

/** Bounded RAM model for a computer tier. */
public final class ComputerMemory {
    public static final int MIN_BYTES = 64 * 1024;
    public static final int MAX_BYTES = 64 * 1024 * 1024;
    private int capacityBytes;

    public ComputerMemory(int capacityBytes) {
        setCapacityBytes(capacityBytes);
    }

    public int capacityBytes() { return capacityBytes; }

    public void setCapacityBytes(int value) {
        capacityBytes = Math.max(MIN_BYTES, Math.min(MAX_BYTES, value));
    }
}
