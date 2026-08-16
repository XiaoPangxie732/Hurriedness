package cn.maxpixel.mods.hurriedness.registry;

import cn.maxpixel.mods.hurriedness.HurriednessMod;
import cn.maxpixel.mods.hurriedness.item.AlienHandItem;
import cn.maxpixel.mods.hurriedness.item.HurriedEggItem;
import cn.maxpixel.mods.hurriedness.item.InvertedAlienHandItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemRegistry {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(HurriednessMod.MODID);

    public static final DeferredItem<BlockItem> HURRIED_ANCHOR = ITEMS.registerSimpleBlockItem(BlockRegistry.HURRIED_ANCHOR);

    public static final DeferredItem<AlienHandItem> ALIEN_HAND_HURRY = ITEMS.registerItem("alien_hand_hurry", AlienHandItem::new);
    public static final DeferredItem<InvertedAlienHandItem> ALIEN_HAND_NOT_HURRY = ITEMS.registerItem("alien_hand_not_hurry", InvertedAlienHandItem::new);
    public static final DeferredItem<HurriedEggItem> HURRIED_EGG = ITEMS.registerItem("hurried_egg", HurriedEggItem::new);
}