package de.xdarkixx.minecraftcommunity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.neoforged.fml.common.Mod;

/**
 * Modern community-maintained RotaryCraft foundation for Minecraft 26.2.
 *
 * <p>RotaryCraft declares DragonAPI as a required dependency in the mod
 * metadata and will be migrated subsystem-by-subsystem on top of this
 * foundation.</p>
 */
@Mod(RotaryCraftCommunity.MOD_ID)
public final class RotaryCraftCommunity {
    public static final String MOD_ID = "rotarycraft";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public RotaryCraftCommunity() {
        LOGGER.info("Minecraft Community RotaryCraft {} loaded for Minecraft {} / NeoForge {}",
                BuildInfo.VERSION, BuildInfo.MINECRAFT_VERSION, BuildInfo.NEOFORGE_VERSION);
    }
}
