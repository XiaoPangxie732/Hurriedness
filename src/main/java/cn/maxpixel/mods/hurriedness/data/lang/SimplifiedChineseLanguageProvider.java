package cn.maxpixel.mods.hurriedness.data.lang;

import cn.maxpixel.mods.hurriedness.Config;
import cn.maxpixel.mods.hurriedness.registry.BlockRegistry;
import cn.maxpixel.mods.hurriedness.registry.ItemRegistry;
import cn.maxpixel.mods.hurriedness.util.I18nKey;
import net.minecraft.data.PackOutput;

public class SimplifiedChineseLanguageProvider extends CustomLanguageProvider {
    public SimplifiedChineseLanguageProvider(PackOutput output) {
        super(output, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        add(I18nKey.KEY_CONFIG_TITLE, "急 配置");
        add(I18nKey.KEY_CONFIG_SECTION_COMMON, "急 配置");
        add(I18nKey.KEY_CONFIG_SECTION_COMMON_TITLE, "急 配置");
        add(Config.KEY_FORCE_DISABLE_BLOCK_DESTRUCTION, "强制关闭方块破坏");
        add(Config.KEY_FORCE_DISABLE_BLOCK_DESTRUCTION_TOOLTIP, "关闭本模组所产生的爆炸的方块破坏效果，无论mob_griefing游戏规则是否为真");
        add(Config.KEY_ENABLE_HURRIEDNESS_VALUE_DEBUG_RENDERER, "开启急值调试渲染器");
        add(Config.KEY_ENABLE_HURRIEDNESS_VALUE_DEBUG_RENDERER_TOOLTIP, "是否开启急值调试渲染器，开启后将显示附近方块与实体的急值");
//        add(Config.KEY_EXHIBITION_MODE, "展会模式");

        addBlock(BlockRegistry.HURRIED_ANCHOR, "急锚");

        addItem(ItemRegistry.ALIEN_HAND_HURRY, "你已急哭");
        addItem(ItemRegistry.ALIEN_HAND_NOT_HURRY, "你没急吧");
        addItem(ItemRegistry.HURRIED_EGG, "急蛋");
    }
}
