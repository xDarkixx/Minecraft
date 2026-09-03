# OpenComputers → Minecraft 26.2

This directory tracks the modern port of the archived `MightyPirates/OpenComputers` project.

## Scope

- **Only OpenComputers functionality is part of this project.**
- DragonAPI and RotaryCraft are not dependencies and are not packaged.
- The distributable file is exactly `Minecraft.jar`.

## Current milestone

- Target: **Minecraft Java 26.2**
- Loader: NeoForge 26.2.x
- Java: 25
- Mod ID: `opencomputers`
- Modern entry point: `de.xdarkixx.minecraft.opencomputers.OpenComputersMod`
- Registered hardware anchor: `opencomputers:computer`
- Lua runtime: Luna / Lua 5.3

## Implemented in the current port

- Computer lifecycle, server ticking and chunk persistence
- Component registry and built-in component inventory
- EEPROM and virtual HDD abstraction
- Persistent, host-independent virtual filesystem
- Energy storage/consumption model
- Inventory insertion/extraction model
- In-process modem/network packet routing and subscriptions
- GPU-style text screen buffer and keyboard event queue
- Robot movement, orientation, energy and inventory
- OpenOS bootstrap resource and Lua runtime boundary
- Computer crafting recipe and existing client block/item models
- Automated unit coverage for the subsystem layer

## Still required before `Minecraft.jar` is marked finished

1. Bind every component operation into the Lua environment (`computer`, `component`, `filesystem`, `gpu`, `screen`, `keyboard`, `modem`, `robot`, `inventory_controller`).
2. Replace the bootstrap-only OpenOS resource with the full OpenOS userspace/driver set needed for the migrated API.
3. Add real Minecraft-side robot entity/item interaction and inventory capabilities.
4. Add the final client-side screen/GPU/keyboard renderer and interaction events.
5. Complete EEPROM/HDD item/block registration and recipe/data coverage beyond the computer anchor.
6. Add GameTest coverage for placement, ticking, persistence, networking and client/server boundaries.
7. Run the full Gradle build plus client and dedicated-server smoke tests and inspect the produced `Minecraft.jar`.

`Minecraft.jar` must **not** be declared finished until the remaining items above are verified by CI.

## Upstream compatibility

The only functional reference for this port is the original `MightyPirates/OpenComputers` source. The original project targets Minecraft 1.7.10 and cannot be compiled unchanged against the modern API, so its behavior is being reimplemented subsystem-by-subsystem for NeoForge 26.2.

The original OpenComputers project describes persistent programmable computers and robots with an embedded Lua runtime. The modern port keeps the same architectural goals while replacing legacy Minecraft/Forge APIs with current NeoForge APIs.

## Verification rule

The project is only considered complete when all migrated OpenComputers systems build successfully, automated tests pass, `Minecraft.jar` contains only the OpenComputers mod, and both client and dedicated-server startup have been verified on the target NeoForge/Minecraft version.

This community port is not an official OpenComputers release.

## Upstream

The original project is `MightyPirates/OpenComputers`. Its source is MIT licensed; copied upstream source must retain its original copyright/license notices. Assets are subject to their individual upstream licensing statements.
