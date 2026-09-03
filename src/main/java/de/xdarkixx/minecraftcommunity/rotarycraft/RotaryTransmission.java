package de.xdarkixx.minecraftcommunity.rotarycraft;

/**
 * Pure mechanical transmission step. A ratio above one increases output speed
 * and reduces output torque; a ratio below one does the opposite.
 */
public record RotaryTransmission(double ratio, double efficiency, double maxTorque) {
    public RotaryTransmission {
        if (!Double.isFinite(ratio) || ratio <= 0) {
            throw new IllegalArgumentException("ratio must be finite and positive");
        }
        if (!Double.isFinite(efficiency) || efficiency <= 0 || efficiency > 1) {
            throw new IllegalArgumentException("efficiency must be in (0, 1]");
        }
        if (!Double.isFinite(maxTorque) || maxTorque < 0) {
            throw new IllegalArgumentException("maxTorque must be finite and non-negative");
        }
    }

    public RotaryPowerState transmit(RotaryPowerState input) {
        double outputSpeed = input.speedRpm() * ratio;
        double outputTorque = input.torque() / ratio * efficiency;
        if (maxTorque > 0) {
            outputTorque = Math.min(outputTorque, maxTorque);
        }
        return new RotaryPowerState(outputSpeed, outputTorque);
    }
}
