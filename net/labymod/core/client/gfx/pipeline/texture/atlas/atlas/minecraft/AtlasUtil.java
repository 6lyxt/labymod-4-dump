// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.pipeline.texture.atlas.atlas.minecraft;

import net.labymod.api.client.gfx.pipeline.texture.data.scale.SpriteScaling;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.StretchScaling;
import net.labymod.api.client.resources.ResourceLocation;

public final class AtlasUtil
{
    private static final String SERVER_LIST = "server_list";
    private static final String ICON = "icon";
    private static final String HUD = "hud";
    
    public static void buildIcons(final SpriteFactory factory) {
        buildHudIcons(factory);
        buildPingIcons(factory);
        buildExperience(factory);
    }
    
    private static void buildExperience(final SpriteFactory factory) {
        factory.register(createMinecraft("hud/experience_bar_background"), 0, 64, 183, 5);
        factory.register(createMinecraft("hud/experience_bar_progress"), 0, 69, 183, 5);
    }
    
    private static void buildHudIcons(final SpriteFactory factory) {
        factory.register(createMinecraft("hud/food_empty"), 16, 27, 9, 9);
        factory.register(createMinecraft("hud/food_half"), 61, 27, 9, 9);
        factory.register(createMinecraft("hud/food_full"), 52, 27, 9, 9);
        factory.register(createMinecraft("hud/heart/container"), 16, 0, 9, 9);
        factory.register(createMinecraft("hud/heart/full"), 52, 0, 9, 9);
        factory.register(createMinecraft("hud/heart/full_blinking"), 43, 0, 9, 9);
        factory.register(createMinecraft("hud/heart/half"), 61, 0, 9, 9);
        factory.register(createMinecraft("hud/heart/absorbing_full"), 160, 0, 9, 9);
        factory.register(createMinecraft("hud/heart/absorbing_half"), 169, 0, 9, 9);
        factory.register(createMinecraft("hud/armor_full"), 43, 9, 9, 9);
    }
    
    private static void buildPingIcons(final SpriteFactory factory) {
        factory.register(createMinecraft("server_list/incompatible"), 0, 216, 10, 8);
        factory.register(createMinecraft("server_list/unreachable"), 0, 216, 10, 8);
        factory.register(createMinecraft("icon/ping_unknown"), 0, 216, 10, 8);
        int i = 0;
        for (int type = 0; type < 2; ++type) {
            for (int index = 5; index > 0; --index) {
                factory.register(createMinecraft(((type == 0) ? "server_list" : "icon") + "/ping_" + index), 0, 177 + i * 8, 10, 8);
                ++i;
            }
            i = 0;
        }
        for (int index2 = 5; index2 > 0; --index2) {
            factory.register(createMinecraft("server_list/pinging_" + index2), 10, 177 + i * 8, 10, 8);
            ++i;
        }
    }
    
    private static ResourceLocation createMinecraft(final String path) {
        return ResourceLocation.create("minecraft", path);
    }
    
    public interface SpriteFactory
    {
        default void register(final ResourceLocation id, final int x, final int y, final int width, final int height) {
            this.register(id, x, y, width, height, (w, h) -> new StretchScaling());
        }
        
        void register(final ResourceLocation p0, final int p1, final int p2, final int p3, final int p4, final SpriteScaling.Factory p5);
    }
}
