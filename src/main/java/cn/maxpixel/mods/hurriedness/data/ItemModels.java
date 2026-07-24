package cn.maxpixel.mods.hurriedness.data;

import cn.maxpixel.mods.hurriedness.HurriednessMod;
import cn.maxpixel.mods.hurriedness.registry.ItemRegistry;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.renderer.item.CuboidItemModelWrapper;
import net.minecraft.data.PackOutput;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Optional;

public class ItemModels extends ModelProvider {
    public ItemModels(PackOutput output) {
        super(output, HurriednessMod.MODID);
    }

    @Override
    protected void registerModels(@NonNull BlockModelGenerators blockModels, @NonNull ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(ItemRegistry.ALIEN_HAND_HURRY.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.ALIEN_HAND_NOT_HURRY.get(), ModelTemplates.FLAT_ITEM);
    }
}
