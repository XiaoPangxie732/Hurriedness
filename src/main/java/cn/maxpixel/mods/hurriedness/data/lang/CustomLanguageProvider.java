package cn.maxpixel.mods.hurriedness.data.lang;

import cn.maxpixel.mods.hurriedness.HurriednessMod;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public abstract class CustomLanguageProvider extends LanguageProvider {
    public CustomLanguageProvider(PackOutput output, String locale) {
        super(output, HurriednessMod.MODID, locale);
    }
}