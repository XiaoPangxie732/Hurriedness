package cn.maxpixel.mods.hurriedness.data;

import cn.maxpixel.mods.hurriedness.HurriednessMod;
import cn.maxpixel.mods.hurriedness.registry.EntityTypeRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;

import java.util.concurrent.CompletableFuture;

public class ModEntityTypeTagsProvider extends EntityTypeTagsProvider {
    public ModEntityTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, HurriednessMod.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        this.tag(EntityTypeTags.IMPACT_PROJECTILES)
                .add(EntityTypeRegistry.HURRIED_EGG.get());
    }
}
