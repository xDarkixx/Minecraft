# OpenComputers Lua API — 26.2

The compatibility layer exposes OpenComputers-style APIs through an explicitly constructed Lua environment.

## Core modules

- `computer` — uptime, energy state, signals and computer lifecycle.
- `component` — enumerate components, inspect methods and invoke approved component methods.
- `filesystem` — access the computer's virtual mounted filesystems.
- `event` — pull and queue computer events.
- `term` — terminal input/output abstraction.
- `sides` — stable side identifiers.

## Security boundary

Lua code receives no arbitrary Java reflection, host process execution, host filesystem access or unrestricted sockets. Every operation crosses an explicit Java-side capability boundary and is subject to the computer's execution budget.

## Compatibility

The target is Lua 5.3 through the embedded Luna runtime. The API follows the concepts of the original OpenComputers Lua environment while using modern Minecraft/NeoForge services underneath.
