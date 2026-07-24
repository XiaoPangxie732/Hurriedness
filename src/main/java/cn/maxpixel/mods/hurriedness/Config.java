package cn.maxpixel.mods.hurriedness;

import java.util.List;

import cn.maxpixel.mods.hurriedness.util.I18nKey;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final String KEY_FORCE_DISABLE_BLOCK_DESTRUCTION = I18nKey.config("forceDisableBlockDestruction");
    public static final String KEY_FORCE_DISABLE_BLOCK_DESTRUCTION_TOOLTIP = I18nKey.config("forceDisableBlockDestruction.tooltip");
    public static final ModConfigSpec.BooleanValue FORCE_DISABLE_BLOCK_DESTRUCTION = BUILDER
            .comment("Disable block destruction of explosions created by this mod, disregarding the mobGriefing game rule")
            .define("forceDisableBlockDestruction", false);
//    public static final ModConfigSpec.BooleanValue LOG_DIRT_BLOCK = BUILDER
//            .comment("Whether to log the dirt block on common setup")
//            .define("logDirtBlock", true);
//
//    public static final ModConfigSpec.IntValue MAGIC_NUMBER = BUILDER
//            .comment("A magic number")
//            .defineInRange("magicNumber", 42, 0, Integer.MAX_VALUE);
//
//    public static final ModConfigSpec.ConfigValue<String> MAGIC_NUMBER_INTRODUCTION = BUILDER
//            .comment("What you want the introduction message to be for the magic number")
//            .define("magicNumberIntroduction", "The magic number is... ");
//
//    // a list of strings that are treated as resource locations for items
//    public static final ModConfigSpec.ConfigValue<List<? extends String>> ITEM_STRINGS = BUILDER
//            .comment("A list of items to log on common setup.")
//            .defineListAllowEmpty("items", List.of("minecraft:iron_ingot"), () -> "", Config::validateItemName);

    static final ModConfigSpec SPEC = BUILDER.build();

}
