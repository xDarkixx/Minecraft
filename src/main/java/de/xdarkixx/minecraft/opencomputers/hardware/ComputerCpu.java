package de.xdarkixx.minecraft.opencomputers.hardware;

/** Tiered CPU budget used to keep computer execution bounded per server tick. */
public final class ComputerCpu {
    private int tier;
    private int operationsPerTick;

    public ComputerCpu(int tier) {
        setTier(tier);
    }

    public int tier() { return tier; }
    public int operationsPerTick() { return operationsPerTick; }

    public void setTier(int value) {
        tier = Math.max(1, Math.min(8, value));
        operationsPerTick = 25_000 * tier;
    }
}
