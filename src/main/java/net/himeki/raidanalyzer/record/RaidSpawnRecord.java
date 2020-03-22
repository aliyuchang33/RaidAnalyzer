package net.himeki.raidanalyzer.record;

import net.minecraft.util.math.BlockPos;

public class RaidSpawnRecord implements RaidRecord {
    private long tick;
    private int raidId;
    private BlockPos center;


    public RaidSpawnRecord(int raidId, BlockPos pos) {
        this.raidId = raidId;
        this.center = pos;

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


    public BlockPos getCenter() {
        return center;
    }
}
