package de.xdarkixx.minecraft.opencomputers.hardware;

import java.nio.charset.StandardCharsets;

/** Bounded EEPROM-style program storage owned by a computer item/component. */
public final class EepromMemory {
    public static final int MIN_BYTES = 256;
    public static final int MAX_BYTES = 64 * 1024;

    private final byte[] data;

    public EepromMemory(int capacity) {
        if (capacity < MIN_BYTES || capacity > MAX_BYTES) {
            throw new IllegalArgumentException("EEPROM capacity must be between " + MIN_BYTES + " and " + MAX_BYTES);
        }
        data = new byte[capacity];
    }

    public int capacity() {
        return data.length;
    }

    public byte[] read() {
        return data.clone();
    }

    public void write(byte[] value) {
        if (value == null || value.length > data.length) {
            throw new IllegalArgumentException("EEPROM program exceeds capacity");
        }
        java.util.Arrays.fill(data, (byte) 0);
        System.arraycopy(value, 0, data, 0, value.length);
    }

    public void writeText(String source) {
        if (source == null) throw new IllegalArgumentException("source is required");
        write(source.getBytes(StandardCharsets.UTF_8));
    }

    public String readText() {
        int length = data.length;
        while (length > 0 && data[length - 1] == 0) length--;
        return new String(data, 0, length, StandardCharsets.UTF_8);
    }
}
