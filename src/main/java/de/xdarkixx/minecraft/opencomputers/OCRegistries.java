package de.xdarkixx.minecraft.opencomputers;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

/** Central registry for the first modern OC hardware milestone. */
public final class OCRegistries {
    private OCRegistries() {}

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(OpenComputersMod.MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(OpenComputersMod.MOD_ID);

    /** A functional, persistent hardware shell used as the migration anchor for the computer subsystem. */
    public static final DeferredBlock<Block> COMPUTER = BLOCKS.registerBlock(
            "computer",
            Block::new,
            BlockBehaviour.Properties.of()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceKey.parseable("opencomputers:computer")))
                    .destroyTime(2.0f)
                    .explosionResistance(6.0f)
    );

    public static final DeferredItem<BlockItem> COMPUTER_ITEM = ITEMS.registerSimpleBlockItem(COMPUTER);

    public static void register(IEventBus modBus) {
        BLOCKS.register(modBus);
        ITEMS.register(modBus);
    }
}
