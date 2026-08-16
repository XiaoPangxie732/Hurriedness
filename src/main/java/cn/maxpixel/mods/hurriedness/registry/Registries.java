package cn.maxpixel.mods.hurriedness.registry;

import cn.maxpixel.mods.hurriedness.HurriednessMod;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.level.block.DispenserBlock;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@EventBusSubscriber(modid = HurriednessMod.MODID)
public class Registries {
    public static void register(IEventBus modBus) {
        BlockRegistry.BLOCKS.register(modBus);
        ItemRegistry.ITEMS.register(modBus);
        EntityTypeRegistry.ENTITY_TYPES.register(modBus);
//        CreativeTabRegistry.CREATIVE_MODE_TABS.register(modBus);
        DataAttachmentRegistry.ATTACHMENT_TYPES.register(modBus);
    }

    @SubscribeEvent
    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        CreativeTabRegistry.addCreative(event);
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {// Dispenser Behaviors
            DispenserBlock.registerProjectileBehavior(ItemRegistry.HURRIED_EGG);
        });
    }
}