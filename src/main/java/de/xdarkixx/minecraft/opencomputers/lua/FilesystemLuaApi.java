package de.xdarkixx.minecraft.opencomputers.lua;

import de.xdarkixx.minecraft.opencomputers.VirtualFileSystem;
import java.util.List;

/** Safe Lua-facing filesystem facade. It never exposes the host JVM filesystem. */
public final class FilesystemLuaApi {
    private final VirtualFileSystem filesystem;

    public FilesystemLuaApi(VirtualFileSystem filesystem) {
        this.filesystem = filesystem;
    }

    public String canonical(String path) { return filesystem.canonical(path); }
    public boolean exists(String path) { return filesystem.exists(path); }
    public boolean isDirectory(String path) { return filesystem.isDirectory(path); }
    public long size(String path) { return filesystem.size(path); }
    public List<String> list(String path) { return filesystem.list(path); }
    public byte[] read(String path) { return filesystem.read(path); }
    public boolean write(String path, byte[] data) { return filesystem.write(path, data); }
    public boolean delete(String path) { return filesystem.delete(path); }
    public boolean makeDirectory(String path) { return filesystem.makeDirectory(path); }
    public long usedSpace() { return filesystem.usedSpace(); }
    public long capacity() { return filesystem.capacity(); }
}
