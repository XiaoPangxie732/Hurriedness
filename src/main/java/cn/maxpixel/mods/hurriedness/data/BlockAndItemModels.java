package cn.maxpixel.mods.hurriedness.data;

import cn.maxpixel.mods.hurriedness.HurriednessMod;
import cn.maxpixel.mods.hurriedness.registry.BlockRegistry;
import cn.maxpixel.mods.hurriedness.registry.ItemRegistry;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;
import org.jspecify.annotations.NonNull;

public class BlockAndItemModels extends ModelProvider {
    public BlockAndItemModels(PackOutput output) {
        super(output, HurriednessMod.MODID);
    }

    @Override
    protected void registerModels(@NonNull BlockModelGenerators blockModels, @NonNull ItemModelGenerators itemModels) {
        blockModels.createTrivialCube(BlockRegistry.HURRIED_ANCHOR.get());

        itemModels.generateFlatItem(ItemRegistry.ALIEN_HAND_HURRY.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.ALIEN_HAND_NOT_HURRY.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.HURRIED_EGG.get(), ModelTemplates.FLAT_ITEM);
    }
}
