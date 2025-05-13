// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.vanilla.renderer;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.MinecraftTextures;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.render.draw.ResourceRenderer;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.client.gui.screen.widget.attributes.DirtBackgroundType;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

public class VanillaDirtBackgroundRenderer extends VanillaBackgroundRenderer
{
    public VanillaDirtBackgroundRenderer() {
        this.name = "DirtBackground";
    }
    
    @Override
    public void renderPre(final AbstractWidget<?> widget, final ScreenContext context) {
        final float shift = widget.backgroundDirtShift().get();
        DirtBackgroundType dirtBackgroundType = widget.backgroundDirtType().get();
        final float legacyBrightness = widget.backgroundDirtBrightness().get();
        if (legacyBrightness != 0.0f) {
            dirtBackgroundType = ((legacyBrightness >= 64.0f) ? DirtBackgroundType.MENU : DirtBackgroundType.LIST);
        }
        final MinecraftTextures textures = this.labyAPI.minecraft().textures();
        final ResourceLocation backgroundTexture = (dirtBackgroundType == DirtBackgroundType.MENU) ? textures.screenMenuBackgroundTexture() : textures.screenListBackgroundTexture();
        final float brightness = PlatformEnvironment.isFreshUI() ? 255.0f : dirtBackgroundType.getBrightness();
        if (!widget.backgroundAlwaysDirt().get() && this.labyAPI.minecraft().isIngame()) {
            super.renderPre(widget, context);
        }
        else {
            final Rectangle bounds = Bounds.absoluteBounds(widget, BoundsType.MIDDLE);
            this.labyAPI.renderPipeline().resourceRenderer().texture(backgroundTexture).pos(widget.bounds().rectangle(BoundsType.MIDDLE)).sprite(bounds.getX() * 8.0f, (bounds.getY() + shift) * 8.0f, bounds.getWidth() * 8.0f, bounds.getHeight() * 8.0f).color(brightness / 255.0f, brightness / 255.0f, brightness / 255.0f, 1.0f).render(context.stack());
        }
        super.renderPre(widget, context);
    }
}
