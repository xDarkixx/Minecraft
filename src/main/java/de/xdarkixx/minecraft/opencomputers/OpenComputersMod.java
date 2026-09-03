package de.xdarkixx.minecraft.opencomputers;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

/**
 * Modern OpenComputers port entry point.
 *
 * This package is intentionally isolated from the legacy 1.7.10 sources.
 * Subsystems are being migrated to the NeoForge 26.2 API incrementally.
 */
@Mod(OpenComputersMod.MOD_ID)
public final class OpenComputersMod {
    public static final String MOD_ID = "opencomputers";

    public OpenComputersMod(IEventBus modBus) {
        OCRegistries.register(modBus);
    }
}
