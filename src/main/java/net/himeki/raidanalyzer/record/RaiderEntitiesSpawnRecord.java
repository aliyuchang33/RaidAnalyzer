package net.himeki.raidanalyzer.record;

import net.minecraft.util.math.BlockPos;

public class RaiderEntitiesSpawnRecord implements RaidRecord {
    private long tick;
    private int raidId;
    private BlockPos spawnPoint;
    private int wave;

    public RaiderEntitiesSpawnRecord(int raidId, BlockPos pos, int wave) {
        this.raidId = raidId;
        this.spawnPoint = pos;
        this.wave = wave;
    }

    @Override
    public long getTick() {
        return tick;
    }

    @Override
    public void setTick(int tick) {
        this.tick = tick;

    }


    public int getRaidId() {
        return raidId;
    }


    public BlockPos getSpawnPoint() {
        return spawnPoint;
    }

    public int getWave() {
        return wave;
    }
}
