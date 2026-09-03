# OpenComputers → Minecraft 26.2

This directory tracks the modern port of the archived MightyPirates/OpenComputers project.

## Current milestone

- Target: Minecraft 26.2
- Loader: NeoForge 26.2.x
- Java: 25
- Mod ID: `opencomputers`
- Modern entry point: `de.xdarkixx.minecraft.opencomputers.OpenComputersMod`
- Registered hardware anchor: `opencomputers:computer`

## Migration policy

The original OpenComputers code targets Minecraft 1.7.10 and cannot be compiled unchanged against the modern API. The port therefore keeps legacy sources as historical material and migrates functionality subsystem-by-subsystem.

Planned migration order:

1. Registry and item/block foundation
2. Computer state, persistence and lifecycle
3. Component/device API
4. Lua runtime and sandbox boundary
5. Robot/entity subsystem
6. Network, energy and inventory adapters
7. Screens, keyboards and client rendering
8. Recipes, loot, tags and data generation
9. Compatibility and GameTest coverage

The first milestone is intentionally small and build-oriented rather than pretending the complete 1.7.10 implementation is already portable.

## Upstream

The original project is `MightyPirates/OpenComputers`. Its source is MIT licensed; any copied upstream source must retain its original copyright/license notices. Assets are subject to their individual upstream licensing statements.

This community port is not an official OpenComputers release.
