package de.xdarkixx.minecraft.opencomputers.lua;

/** Explicit deny-by-default policy for embedded computer programs. */
public final class LuaSandboxPolicy {
    private final int operationLimit;

    public LuaSandboxPolicy(int operationLimit) {
        if (operationLimit < 1) throw new IllegalArgumentException("operationLimit must be positive");
        this.operationLimit = operationLimit;
    }

    public int operationLimit() { return operationLimit; }

    public boolean allowsHostFileAccess() { return false; }
    public boolean allowsProcessExecution() { return false; }
    public boolean allowsReflection() { return false; }
    public boolean allowsAmbientNetworkAccess() { return false; }
}
