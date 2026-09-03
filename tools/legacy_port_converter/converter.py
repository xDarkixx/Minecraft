#!/usr/bin/env python3
"""Legacy Forge/1.7.10 source analyzer and conservative port converter.

Default mode is analysis only. --convert writes mechanically transformed copies
and never modifies the legacy source tree.
"""
from __future__ import annotations

import argparse
import json
import re
from dataclasses import asdict, dataclass
from pathlib import Path


@dataclass(frozen=True)
class Finding:
    file: str
    line: int
    rule: str
    severity: str
    text: str
    suggestion: str


RULES = [
    ("legacy_fml_import", re.compile(r"cpw\.mods\.fml"), "error", "Legacy FML import", "Migrate to net.neoforged.* APIs."),
    ("legacy_forge_import", re.compile(r"import\s+net\.minecraftforge\b"), "error", "Legacy Forge import", "Map the subsystem to the current NeoForge API."),
    ("mod_annotation", re.compile(r"@Mod\s*\("), "warning", "Legacy @Mod usage", "Keep only the modern NeoForge @Mod entrypoint pattern."),
    ("fml_lifecycle", re.compile(r"FML(?:PreInitialization|Initialization|PostInitialization|ServerStarting|ServerStarted|ServerStopping|ServerStopped)Event"), "error", "Legacy FML lifecycle event", "Replace with the matching modern NeoForge lifecycle event."),
    ("game_registry", re.compile(r"GameRegistry\.(?:registerBlock|registerItem|registerTileEntity|registerWorldGenerator)"), "error", "Legacy GameRegistry registration", "Use modern deferred/vanilla registries."),
    ("side_only", re.compile(r"@SideOnly\s*\("), "error", "Legacy @SideOnly", "Split client-only code with modern NeoForge distribution/client event mechanisms."),
    ("icon_api", re.compile(r"\bIIcon\b|\bIIconRegister\b"), "error", "Legacy icon rendering API", "Migrate to modern model/texture and client rendering APIs."),
    ("tile_entity", re.compile(r"\bTileEntity\b"), "warning", "Legacy TileEntity API", "Migrate to the modern BlockEntity API."),
    ("inventory", re.compile(r"\bIInventory\b"), "warning", "Legacy IInventory API", "Migrate inventory behavior to current container/menu and item-handler APIs."),
    ("old_nbt", re.compile(r"\bNBTTag(?:Compound|List|String|Int|Double|Long)\b"), "warning", "Legacy NBT class", "Review against modern data components/serialization APIs."),
    ("math_helper", re.compile(r"\bMathHelper\b"), "warning", "Legacy MathHelper", "Use the current Minecraft math utilities."),
    ("chat_formatting", re.compile(r"\bEnumChatFormatting\b"), "warning", "Legacy chat formatting", "Use modern Style/Component formatting."),
    ("world_server", re.compile(r"\bWorldServer\b"), "warning", "Legacy WorldServer type", "Use the current ServerLevel/server world API."),
]

# Only transformations that are intentionally mechanical and do not claim semantic migration.
SAFE_REPLACEMENTS = [
    (re.compile(r"\r\n?"), "\n"),
]


def analyze_file(path: Path, root: Path) -> list[Finding]:
    try:
        text = path.read_text(encoding="utf-8")
    except (UnicodeDecodeError, OSError):
        return []
    findings: list[Finding] = []
    rel = path.relative_to(root).as_posix()
    for number, line in enumerate(text.splitlines(), 1):
        for rule, pattern, severity, description, suggestion in RULES:
            if pattern.search(line):
                findings.append(Finding(rel, number, rule, severity, line.strip(), suggestion))
    return findings


def scan(root: Path) -> tuple[list[Finding], int]:
    findings: list[Finding] = []
    files = 0
    for path in sorted(root.rglob("*.java")):
        files += 1
        findings.extend(analyze_file(path, root))
    return findings, files


def convert_file(source: Path, destination: Path) -> None:
    text = source.read_text(encoding="utf-8")
    for pattern, replacement in SAFE_REPLACEMENTS:
        text = pattern.sub(replacement, text)
    destination.parent.mkdir(parents=True, exist_ok=True)
    destination.write_text(text, encoding="utf-8")


def main() -> int:
    parser = argparse.ArgumentParser(description="Analyze legacy DragonAPI/RotaryCraft Java sources for NeoForge porting.")
    parser.add_argument("roots", nargs="*", type=Path, default=[Path("DragonAPI"), Path("RotaryCraft")])
    parser.add_argument("--report", type=Path, default=Path("build/legacy-port-report.json"))
    parser.add_argument("--convert", action="store_true", help="Write conservative transformed copies; originals are untouched.")
    parser.add_argument("--output", type=Path, default=Path("build/converted-sources"))
    args = parser.parse_args()

    all_findings: list[Finding] = []
    scanned_files = 0
    roots_used: list[str] = []
    for root in args.roots:
        if not root.exists():
            continue
        roots_used.append(root.as_posix())
        findings, count = scan(root)
        all_findings.extend(findings)
        scanned_files += count
        if args.convert:
            for source in sorted(root.rglob("*.java")):
                convert_file(source, args.output / root.name / source.relative_to(root))

    report = {
        "tool": "legacy_port_converter",
        "mode": "convert" if args.convert else "analyze",
        "roots": roots_used,
        "java_files_scanned": scanned_files,
        "findings": [asdict(f) for f in all_findings],
        "summary": {
            "errors": sum(f.severity == "error" for f in all_findings),
            "warnings": sum(f.severity == "warning" for f in all_findings),
            "findings": len(all_findings),
        },
    }
    args.report.parent.mkdir(parents=True, exist_ok=True)
    args.report.write_text(json.dumps(report, indent=2, ensure_ascii=False) + "\n", encoding="utf-8")
    print(f"Scanned {scanned_files} Java files; found {len(all_findings)} porting findings.")
    print(f"Report: {args.report}")
    if args.convert:
        print(f"Converted copies: {args.output}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
