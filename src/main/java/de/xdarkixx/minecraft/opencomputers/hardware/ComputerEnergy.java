package de.xdarkixx.minecraft.opencomputers.hardware;

/** Small deterministic energy buffer for computer hardware. */
public final class ComputerEnergy {
    private final long capacity;
    private long stored;

    public ComputerEnergy(long capacity) {
        this.capacity = Math.max(1L, capacity);
    }

    public long capacity() { return capacity; }
    public long stored() { return stored; }

    public long receive(long amount) {
        if (amount <= 0) return 0;
        long accepted = Math.min(amount, capacity - stored);
        stored += accepted;
        return accepted;
    }

    public long consume(long amount) {
        if (amount <= 0) return 0;
        long used = Math.min(amount, stored);
        stored -= used;
        return used;
    }
}
