# OpenOS compatibility layer

The modern port keeps the classic OpenComputers programming model while rebuilding the host integration for Minecraft 26.2.

Planned compatibility modules:

- `computer` — uptime, energy, boot/reboot/shutdown and signals
- `component` — address lookup, listing and controlled method invocation
- `filesystem` — mounted virtual filesystems and handles
- `event` — queued computer events
- `term` — terminal input/output
- `sides` — side constants
- `unicode` — Unicode helpers
- `serialization` — safe Lua value serialization

Hardware-specific modules such as `gpu`, `screen`, `keyboard`, `redstone`, `modem`, `internet`, `robot` and `navigation` are implemented behind explicit server-side capabilities.

Lua programs never receive direct JVM reflection, process, host filesystem or ambient socket access.
