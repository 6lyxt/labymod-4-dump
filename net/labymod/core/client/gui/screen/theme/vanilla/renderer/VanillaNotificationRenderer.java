// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.vanilla.renderer;

import net.labymod.api.client.gfx.pipeline.texture.data.scale.SpriteScaling;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.render.AtlasRenderer;
import net.labymod.api.notification.Notification;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.client.render.batch.ResourceRenderContext;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite;
import net.labymod.core.client.gfx.pipeline.texture.atlas.DefaultTextureSprite;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.NineSpliceScaling;
import net.labymod.api.Textures;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.core.client.gfx.pipeline.texture.atlas.AbstractTextureAtlas;
import net.labymod.core.client.gui.screen.widget.widgets.notification.NotificationWidget;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;

public class VanillaNotificationRenderer extends ThemeRenderer<NotificationWidget>
{
    private final AbstractTextureAtlas atlas;
    private static final ResourceLocation NOTIFICATION;
    
    public VanillaNotificationRenderer() {
        super("Notification");
        (this.atlas = new AbstractTextureAtlas(Textures.NOTIFICATION, 128, 128)).register(VanillaNotificationRenderer.NOTIFICATION, new DefaultTextureSprite(0.0f, 0.0f, 0.25f, 0.25f, (width, height) -> {
            new NineSpliceScaling(32, 32, new NineSpliceScaling.Border(3, 3, 3, 3));
            return;
        }));
        Laby.references().atlasRegistry().register(this.atlas);
    }
    
    @Override
    public void renderPre(final NotificationWidget widget, final ScreenContext context) {
        final Bounds bounds = widget.bounds();
        final Notification notification = widget.notification();
        final float progress = notification.progress();
        final AtlasRenderer renderer = ResourceRenderContext.ATLAS_RENDERER;
        final ResourceRenderContext resourceRenderContext = this.labyAPI.renderPipeline().renderContexts().resourceRenderContext();
        resourceRenderContext.begin(context.stack(), this.atlas.resource());
        renderer.blitSprite(resourceRenderContext, this.atlas, VanillaNotificationRenderer.NOTIFICATION, (int)bounds.getX(), (int)bounds.getY(), (int)bounds.getWidth(), (int)bounds.getHeight(), -1);
        resourceRenderContext.uploadToBuffer();
        final float padding = 3.0f;
        final float progressHeight = 1.0f;
        final RectangleRenderer rectangleRenderer = Laby.labyAPI().renderPipeline().rectangleRenderer();
        rectangleRenderer.pos(bounds.getX() + padding, bounds.getBottom() - progressHeight - padding + 1.0f).size(bounds.getWidth() - padding * 2.0f, progressHeight).color(0, 50, 130, 155).render(context.stack());
        rectangleRenderer.pos(bounds.getX() + padding, bounds.getBottom() - progressHeight - padding + 1.0f).size((bounds.getWidth() - padding * 2.0f) * progress, progressHeight).color(0, 150, 230, 255).render(context.stack());
    }
    
    static {
        NOTIFICATION = ResourceLocation.create("labymod", "notification");
    }
}
