package cn.maxpixel.mods.hurriedness.client;

import cn.maxpixel.mods.hurriedness.Config;
import cn.maxpixel.mods.hurriedness.HurriednessMod;
import cn.maxpixel.mods.hurriedness.client.renderer.debug.HurriednessValueDebugRenderer;
import cn.maxpixel.mods.hurriedness.registry.EntityTypeRegistry;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ExtractLevelRenderStateEvent;
import net.neoforged.neoforge.client.event.RegisterDebugRenderersEvent;

@EventBusSubscriber(modid = HurriednessMod.MODID, value = Dist.CLIENT)
public class ClientRegistries {
    @SubscribeEvent
    public static void onRegisterDebugRenderers(RegisterDebugRenderersEvent event) {
         if (Config.ENABLE_HURRIEDNESS_VALUE_DEBUG_RENDERER.getAsBoolean()) {
             event.register(HurriednessValueDebugRenderer::new);
         }
    }

    private static boolean lastHurriednessValueDebugRendererEnabled;
    @SubscribeEvent
    public static void onExtractLevelRenderState(ExtractLevelRenderStateEvent event) {
        if (Config.ENABLE_HURRIEDNESS_VALUE_DEBUG_RENDERER.getAsBoolean() != lastHurriednessValueDebugRendererEnabled) {
            lastHurriednessValueDebugRendererEnabled = Config.ENABLE_HURRIEDNESS_VALUE_DEBUG_RENDERER.getAsBoolean();
            event.getLevelRenderer().debugRenderer.refreshRendererList();
        }
    }

    @SubscribeEvent
    public static void onRegisterEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityTypeRegistry.HURRIED_EGG.get(), ThrownItemRenderer::new);
    }
}