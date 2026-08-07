package cn.maxpixel.mods.hurriedness.hvalue;

import cn.maxpixel.mods.hurriedness.HurriednessMod;
import cn.maxpixel.mods.hurriedness.registry.DataAttachmentRegistry;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.ChunkPos;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

@EventBusSubscriber(modid = HurriednessMod.MODID)
public class TickCandidate {
    private final LongOpenHashSet candidates = new LongOpenHashSet();

    public void tick(ServerLevel level) {
        ProfilerFiller profiler = Profiler.get();
        profiler.push("tickHurriednessValue");
        var it = candidates.longIterator();
        boolean decrease = level.getGameTime() % 20 == 0;
        while (it.hasNext()) {
            long cp = it.nextLong();
            var chunk = level.getChunk(ChunkPos.getX(cp), ChunkPos.getZ(cp));
            if (!chunk.hasData(DataAttachmentRegistry.HURRIEDNESS_VALUE)) {
                it.remove();
                continue;
            }
            var hv = chunk.getData(DataAttachmentRegistry.HURRIEDNESS_VALUE);
            if (decrease) {
                HurriednessValueHelper.decreaseAll(level, chunk);
            }
            if (hv.needSync()) {
                chunk.syncData(DataAttachmentRegistry.HURRIEDNESS_VALUE);
                hv.synced();
            }
            if (hv.getCntTotal() <= 0) {
                it.remove();
            }
        }
        profiler.pop();
    }

    public void add(long chunkPos) {
        candidates.add(chunkPos);
    }

    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Pre event) {
        var level = event.getLevel();
        if (!level.isClientSide() && level.hasData(DataAttachmentRegistry.TICK_CANDIDATE)) {
            var tc = level.getData(DataAttachmentRegistry.TICK_CANDIDATE);
            tc.tick((ServerLevel) level);
        }
    }
}