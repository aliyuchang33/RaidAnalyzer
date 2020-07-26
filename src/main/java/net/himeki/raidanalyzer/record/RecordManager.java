package net.himeki.raidanalyzer.record;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.server.ServerStartCallback;
import net.fabricmc.fabric.api.event.server.ServerStopCallback;
import net.himeki.raidanalyzer.RaidAnalyzer;
import net.himeki.raidanalyzer.config.ConfigHolder;
import net.himeki.raidanalyzer.session.RecordSession;
import net.minecraft.network.MessageType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;


import java.time.LocalDate;
import java.util.ArrayList;


public class RecordManager {
    private RecordSession currentSession = null;
    private ConfigHolder configHolder;
    private MinecraftServer currentServer = null;
    private ArrayList<RecordSession> sessions = new ArrayList<>();

    public RecordManager(RaidAnalyzer mod) {
        configHolder = mod.getConfigHolder();
        ServerStartCallback.EVENT.register((server -> {
            startNewSession(null, server);
        }));
        ServerStopCallback.EVENT.register((server -> {
            stopSession();
        }));
    }

    @Environment(EnvType.CLIENT)
    public void addRecord(RaidRecord record) {
        record.setTick(currentServer.getTicks());
        if (record instanceof RaidSpawnRecord)
            raidSpawnAction((RaidSpawnRecord) record);
        else if (record instanceof RaiderEntitiesSpawnRecord)
            raiderEntitiesSpawnAction((RaiderEntitiesSpawnRecord) record);
        else if (record instanceof RaidMoveCenterRecord)
            raidMoveCenterAction((RaidMoveCenterRecord) record);
        else if (record instanceof RaiderSpawnFailRecord)
            raiderSpawnFailedAction((RaiderSpawnFailRecord) record);
        if (currentSession != null) {
            currentSession.addRecord(record);
        }
    }

    public void startNewSession(String alias, MinecraftServer server) {
        if (currentSession == null) {
            RecordSession session = new RecordSession(LocalDate.now(), alias);
            currentServer = server;
            currentSession = session;
        } else {        // Current session in progress

        }

    }

    public void stopSession() {
        if (currentSession != null) {
            sessions.add(currentSession);
            currentSession = null;
            currentServer = null;
        } else {      // No current session

        }
    }

    public void saveSession() {

    }

    @Environment(EnvType.CLIENT)
    private void raidSpawnAction(RaidSpawnRecord record) {
        if (configHolder.printRaidSpawn)
            for (ServerPlayerEntity player : currentServer.getPlayerManager().getPlayerList()) {
                player.sendMessage(new LiteralText(record.printTime()).append(new TranslatableText("event.raidanalyzer.raid_spawn", record.getCenter().toShortString(), record.getRaidId())).formatted(Formatting.GREEN), false);
            }


    }

    @Environment(EnvType.CLIENT)
    private void raiderEntitiesSpawnAction(RaiderEntitiesSpawnRecord record) {
        if (configHolder.printRaidersEntitiesSpawn)
            for (ServerPlayerEntity player : currentServer.getPlayerManager().getPlayerList()) {
                player.sendMessage(new LiteralText(record.printTime()).append(new TranslatableText("event.raidanalyzer.raiders_spawn", record.getWave(), record.getRaidId(), record.getSpawnPoint().toShortString())).formatted(Formatting.GREEN), false);
            }

    }

    @Environment(EnvType.CLIENT)
    private void raidMoveCenterAction(RaidMoveCenterRecord record) {
        if (configHolder.printRaidMoveCenter)
            for (ServerPlayerEntity player : currentServer.getPlayerManager().getPlayerList()) {
                player.sendMessage(new LiteralText(record.printTime()).append(new TranslatableText("event.raidanalyzer.raid_move_center", record.getRaidId(), record.getFromPos().toShortString(), record.getToPos().toShortString())).formatted(Formatting.GREEN), false);
            }
    }

    private void raiderSpawnFailedAction(RaiderSpawnFailRecord record) {
        if (configHolder.printRaiderSpawnFailed)
            for (ServerPlayerEntity player : currentServer.getPlayerManager().getPlayerList()) {
                player.sendMessage(new LiteralText(record.printTime()).append(new TranslatableText("event.raidanalyzer.raider_spawn_failed", record.getWave(), record.getRaidId(), record.getCenter().getX(), record.getCenter().getZ(), record.getCenter().getX() + 4, record.getCenter().getZ() + 4)).formatted(Formatting.RED), false);
            }
    }
}
