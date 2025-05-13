// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.vanilla.renderer.background;

import net.labymod.api.client.gfx.pipeline.texture.data.scale.NineSpliceScaling;
import net.labymod.core.client.gfx.pipeline.texture.atlas.DefaultTextureUV;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.render.draw.batch.BatchResourceRenderer;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.render.draw.ResourceRenderer;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.render.batch.ResourceRenderContext;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.core.client.gui.screen.theme.vanilla.VanillaTheme;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.SpriteScaling;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;

public class VanillaTexturedBackgroundRenderer extends ThemeRenderer<AbstractWidget<?>>
{
    private static final TextureUV UV;
    private static final SpriteScaling SCALING;
    private final ResourceLocation resource;
    
    public VanillaTexturedBackgroundRenderer(final VanillaTheme theme) {
        super("TexturedBackground");
        this.resource = theme.resource("labymod", "textures/widgets/textured_background.png");
    }
    
    @Override
    public void renderPre(final AbstractWidget<?> widget, final ScreenContext context) {
        final ResourceRenderer resourceRenderer = this.labyAPI.renderPipeline().resourceRenderer();
        final Rectangle rectangle = widget.bounds().rectangle(BoundsType.MIDDLE);
        final float x = rectangle.getX();
        final float y = rectangle.getY();
        final float width = rectangle.getWidth();
        final float height = rectangle.getHeight();
        final BatchResourceRenderer renderer = resourceRenderer.beginBatch(context.stack(), this.resource);
        int backgroundColor = widget.backgroundColor().get();
        if (backgroundColor == 0) {
            backgroundColor = -1;
        }
        ResourceRenderContext.ATLAS_RENDERER.blitSprite(this.resourceRenderContext, VanillaTexturedBackgroundRenderer.UV, VanillaTexturedBackgroundRenderer.SCALING, MathHelper.ceil(x), MathHelper.ceil(y), MathHelper.ceil(width), MathHelper.ceil(height), 0, backgroundColor);
        renderer.upload();
    }
    
    static {
        UV = new DefaultTextureUV(0.0f, 0.0f, 1.0f, 1.0f);
        SCALING = new NineSpliceScaling(200, 20, new NineSpliceScaling.Border(1, 1, 1, 1));
    }
}
