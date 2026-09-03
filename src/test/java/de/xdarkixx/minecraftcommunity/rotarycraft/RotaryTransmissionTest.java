package de.xdarkixx.minecraftcommunity.rotarycraft;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class RotaryTransmissionTest {
    @Test
    void preservesIdealMechanicalPower() {
        RotaryPowerState input = new RotaryPowerState(100, 20);
        RotaryPowerState output = new RotaryTransmission(2, 1, 0).transmit(input);
        assertEquals(200, output.speedRpm(), 1e-9);
        assertEquals(10, output.torque(), 1e-9);
        assertEquals(input.mechanicalPower(), output.mechanicalPower(), 1e-9);
    }

    @Test
    void appliesEfficiencyAndTorqueLimit() {
        RotaryPowerState input = new RotaryPowerState(100, 20);
        RotaryPowerState output = new RotaryTransmission(0.5, 0.8, 25).transmit(input);
        assertEquals(50, output.speedRpm(), 1e-9);
        assertEquals(25, output.torque(), 1e-9);
    }
}
