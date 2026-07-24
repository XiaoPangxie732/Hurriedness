package cn.maxpixel.mods.hurriedness.data;

import cn.maxpixel.mods.hurriedness.HurriednessMod;
import cn.maxpixel.mods.hurriedness.data.lang.AmericanEnglishLanguageProvider;
import cn.maxpixel.mods.hurriedness.data.lang.SimplifiedChineseLanguageProvider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = HurriednessMod.MODID, value = Dist.CLIENT)
public class EntryPoint {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        event.createProvider(AmericanEnglishLanguageProvider::new);
        event.createProvider(SimplifiedChineseLanguageProvider::new);

        event.createProvider(ItemModels::new);
    }
}