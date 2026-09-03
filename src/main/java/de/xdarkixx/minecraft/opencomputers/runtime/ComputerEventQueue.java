package de.xdarkixx.minecraft.opencomputers.runtime;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/** Bounded event queue used by computer programs instead of host callbacks. */
public final class ComputerEventQueue {
    private static final int MAX_EVENTS = 256;
    private final ConcurrentLinkedQueue<ComputerSignal> queue = new ConcurrentLinkedQueue<>();

    public boolean offer(ComputerSignal signal) {
        if (queue.size() >= MAX_EVENTS) return false;
        return queue.offer(signal);
    }

    public ComputerSignal poll() { return queue.poll(); }

    public ComputerSignal poll(long timeout, TimeUnit unit) throws InterruptedException {
        long deadline = System.nanoTime() + unit.toNanos(Math.max(0L, timeout));
        ComputerSignal signal;
        while ((signal = queue.poll()) == null && System.nanoTime() < deadline) {
            Thread.onSpinWait();
        }
        return signal;
    }

    public int size() { return queue.size(); }
    public void clear() { queue.clear(); }
}
