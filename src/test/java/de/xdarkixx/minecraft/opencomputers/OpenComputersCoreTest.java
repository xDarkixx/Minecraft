package de.xdarkixx.minecraft.opencomputers;

import de.xdarkixx.minecraft.opencomputers.api.BasicComponent;
import de.xdarkixx.minecraft.opencomputers.api.ComponentContainer;
import de.xdarkixx.minecraft.opencomputers.hardware.VirtualFileSystem;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenComputersCoreTest {
    @Test
    void componentsHaveStableAddresses() {
        ComponentContainer container = new ComponentContainer();
        UUID address = container.attach(new BasicComponent("cpu", Set.of("getTier")));
        assertTrue(container.get(address).isPresent());
        assertEquals("cpu", container.get(address).orElseThrow().type());
        assertEquals(address, container.findAddress("cpu").orElseThrow());
        assertTrue(container.detach(address));
        assertFalse(container.get(address).isPresent());
    }

    @Test
    void filesystemCopiesData() {
        VirtualFileSystem fs = new VirtualFileSystem();
        byte[] source = {1, 2, 3};
        fs.write("/home/test", source);
        source[0] = 9;
        assertArrayEquals(new byte[]{1, 2, 3}, fs.read("home/test"));
        assertTrue(fs.delete("home/test"));
        assertNull(fs.read("home/test"));
    }

    @Test
    void filesystemPersistsAndRestores() {
        VirtualFileSystem first = new VirtualFileSystem(8192);
        first.write("home/boot.lua", "print('boot')".getBytes());
        String snapshot = first.serialize();
        VirtualFileSystem second = new VirtualFileSystem(8192);
        second.deserialize(snapshot);
        assertArrayEquals(first.read("home/boot.lua"), second.read("home/boot.lua"));
    }

    @Test
    void energyAndInventoryEnforceLimits() {
        OCSystems.Energy energy = new OCSystems.Energy(1000);
        assertEquals(1000, energy.stored());
        assertEquals(250, energy.extract(250));
        assertTrue(energy.consume(100));
        assertEquals(650, energy.stored());

        OCSystems.Inventory inventory = new OCSystems.Inventory(2, 64);
        assertEquals(64, inventory.insert("minecraft:iron_ingot", 100));
        assertEquals(36, inventory.insert("minecraft:iron_ingot", 36));
        assertEquals(100, inventory.count("minecraft:iron_ingot"));
        assertEquals(20, inventory.extract("minecraft:iron_ingot", 20));
    }

    @Test
    void screenKeyboardAndRobotWork() {
        OCSystems.Screen screen = new OCSystems.Screen(10, 2);
        screen.write("hello");
        assertEquals("hello     ", screen.line(0));
        screen.setCursor(0, 1);
        screen.write("world");
        assertEquals("world     ", screen.line(1));

        OCSystems.Keyboard keyboard = new OCSystems.Keyboard();
        keyboard.keyDown("a");
        keyboard.keyUp("a");
        assertEquals("key_down:a", keyboard.poll());
        assertEquals("key_up:a", keyboard.poll());

        OCSystems.Robot robot = new OCSystems.Robot();
        assertTrue(robot.move());
        assertEquals(-1, robot.z());
        robot.turnRight();
        assertTrue(robot.move());
        assertEquals(1, robot.x());
    }

    @Test
    void networkRoutesPackets() {
        OCSystems.Network network = new OCSystems.Network();
        UUID a = UUID.randomUUID();
        UUID b = UUID.randomUUID();
        network.register(a);
        network.subscribe(b, "oc");
        assertTrue(network.send(a, null, "oc", "hello", 42));
        OCSystems.Network.Packet packet = network.receive(b);
        assertNotNull(packet);
        assertEquals("oc", packet.channel());
        assertEquals("hello", packet.payload().get(0));
        assertEquals(42, packet.payload().get(1));
    }

    @Test
    void computerStateProvidesFullHardwareBundle() {
        ComputerState state = new ComputerState();
        assertNotNull(state.storage().filesystem());
        assertNotNull(state.robot());
        assertNotNull(state.screen());
        assertNotNull(state.keyboard());
        assertNotNull(state.network());
        assertNotNull(state.inventory());
        assertNotNull(state.energy());
        assertNotNull(state.findComponent("gpu"));
        state.setRunning(true);
        state.tick();
        assertEquals(1, state.ticks());
    }
}
