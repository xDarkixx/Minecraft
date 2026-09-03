package de.xdarkixx.minecraftcommunity.dragonapi;

import java.util.List;

/** Ordered migration roadmap shared by tooling and documentation. */
public final class PortingPriority {
    public record Step(int order, String subsystem, String owner, String reason) {}

    private PortingPriority() {}

    public static List<Step> ordered() {
        return List.of(
            new Step(1, "identifiers-registration-lifecycle", "DragonAPI", "Everything else depends on stable IDs and lifecycle boundaries."),
            new Step(2, "utility-serialization", "DragonAPI", "Provides reusable data and utility primitives without Minecraft-specific legacy APIs."),
            new Step(3, "networking-side-separation", "DragonAPI", "Required before machines and GUIs can safely communicate."),
            new Step(4, "rotary-power-transmission", "RotaryCraft", "Core gameplay model: speed, torque, power and transmission limits."),
            new Step(5, "blocks-block-entities-inventories", "RotaryCraft", "Makes the power model persistent in the world."),
            new Step(6, "machines-processing", "RotaryCraft", "Ports the main functional gameplay systems on top of the foundation."),
            new Step(7, "items-tools-upgrades", "RotaryCraft", "Ports player-facing equipment after machine APIs stabilize."),
            new Step(8, "menus-guis-rendering", "Both", "Client presentation is migrated after server state and networking are stable."),
            new Step(9, "worldgen-recipes-tags-data", "Both", "Data-driven content is added once registry identities are final."),
            new Step(10, "compatibility-integrations", "Both", "Optional integrations are isolated after core behavior works."),
            new Step(11, "client-server-regression", "Both", "Final validation: build, dedicated server, client and regression tests.")
        );
    }
}
