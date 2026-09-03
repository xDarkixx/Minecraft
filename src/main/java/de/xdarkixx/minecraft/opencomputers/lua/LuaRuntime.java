package de.xdarkixx.minecraft.opencomputers.lua;

import org.classdump.luna.StateContext;
import org.classdump.luna.Table;
import org.classdump.luna.Variable;
import org.classdump.luna.compiler.CompilerChunkLoader;
import org.classdump.luna.exec.DirectCallExecutor;
import org.classdump.luna.load.ChunkLoader;
import org.classdump.luna.runtime.LuaFunction;
import org.classdump.luna.impl.StateContexts;

/** Embedded Lua 5.3 execution boundary for an OpenComputers computer. */
public final class LuaRuntime {
    private final StateContext state = StateContexts.newDefaultInstance();
    private final Table environment = state.newTable();
    private final LuaSandboxPolicy policy;

    public LuaRuntime() {
        this(new LuaSandboxPolicy(100_000));
    }

    public LuaRuntime(LuaSandboxPolicy policy) {
        this.policy = policy;
    }

    public void execute(String source) {
        if (source == null) {
            throw new IllegalArgumentException("Lua source is required");
        }
        ChunkLoader loader = CompilerChunkLoader.of("opencomputers");
        LuaFunction function = loader.loadTextChunk(new Variable(environment), "computer", source);
        try {
            DirectCallExecutor.newExecutorWithTickLimit(policy.operationLimit()).call(state, function);
        } catch (Exception ex) {
            throw new IllegalStateException("Lua execution failed", ex);
        }
    }
}
