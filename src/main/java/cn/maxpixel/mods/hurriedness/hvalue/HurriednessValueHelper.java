package cn.maxpixel.mods.hurriedness.hvalue;

import cn.maxpixel.mods.hurriedness.Config;
import cn.maxpixel.mods.hurriedness.registry.DataAttachmentRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.gamerules.GameRules;

import java.util.Random;

public class HurriednessValueHelper {
    public static void increase(ServerLevel level, ChunkAccess chunk, BlockPos pos) {
        var hv = chunk.getData(DataAttachmentRegistry.HURRIEDNESS_VALUE);
        int value = hv.getValue(
                chunk.getSectionIndex(pos.getY()),
                SectionPos.sectionRelative(pos.getX()),
                SectionPos.sectionRelative(pos.getY()),
                SectionPos.sectionRelative(pos.getZ())
        );
        value += 1 + level.getRandom().nextInt(10);
        if (value > HurriednessValue.MAX_VALUE) {
            // generate explosions
            var center = pos.getCenter();
            boolean destructive = !Config.FORCE_DISABLE_BLOCK_DESTRUCTION.getAsBoolean() &&
                    level.getGameRules().get(GameRules.MOB_GRIEFING);
            level.explode(null, center.x, center.y, center.z, 5.f, destructive,
                    destructive ? Level.ExplosionInteraction.BLOCK : Level.ExplosionInteraction.NONE);
            hv.setValue(
                    chunk.getSectionIndex(pos.getY()),
                    SectionPos.sectionRelative(pos.getX()),
                    SectionPos.sectionRelative(pos.getY()),
                    SectionPos.sectionRelative(pos.getZ()),
                    (byte) 0
            );
            return;
        }
        level.getData(DataAttachmentRegistry.TICK_CANDIDATE).add(chunk.getPos().pack());
        hv.setValue(
                chunk.getSectionIndex(pos.getY()),
                SectionPos.sectionRelative(pos.getX()),
                SectionPos.sectionRelative(pos.getY()),
                SectionPos.sectionRelative(pos.getZ()),
                (byte) value
        );
    }

    public static void decrease(ServerLevel level, ChunkAccess chunk, BlockPos pos) {
        var hv = chunk.getData(DataAttachmentRegistry.HURRIEDNESS_VALUE);
        int value = hv.getValue(
                chunk.getSectionIndex(pos.getY()),
                SectionPos.sectionRelative(pos.getX()),
                SectionPos.sectionRelative(pos.getY()),
                SectionPos.sectionRelative(pos.getZ())
        );
        value -= 1 + level.getRandom().nextInt(10);
        if (value < 0) value = 0;
        hv.setValue(
                chunk.getSectionIndex(pos.getY()),
                SectionPos.sectionRelative(pos.getX()),
                SectionPos.sectionRelative(pos.getY()),
                SectionPos.sectionRelative(pos.getZ()),
                (byte) value
        );
    }

    public static void decreaseAll(ServerLevel level, ChunkAccess chunk) {
        var hv = chunk.getData(DataAttachmentRegistry.HURRIEDNESS_VALUE);
        for (int s = 0; s < hv.getSectionCount(); ++s) {
            if (hv.getCnt(s) > 0) {
                for (int x = 0; x < SectionPos.SECTION_SIZE; ++x) {
                    for (int y = 0; y < SectionPos.SECTION_SIZE; ++y) {
                        for (int z = 0; z < SectionPos.SECTION_SIZE; ++z) {
                            int value = hv.getValue(s, x, y, z);
                            value -= 1 + level.getRandom().nextInt(10);
                            if (value < 0) value = 0;
                            hv.setValue(s, x, y, z, (byte) value);
                        }
                    }
                }
            }
        }
    }
}