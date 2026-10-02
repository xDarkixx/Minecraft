package de.xdarkixx.realspace.tile;

/** Active gas-loop radiator. Cools a connected gas volume while consuming electrical power. */
public class TileRadiator extends TileGasMachineBase {
    public TileRadiator() {
        capacity = 80000;
        maxTransfer = 1600;
    }

    @Override
    public double getGasVolumeM3() {
        return 0.20;
    }

    @Override
    public double getMaxPressurePa() {
        return 3000000.0;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (worldObj == null || worldObj.isRemote || !isMachineEnabled()) return;
        double t = getGasMixture().getTemperatureK();
        if (t > 210.0 && getEnergyStored() >= 20) {
            useEnergy(20);
            getGasMixture().setTemperatureK(t - Math.min(2.0, (t - 210.0) * 0.04));
            onGasChanged();
        }
    }
}
