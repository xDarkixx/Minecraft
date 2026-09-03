package de.xdarkixx.minecraft.opencomputers;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/** Server-authoritative persistent computer state. */
public final class ComputerBlockEntity extends BlockEntity {
    private final ComputerState computer = new ComputerState();

    public ComputerBlockEntity(BlockPos pos, BlockState state) {
        super(OCRegistries.COMPUTER_ENTITY.get(), pos, state);
    }

    public ComputerState computer() {
        return computer;
    }

    public static void serverTick(net.minecraft.world.level.Level level, BlockPos pos, BlockState state, ComputerBlockEntity computer) {
        if (!computer.computer().isRunning()) {
            return;
        }
        computer.computer().tick();
        computer.setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        CompoundTag state = new CompoundTag();
        computer.save(state);
        tag.put("ComputerState", state);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        tag.getCompound("ComputerState").ifPresent(computer::load);
    }
}
