package cn.maxpixel.mods.hurriedness.hvalue;

import cn.maxpixel.mods.hurriedness.HurriednessMod;
import cn.maxpixel.mods.hurriedness.registry.DataAttachmentRegistry;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber(modid = HurriednessMod.MODID)
public class EntityHurriednessValueTicker {
    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Pre event) {
        var entity = event.getEntity();
        if (entity.level() instanceof ServerLevel level &&
                entity.hasData(DataAttachmentRegistry.ENTITY_HURRIEDNESS_VALUE) &&
                level.getGameTime() % 20 == 0) {
            HurriednessValueHelper.decrease(level, entity);
        }
    }
}