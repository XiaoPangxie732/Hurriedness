package cn.maxpixel.mods.hurriedness.util;

import cn.maxpixel.mods.hurriedness.HurriednessMod;

public class I18nKey {
    public static final String KEY_CONFIG_TITLE = I18nKey.config("title");
    public static final String KEY_CONFIG_SECTION_COMMON = I18nKey.config("section.hurriedness.common.toml");
    public static final String KEY_CONFIG_SECTION_COMMON_TITLE = I18nKey.config("section.hurriedness.common.toml.title");

    public static String config(String key) {
        return HurriednessMod.MODID + ".configuration." + key;
    }

    public static String container(String key) {
        return HurriednessMod.MODID + ".container." + key;
    }

    public static String item(String key) {
        return HurriednessMod.MODID + ".item." + key;
    }
}