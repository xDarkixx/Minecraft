package de.xdarkixx.minecraft.opencomputers.hardware;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/** Small persistent-in-memory filesystem abstraction used by the computer runtime. */
public final class VirtualFileSystem {
    private final Map<String, byte[]> files = new LinkedHashMap<>();

    public Set<String> list() {
        return Set.copyOf(files.keySet());
    }

    public byte[] read(String path) {
        byte[] data = files.get(normalize(path));
        return data == null ? null : data.clone();
    }

    public void write(String path, byte[] data) {
        if (data == null) throw new IllegalArgumentException("data is required");
        files.put(normalize(path), data.clone());
    }

    public boolean delete(String path) {
        return files.remove(normalize(path)) != null;
    }

    private static String normalize(String path) {
        if (path == null || path.isBlank()) throw new IllegalArgumentException("path is required");
        String normalized = path.replace('\\', '/');
        while (normalized.startsWith("/")) normalized = normalized.substring(1);
        if (normalized.isBlank() || normalized.contains("../") || normalized.equals("..")) {
            throw new IllegalArgumentException("invalid path");
        }
        return normalized;
    }
}
