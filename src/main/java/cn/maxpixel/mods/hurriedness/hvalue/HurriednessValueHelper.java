package cn.maxpixel.mods.hurriedness.hvalue;

import cn.maxpixel.mods.hurriedness.Config;
import cn.maxpixel.mods.hurriedness.registry.DataAttachmentRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class HurriednessValueHelper {
    public static void increase(ServerLevel level, ChunkAccess chunk, BlockPos pos) {
        if (!level.isInsideBuildHeight(pos)) return;
        var hv = chunk.getData(DataAttachmentRegistry.HURRIEDNESS_VALUE);
        int value = getValue(hv, chunk, pos);
        value += 1 + level.getRandom().nextInt(10);
        if (value > HurriednessValue.MAX_VALUE) {
            generateExplosions(level, pos.getCenter());
            clearValue(hv, chunk, pos);
            return;
        }
        level.getData(DataAttachmentRegistry.TICK_CANDIDATE).add(chunk.getPos().pack());
        setValue(hv, chunk, pos, value);
    }

    public static void increase(ServerLevel level, Entity entity) {
        var value = entity.getData(DataAttachmentRegistry.ENTITY_HURRIEDNESS_VALUE).intValue();
        value += 1 + level.getRandom().nextInt(10);
        if (value > HurriednessValue.MAX_VALUE) {
            generateExplosions(level, entity.position());
            entity.removeData(DataAttachmentRegistry.ENTITY_HURRIEDNESS_VALUE);
            return;
        }
        entity.setData(DataAttachmentRegistry.ENTITY_HURRIEDNESS_VALUE, (byte) value);
    }

    public static void decrease(ServerLevel level, ChunkAccess chunk, BlockPos pos) {
        if (!level.isInsideBuildHeight(pos)) return;
        if (!chunk.hasData(DataAttachmentRegistry.HURRIEDNESS_VALUE)) return;
        var hv = chunk.getData(DataAttachmentRegistry.HURRIEDNESS_VALUE);
        int value = getValue(hv, chunk, pos);
        value -= 1 + level.getRandom().nextInt(10);
        if (value < 0) value = 0;
        setValue(hv, chunk, pos, value);
    }

    public static void decrease(ServerLevel level, Entity entity) {
        if (!entity.hasData(DataAttachmentRegistry.ENTITY_HURRIEDNESS_VALUE)) return;
        var value = entity.getData(DataAttachmentRegistry.ENTITY_HURRIEDNESS_VALUE).intValue();
        value -= 1 + level.getRandom().nextInt(10);
        if (value <= 0) entity.removeData(DataAttachmentRegistry.ENTITY_HURRIEDNESS_VALUE);
        else entity.setData(DataAttachmentRegistry.ENTITY_HURRIEDNESS_VALUE, (byte) value);
    }

    public static void timesTwo(ServerLevel level, ChunkAccess chunk, BlockPos pos) {// aka double
        if (!level.isInsideBuildHeight(pos)) return;
        if (!chunk.hasData(DataAttachmentRegistry.HURRIEDNESS_VALUE)) return;
        var hv = chunk.getData(DataAttachmentRegistry.HURRIEDNESS_VALUE);
        int value = getValue(hv, chunk, pos);
        value *= 2;
        if (value < 0) value = 0;
        if (value > HurriednessValue.MAX_VALUE) {
            generateExplosions(level, pos.getCenter());
            clearValue(hv, chunk, pos);
            return;
        }
        level.getData(DataAttachmentRegistry.TICK_CANDIDATE).add(chunk.getPos().pack());// This is not needed here in theory, but is put here just in case
        setValue(hv, chunk, pos, value);
    }

    public static void timesTwo(ServerLevel level, Entity entity) {
        if (!entity.hasData(DataAttachmentRegistry.ENTITY_HURRIEDNESS_VALUE)) return;
        var value = entity.getData(DataAttachmentRegistry.ENTITY_HURRIEDNESS_VALUE).intValue();
        value *= 2;
        if (value <= 0) entity.removeData(DataAttachmentRegistry.ENTITY_HURRIEDNESS_VALUE);
        else if (value > HurriednessValue.MAX_VALUE) {
            generateExplosions(level, entity.position());
            entity.removeData(DataAttachmentRegistry.ENTITY_HURRIEDNESS_VALUE);
        } else entity.setData(DataAttachmentRegistry.ENTITY_HURRIEDNESS_VALUE, (byte) value);
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

    private static void generateExplosions(ServerLevel level, Vec3 pos) {
        boolean destructive = !Config.FORCE_DISABLE_BLOCK_DESTRUCTION.getAsBoolean() &&
                level.getGameRules().get(GameRules.MOB_GRIEFING);
        level.explode(null, pos.x, pos.y, pos.z, 5.f, destructive,
                destructive ? Level.ExplosionInteraction.BLOCK : Level.ExplosionInteraction.NONE);
    }

    public static int getValue(HurriednessValue hv, ChunkAccess chunk, BlockPos pos) {
        return hv.getValue(
                chunk.getSectionIndex(pos.getY()),
                SectionPos.sectionRelative(pos.getX()),
                SectionPos.sectionRelative(pos.getY()),
                SectionPos.sectionRelative(pos.getZ())
        );
    }

    private static void setValue(HurriednessValue hv, ChunkAccess chunk, BlockPos pos, int value) {
        hv.setValue(
                chunk.getSectionIndex(pos.getY()),
                SectionPos.sectionRelative(pos.getX()),
                SectionPos.sectionRelative(pos.getY()),
                SectionPos.sectionRelative(pos.getZ()),
                (byte) value
        );
    }

    private static void clearValue(HurriednessValue hv, ChunkAccess chunk, BlockPos pos) {
        hv.setValue(
                chunk.getSectionIndex(pos.getY()),
                SectionPos.sectionRelative(pos.getX()),
                SectionPos.sectionRelative(pos.getY()),
                SectionPos.sectionRelative(pos.getZ()),
                (byte) 0
        );
    }
}