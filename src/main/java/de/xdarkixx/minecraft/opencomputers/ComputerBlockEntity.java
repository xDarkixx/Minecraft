package de.xdarkixx.minecraft.opencomputers;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

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
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        computer.save(output);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        computer.load(input);
    }
}
