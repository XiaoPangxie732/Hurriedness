package cn.maxpixel.mods.hurriedness.data.lang;

import cn.maxpixel.mods.hurriedness.Config;
import cn.maxpixel.mods.hurriedness.block.entity.HurriedAnchorBlockEntity;
import cn.maxpixel.mods.hurriedness.item.AlienHandItem;
import cn.maxpixel.mods.hurriedness.registry.BlockRegistry;
import cn.maxpixel.mods.hurriedness.registry.ItemRegistry;
import cn.maxpixel.mods.hurriedness.util.I18nKey;
import net.minecraft.data.PackOutput;

public class AmericanEnglishLanguageProvider extends CustomLanguageProvider {
    public AmericanEnglishLanguageProvider(PackOutput output) {
        super(output, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(I18nKey.KEY_CONFIG_TITLE, "Hurriedness Configs");
        add(I18nKey.KEY_CONFIG_SECTION_COMMON, "Hurriedness Configs");
        add(I18nKey.KEY_CONFIG_SECTION_COMMON_TITLE, "Hurriedness Configs");
        add(Config.KEY_FORCE_DISABLE_BLOCK_DESTRUCTION, "Force Disable Block Destruction");
        add(Config.KEY_FORCE_DISABLE_BLOCK_DESTRUCTION_TOOLTIP, "Disable block destruction of explosions created by this mod, disregarding the mob_griefing game rule");
        add(Config.KEY_ENABLE_HURRIEDNESS_VALUE_DEBUG_RENDERER, "Enable hurriedness value debug renderer");
        add(Config.KEY_ENABLE_HURRIEDNESS_VALUE_DEBUG_RENDERER_TOOLTIP, "Enable hurriedness value debug renderer, which will show the hurriedness value of nearby blocks & entities");
//        add(Config.KEY_EXHIBITION_MODE, "Exhibition Mode");

        addBlock(BlockRegistry.HURRIED_ANCHOR, "Hurried anchor");
        add(HurriedAnchorBlockEntity.DEFAULT_NAME_KEY, "Hurried anchor");

        addItem(ItemRegistry.ALIEN_HAND_HURRY, "Alien hand");
        add(AlienHandItem.OUTSIDE_RANGE_KEY, "You are outside the usable range");

        addItem(ItemRegistry.ALIEN_HAND_NOT_HURRY, "Inverted alien hand");
        addItem(ItemRegistry.HURRIED_EGG, "Hurried egg");
    }
}