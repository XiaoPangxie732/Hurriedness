package cn.maxpixel.mods.hurriedness.client.renderer.debug;

import cn.maxpixel.mods.hurriedness.hvalue.HurriednessValueHelper;
import cn.maxpixel.mods.hurriedness.registry.DataAttachmentRegistry;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.gizmos.Gizmos;
import net.minecraft.gizmos.TextGizmo;
import net.minecraft.util.ARGB;
import net.minecraft.util.debug.DebugValueAccess;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class HurriednessValueDebugRenderer implements DebugRenderer.SimpleDebugRenderer {
    private final Minecraft mc;

    public HurriednessValueDebugRenderer(Minecraft mc) {
        this.mc = mc;
    }

    @Override
    public void emitGizmos(double camX, double camY, double camZ, DebugValueAccess debugValues, Frustum frustum, float partialTicks) {
        Level level = mc.level;
        BlockPos playerPos = BlockPos.containing(camX, camY, camZ);
        for (BlockPos blockPos : BlockPos.betweenClosed(playerPos.offset(-10, -10, -10), playerPos.offset(10, 10, 10))) {
            var chunk = level.getChunk(blockPos);
            if (!chunk.hasData(DataAttachmentRegistry.HURRIEDNESS_VALUE)) continue;
            var hv = chunk.getData(DataAttachmentRegistry.HURRIEDNESS_VALUE);
            int value = HurriednessValueHelper.getValue(hv, chunk, blockPos);
            if (value != 0) {
                Gizmos.billboardTextOverBlock(
                        String.valueOf(value), blockPos, 1, 0xFFFF1111, .32f
                );
            }
        }
        for (Entity entity : level.getEntities(null, AABB.ofSize(playerPos.getCenter(), 21, 21, 21))) {
            if (entity.hasData(DataAttachmentRegistry.ENTITY_HURRIEDNESS_VALUE)) {
                int value = entity.getData(DataAttachmentRegistry.ENTITY_HURRIEDNESS_VALUE).intValue();
                if (value != 0) {
                    Gizmos.billboardTextOverMob(entity, 0, String.valueOf(value), 0xFFFF1111, .32f);
                }
            }
        }
    }
}