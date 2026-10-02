package de.xdarkixx.realspace.tile;

/** Vacuum pump that progressively evacuates its internal/connected gas volume. */
public class TileVacuumPump extends TileGasMachineBase {
    public TileVacuumPump() {
        capacity = 120000;
        maxTransfer = 2500;
    }

    @Override
    public double getGasVolumeM3() {
        return 0.15;
    }

    @Override
    public double getMaxPressurePa() {
        return 2500000.0;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (worldObj == null || worldObj.isRemote || !isMachineEnabled()) return;
        if (getEnergyStored() >= 35 && getGasMixture().getTotalMoles() > 0.0) {
            useEnergy(35);
            getGasMixture().scale(0.985);
            onGasChanged();
        }
    }
}
