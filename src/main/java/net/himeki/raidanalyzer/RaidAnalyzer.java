package net.himeki.raidanalyzer;

import net.fabricmc.api.ModInitializer;
import net.himeki.raidanalyzer.config.ConfigHolder;
import net.himeki.raidanalyzer.record.RecordManager;

public class RaidAnalyzer implements ModInitializer {
    public static RaidAnalyzer INSTANCE;
    private ConfigHolder configHolder = new ConfigHolder(this);
    private RecordManager recordManager = new RecordManager(this);

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
        INSTANCE = this;

    }

    public RecordManager getRecordManager() {
        return recordManager;
    }
    public ConfigHolder getConfigHolder()
    {
        return configHolder;
    }

}
