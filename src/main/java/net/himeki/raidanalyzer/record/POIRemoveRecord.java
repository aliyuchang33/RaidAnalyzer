package net.himeki.raidanalyzer.record;

import net.minecraft.util.math.BlockPos;

public class POIRemoveRecord implements RaidRecord {
    private int tick;
    private BlockPos pos;

    public POIRemoveRecord(BlockPos pos) {
        this.pos = pos;
    }

    public BlockPos getPos() {
        return pos;
    }

    @Override
    public long getTick() {
        return tick;
    }

    @Override
    public void setTick(int tick) {
        this.tick = tick;
    }
}
