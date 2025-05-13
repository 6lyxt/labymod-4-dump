// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.pipeline.renderer.util;

import net.labymod.api.Textures;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.icon.Icon;

public final class BrandingRenderer
{
    public static final float NORMAL_INVENTORY_HEIGHT = 96.0f;
    public static final float CREATIVE_INVENTORY_HEIGHT = 108.0f;
    private static final Icon BRANDING;
    private static final float BRANDING_WIDTH = 100.0f;
    private static final float BRANDING_HEIGHT = 24.25f;
    
    public static void renderCentered(final Stack stack, final float y) {
        renderCentered(stack, y, Laby.labyAPI().config().ingame().inventoryBanner().get());
    }
    
    public static void renderCentered(final Stack stack, final float y, final boolean shouldRender) {
        final Window window = Laby.labyAPI().minecraft().minecraftWindow();
        final float halfWidth = window.getScaledWidth() / 2.0f;
        final float halfHeight = window.getScaledHeight() / 2.0f;
        render(stack, halfWidth - 50.0f, halfHeight - 12.125f - y, shouldRender);
    }
    
    public static void render(final Stack stack, final float x, final float y, final boolean shouldRender) {
        if (!shouldRender) {
            return;
        }
        BrandingRenderer.BRANDING.render(stack, x, y, 100.0f, 24.25f);
    }
    
    static {
        BRANDING = Icon.texture(Textures.LABYMOD_LOGO);
    }
}
