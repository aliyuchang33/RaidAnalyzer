package net.himeki.raidanalyzer.mixin;

import net.himeki.raidanalyzer.RaidAnalyzer;
import net.himeki.raidanalyzer.record.RaidMoveCenterRecord;
import net.himeki.raidanalyzer.record.RaiderEntitiesSpawnRecord;
import net.himeki.raidanalyzer.record.RaiderSpawnFailRecord;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.raid.Raid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(Raid.class)
public abstract class RaidMixin {


    @Shadow
    private BlockPos center;

    @Shadow
    public abstract int getRaidId();

    @Shadow
    private int wavesSpawned;

    private BlockPos before;

    @Inject(method = "moveRaidCenter", at = @At(value = "HEAD"))
    private void onStartMoveCenter(CallbackInfo ci) {
        before = this.center;
    }

    @Inject(method = "moveRaidCenter", at = @At(value = "TAIL"))
    private void onFinishMoveCenter(CallbackInfo ci) {
        RaidAnalyzer.INSTANCE.getRecordManager().addRecord(new RaidMoveCenterRecord(getRaidId(), before, this.center));
    }

    @Inject(method = "spawnNextWave", at = @At(value = "RETURN"))
    private void onRaiderSpawned(final BlockPos pos, CallbackInfo ci) {
        RaidAnalyzer.INSTANCE.getRecordManager().addRecord(new RaiderEntitiesSpawnRecord(getRaidId(), pos, wavesSpawned));
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/village/raid/Raid;invalidate()V", ordinal = 3))
    private void onRaiderSpawnFailed(CallbackInfo ci) {
        RaidAnalyzer.INSTANCE.getRecordManager().addRecord(new RaiderSpawnFailRecord(getRaidId(), center, wavesSpawned + 1));
    }
}
