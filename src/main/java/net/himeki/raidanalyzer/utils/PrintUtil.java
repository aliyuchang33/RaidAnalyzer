package net.himeki.raidanalyzer.utils;


import net.minecraft.util.math.BlockPos;

public class PrintUtil {
    static public String pos2ShortString(BlockPos pos) {
        return "" + pos.getX() + ", " + pos.getY() + ", " + pos.getZ();

    }

}
