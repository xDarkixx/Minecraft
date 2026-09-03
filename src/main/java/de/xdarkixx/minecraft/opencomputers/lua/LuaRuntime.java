package de.xdarkixx.minecraft.opencomputers.lua;

import org.classdump.luna.compiler.CompilerChunkLoader;
import org.classdump.luna.compiler.loaders.ChunkLoader;
import org.classdump.luna.runtime.DirectCallExecutor;
import org.classdump.luna.runtime.LuaFunction;
import org.classdump.luna.runtime.StateContext;
import org.classdump.luna.runtime.StateContexts;
import org.classdump.luna.runtime.Variable;
import org.classdump.luna.stdlib.StandardLibrary;
import org.classdump.luna.runtime.RuntimeEnvironments;

/** Embedded Lua 5.3 execution boundary for an OpenComputers computer. */
public final class LuaRuntime {
    private final StateContext state = StateContexts.newDefaultInstance();

    public LuaRuntime() {
        StandardLibrary.in(RuntimeEnvironments.system()).installInto(state);
    }

    public void execute(String source) {
        ChunkLoader loader = CompilerChunkLoader.of("opencomputers");
        LuaFunction function = loader.loadTextChunk(new Variable(state.getGlobalEnvironment()), "computer", source);
        DirectCallExecutor.newExecutorWithTickLimit(100_000).call(state, function);
    }
}
