# Minecraft Community – DragonAPI & RotaryCraft

Community modernization project for the legacy DragonAPI and RotaryCraft codebase.

## Target

- Minecraft Java Edition **26.2**
- NeoForge **26.2.0.62**
- Java **25**
- ModDevGradle **2.0.143**

The target is based on the current Minecraft 26.2 release and the current 26.2 NeoForge line.

## Project layout

- `DragonAPI/` – preserved legacy DragonAPI tree and resources
- `RotaryCraft/` – preserved legacy RotaryCraft tree and resources
- `src/main/java/` – new, independently maintained 26.2 implementation
- `src/main/templates/META-INF/neoforge.mods.toml` – metadata for both mods

The old 1.7.10 Java sources are deliberately not compiled by the modern build. This keeps the historical material intact while allowing the modern implementation to be ported incrementally.

## Community migration rules

1. Port functionality subsystem-by-subsystem instead of doing unsafe global text replacements.
2. Keep server and client code separated where NeoForge requires it.
3. Prefer modern registries, events, capabilities/components and networking APIs.
4. Do not copy new upstream source wholesale; new migration code is independently implemented.
5. `.gitkeep` placeholders are allowed for planned package/resource directories.

## Building

A GitHub Actions workflow is included to build the project with Java 25 and Gradle 8.8. A local build requires network access so Gradle can resolve Minecraft and NeoForge dependencies.

## Status

The 26.2 community foundation is in place. Full feature parity with the original 1.7.10 DragonAPI/RotaryCraft code is a larger migration and is intentionally tracked as incremental work rather than being falsely marked complete.
