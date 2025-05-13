// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.post;

import net.labymod.api.client.resources.ResourceLocation;

public final class PostEffects
{
    public static final ResourceLocation GRAY_SCALE;
    public static final ResourceLocation LABYMOD_MOTION_BLUR;
    public static final ResourceLocation OLD_MOTION_BLUR;
    public static final ResourceLocation MENU_BLUR;
    public static final ResourceLocation ACTIVITY_MENU_BLUR;
    
    static {
        GRAY_SCALE = ResourceLocation.create("labymod", "shaders/post/gray_scale.json");
        LABYMOD_MOTION_BLUR = ResourceLocation.create("labymod", "shaders/post/labymod_motion_blur.json");
        OLD_MOTION_BLUR = ResourceLocation.create("labymod", "shaders/post/old_motion_blur.json");
        MENU_BLUR = ResourceLocation.create("labymod", "shaders/post/menu_blur.json");
        ACTIVITY_MENU_BLUR = ResourceLocation.create("labymod", "shaders/post/activity_menu_blur.json");
    }
}
