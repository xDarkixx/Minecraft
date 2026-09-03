package de.xdarkixx.minecraft.opencomputers;

import de.xdarkixx.minecraft.opencomputers.api.BasicComponent;
import de.xdarkixx.minecraft.opencomputers.api.Component;
import de.xdarkixx.minecraft.opencomputers.api.ComponentContainer;
import de.xdarkixx.minecraft.opencomputers.hardware.EepromMemory;
import de.xdarkixx.minecraft.opencomputers.hardware.HardDrive;
import de.xdarkixx.minecraft.opencomputers.hardware.VirtualFileSystem;

import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Functional OpenComputers subsystem layer. The implementation is deliberately
 * host-safe: storage is virtual, networking is in-process, and device calls
 * are explicit Java operations exposed to the Lua bridge.
 */
public final class OCSystems {
    private OCSystems() {}

    public static final class Energy {
        private final long capacity;
        private long stored;

        public Energy(long capacity) {
            if (capacity <= 0) throw new IllegalArgumentException("capacity must be positive");
            this.capacity = capacity;
            this.stored = capacity;
        }

        public long capacity() { return capacity; }
        public long stored() { return stored; }
        public long free() { return capacity - stored; }
        public long insert(long amount) {
            if (amount < 0) throw new IllegalArgumentException("amount must be non-negative");
            long accepted = Math.min(amount, free());
            stored += accepted;
            return accepted;
        }
        public long extract(long amount) {
            if (amount < 0) throw new IllegalArgumentException("amount must be non-negative");
            long taken = Math.min(amount, stored);
            stored -= taken;
            return taken;
        }
        public boolean consume(long amount) { return extract(amount) == amount; }
    }

    public static final class Inventory {
        private final int slots;
        private final int maxStack;
        private final Map<String, Integer> stacks = new LinkedHashMap<>();

        public Inventory(int slots, int maxStack) {
            if (slots <= 0 || maxStack <= 0) throw new IllegalArgumentException("invalid inventory size");
            this.slots = slots;
            this.maxStack = maxStack;
        }
        public int slots() { return slots; }
        public int maxStack() { return maxStack; }
        public int count(String item) { return stacks.getOrDefault(item, 0); }
        public Set<String> items() { return Set.copyOf(stacks.keySet()); }
        public int insert(String item, int amount) {
            Objects.requireNonNull(item, "item");
            if (amount < 0) throw new IllegalArgumentException("amount must be non-negative");
            int current = count(item);
            int usedSlots = (int) stacks.values().stream().filter(v -> v > 0).count();
            int room = current > 0 ? maxStack - current : (slots - usedSlots) * maxStack;
            int accepted = Math.min(amount, Math.max(0, room));
            if (accepted > 0) stacks.put(item, current + accepted);
            return accepted;
        }
        public int extract(String item, int amount) {
            if (amount < 0) throw new IllegalArgumentException("amount must be non-negative");
            int current = count(item);
            int taken = Math.min(current, amount);
            if (taken == current) stacks.remove(item); else if (taken > 0) stacks.put(item, current - taken);
            return taken;
        }
    }

    public static final class Network {
        public record Packet(UUID sender, UUID receiver, String channel, List<Object> payload) {
            public Packet {
                payload = List.copyOf(payload == null ? List.of() : payload);
            }
        }

        private final Map<UUID, Deque<Packet>> queues = new LinkedHashMap<>();
        private final Map<UUID, Set<String>> channels = new LinkedHashMap<>();

        public synchronized void register(UUID address) { queues.putIfAbsent(address, new ArrayDeque<>()); }
        public synchronized void subscribe(UUID address, String channel) {
            register(address);
            channels.computeIfAbsent(address, ignored -> new java.util.HashSet<>()).add(channel == null ? "" : channel);
        }
        public synchronized boolean send(UUID sender, UUID receiver, String channel, Object... payload) {
            register(sender);
            if (receiver != null && !queues.containsKey(receiver)) return false;
            Packet packet = new Packet(sender, receiver, channel == null ? "" : channel, Arrays.asList(payload == null ? new Object[0] : payload));
            if (receiver != null) queues.get(receiver).add(packet);
            else for (Map.Entry<UUID, Deque<Packet>> entry : queues.entrySet()) {
                if (!entry.getKey().equals(sender) && channels.getOrDefault(entry.getKey(), Set.of()).contains(packet.channel())) entry.getValue().add(packet);
            }
            return true;
        }
        public synchronized Packet receive(UUID address) {
            register(address);
            return queues.get(address).pollFirst();
        }
    }

    public static final class Keyboard {
        private final Deque<String> events = new ArrayDeque<>();
        public void keyDown(String key) { events.addLast("key_down:" + Objects.requireNonNull(key)); }
        public void keyUp(String key) { events.addLast("key_up:" + Objects.requireNonNull(key)); }
        public String poll() { return events.pollFirst(); }
    }

    public static final class Screen {
        private final int width;
        private final int height;
        private final char[][] cells;
        private int cursorX;
        private int cursorY;

        public Screen(int width, int height) {
            if (width < 1 || height < 1) throw new IllegalArgumentException("invalid screen size");
            this.width = width;
            this.height = height;
            this.cells = new char[height][width];
            clear();
        }
        public int width() { return width; }
        public int height() { return height; }
        public void clear() {
            for (char[] row : cells) Arrays.fill(row, ' ');
            cursorX = cursorY = 0;
        }
        public void setCursor(int x, int y) {
            cursorX = Math.max(0, Math.min(width - 1, x));
            cursorY = Math.max(0, Math.min(height - 1, y));
        }
        public void write(String text) {
            if (text == null) return;
            for (int i = 0; i < text.length(); i++) {
                char c = text.charAt(i);
                if (c == '\n') { cursorX = 0; cursorY++; }
                else if (cursorY < height) { cells[cursorY][cursorX] = c; cursorX++; if (cursorX >= width) { cursorX = 0; cursorY++; } }
                if (cursorY >= height) cursorY = height - 1;
            }
        }
        public String line(int y) { return new String(cells[Math.max(0, Math.min(height - 1, y))]); }
        public List<String> snapshot() {
            List<String> result = new ArrayList<>(height);
            for (char[] row : cells) result.add(new String(row));
            return List.copyOf(result);
        }
    }

    public static final class Robot {
        public enum Direction { NORTH, EAST, SOUTH, WEST }
        private int x;
        private int y;
        private int z;
        private Direction direction = Direction.NORTH;
        private final Inventory inventory = new Inventory(16, 64);
        private final Energy energy = new Energy(100_000);

        public int x() { return x; }
        public int y() { return y; }
        public int z() { return z; }
        public Direction direction() { return direction; }
        public Inventory inventory() { return inventory; }
        public Energy energy() { return energy; }
        public void turnLeft() { direction = Direction.values()[(direction.ordinal() + 3) % 4]; }
        public void turnRight() { direction = Direction.values()[(direction.ordinal() + 1) % 4]; }
        public boolean move() {
            if (!energy.consume(100)) return false;
            switch (direction) {
                case NORTH -> z--;
                case EAST -> x++;
                case SOUTH -> z++;
                case WEST -> x--;
            }
            return true;
        }
        public boolean up() { if (!energy.consume(100)) return false; y++; return true; }
        public boolean down() { if (!energy.consume(100)) return false; y--; return true; }
    }

    public static final class Storage {
        private final EepromMemory eeprom;
        private final HardDrive hardDrive;
        public Storage(int eepromBytes, int driveBytes) {
            this.eeprom = new EepromMemory(eepromBytes);
            this.hardDrive = new HardDrive(driveBytes);
        }
        public EepromMemory eeprom() { return eeprom; }
        public HardDrive hardDrive() { return hardDrive; }
        public VirtualFileSystem filesystem() { return hardDrive.filesystem(); }
    }

    public static final class Components {
        private final ComponentContainer container = new ComponentContainer();
        public UUID attach(Component component) { return container.attach(component); }
        public ComponentContainer container() { return container; }
        public void attachDefaults() {
            attach(new BasicComponent("computer", Set.of("beep", "start", "stop", "uptime")));
            attach(new BasicComponent("eeprom", Set.of("get", "set", "getData", "setData")));
            attach(new BasicComponent("filesystem", Set.of("list", "read", "write", "delete")));
            attach(new BasicComponent("gpu", Set.of("bind", "set", "fill", "copy")));
            attach(new BasicComponent("screen", Set.of("getKeyboards", "getKeyDown", "getKeyUp")));
            attach(new BasicComponent("keyboard", Set.of("isKeyDown")));
            attach(new BasicComponent("modem", Set.of("open", "close", "send", "broadcast", "isOpen")));
            attach(new BasicComponent("inventory_controller", Set.of("getInventorySize", "getStackInSlot", "suckFromSlot", "dropIntoSlot")));
            attach(new BasicComponent("robot", Set.of("move", "turnLeft", "turnRight", "up", "down")));
        }
    }

    public static String encodeForPersistence(List<String> lines) {
        return String.join("\n", lines).getBytes(StandardCharsets.UTF_8).length == 0 ? "" : String.join("\n", lines);
    }

    public static List<String> decodePersistence(String value) {
        if (value == null || value.isEmpty()) return List.of();
        return List.of(value.split("\\n", -1));
    }
}
