package net.himeki.raidanalyzer.record;

import jdk.nashorn.internal.ir.Block;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.poi.PointOfInterestType;

public class POIAddRecord implements RaidRecord {
    private int tick;
    private BlockPos pos;
    private PointOfInterestType type;

    public POIAddRecord(BlockPos pos, PointOfInterestType type) {
        this.pos = pos;
        this.type = type;
    }


    public BlockPos getPos() {
        return pos;
    }

    public PointOfInterestType getType() {
        return type;
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
