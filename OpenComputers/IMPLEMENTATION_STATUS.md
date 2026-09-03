# OpenComputers modern port — implementation status

Target: Minecraft 26.2 / NeoForge 26.2.x / Java 25.

## Current foundation
- Computer block/entity foundation
- Persistent computer state
- Component registry/address model
- Virtual filesystem boundary
- EEPROM and virtual HDD model
- Lua 5.3 runtime boundary and sandbox policy
- Lua API facade layer for computer/component/filesystem/event/screen

## Remaining implementation
- Bind Lua facades into the Lua VM
- event.pull / event.push scheduling
- GPU + screen rendering and keyboard input
- EEPROM/HDD item/block persistence
- energy, redstone, inventory and fluid adapters
- modem/network packets
- robot entity, inventory and upgrades
- tablet/server/rack systems
- OpenOS-compatible filesystem and boot image
- recipes, tags, loot, models, translations
- client/server synchronization
- unit tests, GameTests, dedicated-server test
- final Gradle build verification

## Verification rule
This file intentionally does not mark the port as complete until the complete Gradle build, tests, client startup and dedicated-server startup have been verified successfully.
