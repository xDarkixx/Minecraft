package de.xdarkixx.minecraft.opencomputers.lua;

import de.xdarkixx.minecraft.opencomputers.hardware.VirtualFileSystem;
import java.util.Set;

/** Safe Lua-facing filesystem facade. It never exposes the host JVM filesystem. */
public final class FilesystemLuaApi {
    private final VirtualFileSystem filesystem;

    public FilesystemLuaApi(VirtualFileSystem filesystem) {
        if (filesystem == null) throw new IllegalArgumentException("filesystem is required");
        this.filesystem = filesystem;
    }

    public Set<String> list() { return filesystem.list(); }
    public byte[] read(String path) { return filesystem.read(path); }
    public void write(String path, byte[] data) { filesystem.write(path, data); }
    public boolean delete(String path) { return filesystem.delete(path); }
    public long usedSpace() { return filesystem.usedBytes(); }
    public long capacity() { return filesystem.capacityBytes(); }
    public long freeSpace() { return filesystem.freeBytes(); }
}
