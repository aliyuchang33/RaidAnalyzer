package net.himeki.raidanalyzer.mixin;

import net.himeki.raidanalyzer.RaidAnalyzer;
import net.himeki.raidanalyzer.record.RaidSpawnRecord;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.raid.Raid;
import net.minecraft.village.raid.RaidManager;
import net.minecraft.world.PersistentState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(RaidManager.class)
public abstract class RaidManagerMixin extends PersistentState {


    public RaidManagerMixin(String key) {
        super(key);
    }

    @Inject(method = "startRaid", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/village/raid/RaidManager;getOrCreateRaid(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/village/raid/Raid;"), locals = LocalCapture.CAPTURE_FAILHARD)

    private void onRaidCreated(ServerPlayerEntity player, CallbackInfoReturnable<Raid> cir, BlockPos blockPos, BlockPos blockPos4, Raid raid) {
        RaidAnalyzer.INSTANCE.getRecordManager().addRecord(new RaidSpawnRecord(raid.getRaidId(), raid.getCenter()));
    }

}