package net.himeki.raidanalyzer.config;

import net.himeki.raidanalyzer.RaidAnalyzer;

public class ConfigHolder {
    private RaidAnalyzer mod;
    public boolean printRaidSpawn = true;
    public boolean printRaidersEntitiesSpawn = true;
    public boolean printRaidMoveCenter = true;
    public boolean printPoiAdd = true;
    public boolean printPoiRemove = true;
    public boolean printRaiderSpawnFailed = true;

    public ConfigHolder(RaidAnalyzer mod) {
        this.mod = mod;
    }
}
