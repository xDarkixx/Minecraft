package de.xdarkixx.minecraftcommunity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.neoforged.fml.common.Mod;

/**
 * Modern community-maintained DragonAPI foundation for Minecraft 26.2.
 *
 * <p>The original 1.7.10 implementation is kept separately in the repository
 * and is not compiled by the modern build.</p>
 */
@Mod(DragonAPICommunity.MOD_ID)
public final class DragonAPICommunity {
    public static final String MOD_ID = "dragonapi";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public DragonAPICommunity() {
        LOGGER.info("Minecraft Community DragonAPI {} loaded for Minecraft {} / NeoForge {}",
                BuildInfo.VERSION, BuildInfo.MINECRAFT_VERSION, BuildInfo.NEOFORGE_VERSION);
    }
}
