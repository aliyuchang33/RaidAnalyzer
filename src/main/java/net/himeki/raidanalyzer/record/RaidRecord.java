package net.himeki.raidanalyzer.record;


public interface RaidRecord {
    long getTick();
    void setTick(int tick);

    default String printTime() {
        return "[" + getTick() + "] ";
    }

}
