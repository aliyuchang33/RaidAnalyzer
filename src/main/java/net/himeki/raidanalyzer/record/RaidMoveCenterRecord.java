package net.himeki.raidanalyzer.record;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class RaidMoveCenterRecord implements RaidRecord {
    private long tick;
    private int raidId;
    private BlockPos fromPos;
    private BlockPos toPos;


    public RaidMoveCenterRecord(int raidId, BlockPos from, BlockPos to) {
        this.raidId = raidId;
        this.fromPos = from;
        this.toPos = to;
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


    public BlockPos getFromPos() {
        return fromPos;
    }

    public BlockPos getToPos() {
        return toPos;
    }


}
