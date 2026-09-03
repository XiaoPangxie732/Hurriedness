package cn.maxpixel.mods.hurriedness.registry;

import cn.maxpixel.mods.hurriedness.HurriednessMod;
import cn.maxpixel.mods.hurriedness.item.component.RestrictedInComponent;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class DataComponentRegistry {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, HurriednessMod.MODID);

    public static final Supplier<DataComponentType<RestrictedInComponent>> RESTRICTED_IN = DATA_COMPONENTS.registerComponentType(
            "restricted_in",
            builder -> builder
                    .persistent(RestrictedInComponent.CODEC)
                    .networkSynchronized(RestrictedInComponent.STREAM_CODEC)
                    .cacheEncoding()
    );
}