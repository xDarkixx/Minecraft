# Community Porting Plan

This repository is a community modernization effort for the legacy DragonAPI and RotaryCraft projects.

## Reference projects

- ReikaKalseki/DragonAPI
- ReikaKalseki/RotaryCraft

The reference repositories are used to understand architecture and feature intent. The modern implementation is written independently and is not a wholesale copy of the legacy source.

## Target platform

- Minecraft Java Edition 26.2
- NeoForge 26.2.x
- Java 25
- ModDevGradle 2.0.143

## Port order

1. Core identifiers, registration and lifecycle foundation
2. Common utility and serialization layer
3. Networking and server/client separation
4. Rotary power model: speed, torque, power and transmission limits
5. Blocks, block entities and inventories
6. Machines and processing systems
7. Items, tools and upgrades
8. GUI/menu and client rendering systems
9. World generation, recipes, tags and data generation
10. Compatibility integrations and optional modules
11. Automated tests and dedicated-server verification

## Compatibility rule

Legacy 1.7.10 code remains under `DragonAPI/` and `RotaryCraft/` as historical material. It is excluded from the modern Gradle source set. Each migrated subsystem must use current Minecraft/NeoForge APIs and be validated independently before the next subsystem is added.

## Completion criteria

The port is only considered release-ready after the modern project compiles, launches a client and dedicated server, loads both mod IDs, and passes automated regression tests for the migrated systems.
