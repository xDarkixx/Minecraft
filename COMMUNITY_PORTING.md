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

## Port order and current status

| Order | Subsystem | Owner | Status |
|---:|---|---|---|
| 1 | Core identifiers, registration and lifecycle foundation | DragonAPI | **Implemented foundation** |
| 2 | Common utility and serialization layer | DragonAPI | Planned |
| 3 | Networking and server/client separation | DragonAPI | Planned |
| 4 | Rotary power model: speed, torque, power and transmission limits | RotaryCraft | **Implemented pure core** |
| 5 | Blocks, block entities and inventories | RotaryCraft | Planned |
| 6 | Machines and processing systems | RotaryCraft | Planned |
| 7 | Items, tools and upgrades | RotaryCraft | Planned |
| 8 | GUI/menu and client rendering systems | Both | Planned |
| 9 | World generation, recipes, tags and data generation | Both | Planned |
| 10 | Compatibility integrations and optional modules | Both | Planned |
| 11 | Automated tests and dedicated-server verification | Both | In progress continuously |

The migration priority is encoded in `PortingPriority.ordered()` so tooling and documentation share one sequence.

## Implemented first milestones

### DragonAPI foundation

- `CommunityId` provides a validated, loader-independent identifier.
- `RegistrationTracker` provides deterministic duplicate-safe registration/indexing.
- `DragonAPICommunity` is the modern NeoForge entrypoint.

### RotaryCraft mechanical core

- `RotaryPowerState` models speed and torque without Minecraft dependencies.
- `RotaryTransmission` models ratio, efficiency and torque limits.
- JUnit regression tests cover ideal power preservation and efficiency/limit behavior.

## Compatibility rule

Legacy 1.7.10 code remains under `DragonAPI/` and `RotaryCraft/` as historical material. It is excluded from the modern Gradle source set. Each migrated subsystem must use current Minecraft/NeoForge APIs and be validated independently before the next subsystem is added.

## Completion criteria

The port is only considered release-ready after the modern project compiles, launches a client and dedicated server, loads both mod IDs, and passes automated regression tests for the migrated systems.
