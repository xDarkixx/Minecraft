# OpenComputers → Minecraft 26.2

This directory tracks the modern port of the archived `MightyPirates/OpenComputers` project.

## Scope

- **Only OpenComputers functionality is part of this project.**
- DragonAPI and RotaryCraft are not dependencies and are not packaged.
- The distributable file is exactly `Minecraft.jar`.

## Current milestone

- Target: **Minecraft Java 26.2 (latest stable release)**
- Loader: NeoForge 26.2.x
- Java: 25
- Mod ID: `opencomputers`
- Modern entry point: `de.xdarkixx.minecraft.opencomputers.OpenComputersMod`
- Registered hardware anchor: `opencomputers:computer`
- Lua runtime: Luna / Lua 5.3

Minecraft Java 26.2 is the latest stable release at the time of this port; Minecraft 26.3 is currently a pre-release, so it is not used as the production target.

## Upstream compatibility

The only functional reference for this port is the original `MightyPirates/OpenComputers` source. The original project targets Minecraft 1.7.10 and cannot be compiled unchanged against the modern API, so its behavior is being reimplemented subsystem-by-subsystem for NeoForge 26.2.

The original OpenComputers project describes persistent programmable computers and robots with an embedded Lua runtime. The modern port keeps the same architectural goals while replacing legacy Minecraft/Forge APIs with current NeoForge APIs.

## Migration order

1. Registry and item/block foundation
2. Computer state, persistence and lifecycle
3. Component/device API
4. Lua runtime and sandbox boundary
5. Robot/entity subsystem
6. Network, energy and inventory adapters
7. Screens, keyboards and client rendering
8. Recipes, loot, tags and data generation
9. Compatibility and GameTest coverage
10. Client and dedicated-server startup verification

## Verification rule

The project is only considered complete when all migrated OpenComputers systems build successfully, automated tests pass, `Minecraft.jar` contains only the OpenComputers mod, and both client and dedicated-server startup have been verified on the target NeoForge/Minecraft version.

This community port is not an official OpenComputers release.

## Upstream

The original project is `MightyPirates/OpenComputers`. Its source is MIT licensed; copied upstream source must retain its original copyright/license notices. Assets are subject to their individual upstream licensing statements.