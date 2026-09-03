# Minecraft Community – DragonAPI & RotaryCraft

Community modernization project for the legacy DragonAPI and RotaryCraft codebase.

## Target

- Minecraft Java Edition **26.2**
- NeoForge **26.2.x** (currently pinned to **26.2.0.62**)
- Java **25**
- ModDevGradle **2.0.143**
- Gradle **9.1+**

Minecraft 26.2 is the released target. The project is pinned to a known NeoForge 26.2 artifact so builds remain reproducible until the pin is deliberately advanced.

## Project layout

- `DragonAPI/` – preserved legacy DragonAPI tree and resources
- `RotaryCraft/` – preserved legacy RotaryCraft tree and resources
- `src/main/java/` – new, independently maintained 26.2 implementation
- `src/main/templates/META-INF/neoforge.mods.toml` – metadata for both mods
- `COMMUNITY_PORTING.md` – migration order and release criteria

The old 1.7.10 Java sources are deliberately not compiled by the modern build. Historical material stays available while the modern implementation is migrated subsystem-by-subsystem.

## Current foundation

- Separate NeoForge entrypoints for `dragonapi` and `rotarycraft`
- Shared modern build for both mod IDs
- Loader-independent DragonAPI registration/index foundation
- Loader-independent RotaryCraft mechanical power state
- Legacy source trees excluded from the 26.2 Java compilation
- GitHub Actions build using Java 25 and Gradle 9.1

## Migration rules

1. Port functionality subsystem-by-subsystem instead of unsafe global text replacements.
2. Keep server and client code separated where NeoForge requires it.
3. Prefer modern registries, events, components and networking APIs.
4. Write new migration code independently rather than copying upstream source wholesale.
5. Keep compatibility boundaries explicit so RotaryCraft can depend on DragonAPI.
6. `.gitkeep` placeholders are allowed for planned package/resource directories.

## Building

GitHub Actions is configured to build the project with Java 25 and Gradle 9.1. A local build requires network access so Gradle can resolve Minecraft and NeoForge dependencies.

## Status

The community foundation is implemented. Full feature parity with the original 1.7.10 projects is **not yet claimed**; the remaining systems are being migrated according to `COMMUNITY_PORTING.md`. Release readiness requires a successful client build, dedicated-server build, both mod IDs loading, and automated regression coverage.
