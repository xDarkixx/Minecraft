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
}
