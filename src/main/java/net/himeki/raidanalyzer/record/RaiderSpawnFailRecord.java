package net.himeki.raidanalyzer.record;

import net.minecraft.util.math.BlockPos;

public class RaiderSpawnFailRecord implements RaidRecord {
    private long tick;
    private int raidId;
    private BlockPos center;
    private int wave;

    public RaiderSpawnFailRecord(int raidId, BlockPos center, int wave) {
        this.raidId = raidId;
        this.center = center;
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

    public BlockPos getCenter() {
        return center;
    }

    public int getRaidId() {
        return raidId;
    }

    public int getWave() {
        return wave;
    }
}
