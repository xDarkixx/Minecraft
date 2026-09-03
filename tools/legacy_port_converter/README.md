# Legacy Port Converter

This tool analyzes the historical Java sources under `DragonAPI/` and `RotaryCraft/` and produces a machine-readable migration report for the modern NeoForge port.

## Usage

From the repository root:

```text
python tools/legacy_port_converter/converter.py
```

This creates `build/legacy-port-report.json` and does not modify legacy sources.

To create conservative transformed copies:

```text
python tools/legacy_port_converter/converter.py --convert
```

Copies are written below `build/converted-sources/`. The originals remain untouched.

## What it does

- Scans every Java file in both legacy source trees.
- Detects common Forge/FML 1.7.10 APIs and flags them by severity.
- Records the exact source line and a migration direction.
- Supports a deliberately conservative conversion mode; currently this only performs mechanical line-ending normalization.
- Produces JSON suitable for later automated migration passes.

The tool does **not** pretend that a regex can safely port Minecraft 1.7.10 to modern NeoForge. API and behavior changes require subsystem-specific migration code and tests. New transformations should only be added when they are mechanically safe and covered by regression tests.
