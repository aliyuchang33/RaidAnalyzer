package net.himeki.raidanalyzer.mixin;

import net.himeki.raidanalyzer.RaidAnalyzer;
import net.himeki.raidanalyzer.record.RaidMoveCenterRecord;
import net.himeki.raidanalyzer.record.RaiderEntitiesSpawnRecord;
import net.himeki.raidanalyzer.record.RaiderSpawnFailRecord;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.village.raid.Raid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Comparator;
import java.util.stream.Stream;


@Mixin(Raid.class)
public abstract class RaidMixin {


    @Shadow
    private BlockPos center;

    @Shadow
    private ServerWorld world;

    @Shadow
    public abstract int getRaidId();

    @Shadow
    public abstract void setCenter(BlockPos blockPos);

    @Shadow
    private int wavesSpawned;

    private void moveRaidCenter() {
        BlockPos before = this.center;
        Stream<ChunkSectionPos> stream = ChunkSectionPos.stream(ChunkSectionPos.from(this.center), 2);
        ServerWorld var10001 = this.world;
        var10001.getClass();
        stream.filter(var10001::isNearOccupiedPointOfInterest).map(ChunkSectionPos::getCenterPos).min(Comparator.comparingDouble((blockPos) -> {
            return blockPos.getSquaredDistance(this.center);
        })).ifPresent(this::setCenter);
        RaidAnalyzer.INSTANCE.getRecordManager().addRecord(new RaidMoveCenterRecord(getRaidId(), before, this.center));
    }

    @Inject(method = "spawnNextWave", at = @At(value = "RETURN"))
    private void onRaiderSpawned(final BlockPos pos, CallbackInfo ci) {
        RaidAnalyzer.INSTANCE.getRecordManager().addRecord(new RaiderEntitiesSpawnRecord(getRaidId(), pos, wavesSpawned));
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/village/raid/Raid;invalidate()V",ordinal = 3))
    private void onRaiderSpawnFailed(CallbackInfo ci) {
        RaidAnalyzer.INSTANCE.getRecordManager().addRecord(new RaiderSpawnFailRecord(getRaidId(), center, wavesSpawned + 1));
    }
}
