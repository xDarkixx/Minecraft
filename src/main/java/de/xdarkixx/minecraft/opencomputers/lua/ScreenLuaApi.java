package de.xdarkixx.minecraft.opencomputers.lua;

import java.util.Arrays;

/** Server-side logical screen buffer. Rendering can bind this buffer to a Minecraft menu/screen. */
public final class ScreenLuaApi {
    private int width;
    private int height;
    private char[][] cells;

    public ScreenLuaApi(int width, int height) {
        resize(width, height);
    }

    public void resize(int width, int height) {
        if (width < 1 || height < 1 || width > 256 || height > 256) {
            throw new IllegalArgumentException("invalid screen size");
        }
        this.width = width;
        this.height = height;
        this.cells = new char[height][width];
        clear();
    }

    public int width() { return width; }
    public int height() { return height; }

    public void clear() {
        for (char[] row : cells) Arrays.fill(row, ' ');
    }

    public void set(int x, int y, String text) {
        if (text == null || y < 0 || y >= height) return;
        for (int i = 0; i < text.length() && x + i < width; i++) {
            if (x + i >= 0) cells[y][x + i] = text.charAt(i);
        }
    }

    public String line(int y) {
        if (y < 0 || y >= height) return "";
        return new String(cells[y]);
    }

    public String[] snapshot() {
        String[] result = new String[height];
        for (int y = 0; y < height; y++) result[y] = new String(cells[y]);
        return result;
    }
}
