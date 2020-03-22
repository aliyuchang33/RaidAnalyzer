package net.himeki.raidanalyzer.mixin;

import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.Dynamic;
import net.himeki.raidanalyzer.RaidAnalyzer;
import net.himeki.raidanalyzer.record.POIAddRecord;
import net.himeki.raidanalyzer.record.POIRemoveRecord;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.poi.PointOfInterestSet;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestType;
import net.minecraft.world.storage.SerializingRegionBasedStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.util.function.BiFunction;
import java.util.function.Function;

@Mixin(PointOfInterestStorage.class)
public abstract class POIStorageMixin extends SerializingRegionBasedStorage<PointOfInterestSet> {
    public POIStorageMixin(File directory, BiFunction<Runnable, Dynamic<?>, PointOfInterestSet> deserializer, Function<Runnable, PointOfInterestSet> factory, DataFixer dataFixer, DataFixTypes dataFixType) {
        super(directory, deserializer, factory, dataFixer, dataFixType);
    }
    // Wrong inject point, need to be fixed.

    @Inject(method = "add", at = @At("RETURN"))
    private void onAddPoi(final BlockPos pos, final PointOfInterestType type, CallbackInfo ci) {
        RaidAnalyzer.INSTANCE.getRecordManager().addRecord(new POIAddRecord(pos, type));
    }

    @Inject(method = "remove", at = @At("RETURN"))
    private void onRemovePoi(BlockPos pos, CallbackInfo ci) {
        RaidAnalyzer.INSTANCE.getRecordManager().addRecord(new POIRemoveRecord(pos));
    }

}
