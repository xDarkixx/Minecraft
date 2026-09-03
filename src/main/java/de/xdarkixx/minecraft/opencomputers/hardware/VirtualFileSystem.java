package de.xdarkixx.minecraft.opencomputers.hardware;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/** Bounded virtual filesystem; paths and bytes are kept entirely in memory. */
public final class VirtualFileSystem {
    private static final int DEFAULT_CAPACITY = 64 * 1024 * 1024;
    private final Map<String, byte[]> files = new LinkedHashMap<>();
    private final int capacityBytes;
    private int usedBytes;

    public VirtualFileSystem() { this(DEFAULT_CAPACITY); }

    public VirtualFileSystem(int capacityBytes) {
        if (capacityBytes < 4 * 1024 || capacityBytes > DEFAULT_CAPACITY) {
            throw new IllegalArgumentException("filesystem capacity out of bounds");
        }
        this.capacityBytes = capacityBytes;
    }

    public int capacityBytes() { return capacityBytes; }
    public int usedBytes() { return usedBytes; }
    public int freeBytes() { return capacityBytes - usedBytes; }
    public Set<String> list() { return Set.copyOf(files.keySet()); }

    public byte[] read(String path) {
        byte[] data = files.get(normalize(path));
        return data == null ? null : data.clone();
    }

    public void write(String path, byte[] data) {
        if (data == null) throw new IllegalArgumentException("data is required");
        String normalized = normalize(path);
        byte[] copy = data.clone();
        byte[] old = files.get(normalized);
        int oldSize = old == null ? 0 : old.length;
        long next = (long) usedBytes - oldSize + copy.length;
        if (next > capacityBytes) throw new IllegalStateException("filesystem is full");
        files.put(normalized, copy);
        usedBytes = (int) next;
    }

    public boolean delete(String path) {
        byte[] removed = files.remove(normalize(path));
        if (removed == null) return false;
        usedBytes -= removed.length;
        return true;
    }

    /** Compact, host-independent persistence format: path=base64(data), one entry per line. */
    public String serialize() {
        StringBuilder out = new StringBuilder();
        for (Map.Entry<String, byte[]> entry : files.entrySet()) {
            if (out.length() > 0) out.append('\n');
            out.append(Base64.getUrlEncoder().withoutPadding().encodeToString(entry.getKey().getBytes(StandardCharsets.UTF_8)));
            out.append('=');
            out.append(Base64.getUrlEncoder().withoutPadding().encodeToString(entry.getValue()));
        }
        return out.toString();
    }

    public void deserialize(String serialized) {
        files.clear();
        usedBytes = 0;
        if (serialized == null || serialized.isEmpty()) return;
        for (String line : serialized.split("\\n", -1)) {
            int separator = line.indexOf('=');
            if (separator <= 0) continue;
            String path = new String(Base64.getUrlDecoder().decode(line.substring(0, separator)), StandardCharsets.UTF_8);
            byte[] data = Base64.getUrlDecoder().decode(line.substring(separator + 1));
            write(path, data);
        }
    }

    private static String normalize(String path) {
        if (path == null || path.isBlank()) throw new IllegalArgumentException("path is required");
        String normalized = path.replace('\\', '/');
        while (normalized.startsWith("/")) normalized = normalized.substring(1);
        if (normalized.isBlank() || normalized.contains("../") || normalized.equals("..") || normalized.startsWith("../")) {
            throw new IllegalArgumentException("invalid path");
        }
        return normalized;
    }
}
