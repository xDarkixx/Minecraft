package de.xdarkixx.minecraft.opencomputers.runtime;

/** Immutable event delivered to a computer. */
public record ComputerSignal(String name, Object[] arguments) {
    public ComputerSignal {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("signal name is required");
        arguments = arguments == null ? new Object[0] : arguments.clone();
    }

    @Override
    public Object[] arguments() { return arguments.clone(); }
}
