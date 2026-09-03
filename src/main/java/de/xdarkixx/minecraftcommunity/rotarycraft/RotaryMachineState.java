package de.xdarkixx.minecraftcommunity.rotarycraft;

/**
 * Deterministic mechanical state used as a foundation for the RotaryCraft community port.
 * Speed is expressed in rotations per minute and torque in arbitrary mechanical units.
 */
public record RotaryMachineState(double speedRpm, double torque) {
    public RotaryMachineState {
        if (!Double.isFinite(speedRpm) || !Double.isFinite(torque)) {
            throw new IllegalArgumentException("speed and torque must be finite");
        }
        if (speedRpm < 0.0 || torque < 0.0) {
            throw new IllegalArgumentException("speed and torque must not be negative");
        }
    }

    public double power() {
        return speedRpm * torque;
    }

    public boolean isRunning() {
        return speedRpm > 0.0 && torque > 0.0;
    }
}
