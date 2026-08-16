package cn.maxpixel.mods.hurriedness.registry;

import cn.maxpixel.mods.hurriedness.HurriednessMod;
import cn.maxpixel.mods.hurriedness.entity.ThrownHurriedEgg;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class EntityTypeRegistry {
    public static final DeferredRegister.Entities ENTITY_TYPES = DeferredRegister.createEntities(HurriednessMod.MODID);

    public static final Supplier<EntityType<ThrownHurriedEgg>> HURRIED_EGG = ENTITY_TYPES.registerEntityType(
            "hurried_egg", ThrownHurriedEgg::new, MobCategory.MISC,
            builder -> builder.noLootTable().sized(0.25F, 0.25F)
                    .clientTrackingRange(4).updateInterval(10));
}
