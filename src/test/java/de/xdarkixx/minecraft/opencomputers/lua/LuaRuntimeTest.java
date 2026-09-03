package de.xdarkixx.minecraft.opencomputers.lua;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LuaRuntimeTest {
    @Test
    void executesLua53Arithmetic() {
        LuaRuntime runtime = new LuaRuntime();
        assertDoesNotThrow(() -> runtime.execute("local x = 40 + 2; assert(x == 42)"));
    }

    @Test
    void rejectsNullSource() {
        LuaRuntime runtime = new LuaRuntime();
        assertThrows(IllegalArgumentException.class, () -> runtime.execute(null));
    }

    @Test
    void enforcesOperationBudget() {
        LuaRuntime runtime = new LuaRuntime(new LuaSandboxPolicy(1_000));
        assertThrows(IllegalStateException.class, () -> runtime.execute("while true do end"));
    }
}
