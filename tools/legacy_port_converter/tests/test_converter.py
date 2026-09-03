import json
import tempfile
import unittest
from pathlib import Path
import sys

sys.path.insert(0, str(Path(__file__).resolve().parents[1]))
import converter


class ConverterTests(unittest.TestCase):
    def test_detects_common_legacy_apis(self):
        with tempfile.TemporaryDirectory() as tmp:
            root = Path(tmp)
            src = root / "Example.java"
            src.write_text(
                "import cpw.mods.fml.common.Mod;\n"
                "import net.minecraftforge.common.MinecraftForge;\n"
                "@Mod(modid=\"example\")\n"
                "public class Example { TileEntity tile; IInventory inv; }\n",
                encoding="utf-8",
            )
            findings = converter.analyze_file(src, root)
            rules = {item.rule for item in findings}
            self.assertIn("legacy_fml_import", rules)
            self.assertIn("legacy_forge_import", rules)
            self.assertIn("mod_annotation", rules)
            self.assertIn("tile_entity", rules)
            self.assertIn("inventory", rules)

    def test_convert_never_changes_source_and_normalizes_copy(self):
        with tempfile.TemporaryDirectory() as tmp:
            root = Path(tmp)
            src = root / "Example.java"
            original = b"class Example {\r\n}\r\n"
            src.write_bytes(original)
            out = root / "out" / "Example.java"
            converter.convert_file(src, out)
            self.assertEqual(src.read_bytes(), original)
            self.assertEqual(out.read_bytes(), b"class Example {\n}\n")

    def test_report_is_json_serializable(self):
        finding = converter.Finding("x.java", 1, "rule", "warning", "text", "suggestion")
        payload = {"findings": [converter.asdict(finding)]}
        self.assertEqual(json.loads(json.dumps(payload))["findings"][0]["rule"], "rule")


if __name__ == "__main__":
    unittest.main()
