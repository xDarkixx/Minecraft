package de.xdarkixx.minecraftcommunity.rotarycraft;

/**
 * Immutable mechanical state for a RotaryCraft transmission node.
 * Values are deliberately independent of Minecraft classes so the physics
 * layer can be unit-tested before block entities are migrated.
 */
public record RotaryPowerState(double speedRpm, double torque) {
    public RotaryPowerState {
        if (!Double.isFinite(speedRpm) || !Double.isFinite(torque)) {
            throw new IllegalArgumentException("speed and torque must be finite");
        }
        if (speedRpm < 0 || torque < 0) {
            throw new IllegalArgumentException("speed and torque must not be negative");
        }
    }

    public double mechanicalPower() {
        return speedRpm * torque;
    }

    public RotaryPowerState withSpeed(double newSpeedRpm) {
        return new RotaryPowerState(newSpeedRpm, torque);
    }

    public RotaryPowerState withTorque(double newTorque) {
        return new RotaryPowerState(speedRpm, newTorque);
    }
}
