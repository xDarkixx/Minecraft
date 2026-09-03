package de.xdarkixx.minecraft.opencomputers.hardware;

/** Persistent virtual hard-drive model. Storage never escapes into the host filesystem. */
public final class HardDrive {
    private final int capacityBytes;
    private final VirtualFileSystem filesystem;

    public HardDrive(int capacityBytes) {
        if (capacityBytes < 4 * 1024 || capacityBytes > 64 * 1024 * 1024) {
            throw new IllegalArgumentException("hard-drive capacity out of bounds");
        }
        this.capacityBytes = capacityBytes;
        this.filesystem = new VirtualFileSystem(capacityBytes);
    }

    public int capacityBytes() {
        return capacityBytes;
    }

    public VirtualFileSystem filesystem() {
        return filesystem;
    }
}
