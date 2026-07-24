package cn.maxpixel.mods.hurriedness.data.lang;

import cn.maxpixel.mods.hurriedness.Config;
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
//        add(Config.KEY_EXHIBITION_MODE, "Exhibition Mode");

        addItem(ItemRegistry.ALIEN_HAND_HURRY, "Alien hand");
        addItem(ItemRegistry.ALIEN_HAND_NOT_HURRY, "Inverted alien hand");
    }
}