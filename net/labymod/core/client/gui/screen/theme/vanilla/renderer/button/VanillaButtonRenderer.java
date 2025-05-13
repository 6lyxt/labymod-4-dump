// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.vanilla.renderer.button;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.render.draw.batch.BatchResourceRenderer;
import net.labymod.api.client.render.draw.ResourceRenderer;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.render.batch.ResourceRenderContext;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.client.gfx.pipeline.texture.atlas.Atlases;
import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributeState;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;

public class VanillaButtonRenderer extends ThemeRenderer<AbstractWidget<?>>
{
    protected static final ResourceLocation BUTTON;
    protected static final ResourceLocation BUTTON_DISABLED;
    protected static final ResourceLocation BUTTON_HIGHLIGHTED;
    
    public VanillaButtonRenderer() {
        super("Button");
    }
    
    public VanillaButtonRenderer(final String name) {
        super(name);
    }
    
    @Override
    public void renderPre(final AbstractWidget<?> widget, final ScreenContext context) {
        this.renderTexture(context.stack(), widget.bounds().rectangle(BoundsType.MIDDLE), widget.isAttributeStateEnabled(AttributeState.ENABLED), widget.isHovered() || widget.isFocused(), widget.backgroundColor().get());
    }
    
    public void renderTexture(final Stack stack, final Rectangle rectangle, final boolean enabled, final boolean hovered, int backgroundColor) {
        final TextureAtlas widgetAtlas = Laby.references().atlasRegistry().getAtlas(Atlases.WIDGET_ATLAS);
        final ResourceLocation buttonTexture = enabled ? (hovered ? VanillaButtonRenderer.BUTTON_HIGHLIGHTED : VanillaButtonRenderer.BUTTON) : VanillaButtonRenderer.BUTTON_DISABLED;
        if (backgroundColor == 0) {
            backgroundColor = -1;
        }
        this.renderSingleVanillaButton(stack, rectangle, backgroundColor, widgetAtlas, buttonTexture);
    }
    
    protected void renderSingleVanillaButton(final Stack stack, final Rectangle rectangle, final int backgroundColor, final TextureAtlas textureAtlas, final ResourceLocation buttonTexture) {
        final TextureSprite sprite = textureAtlas.findSprite(buttonTexture);
        if (sprite == null) {
            throw new IllegalStateException();
        }
        final ResourceRenderer resourceRenderer = this.labyAPI.renderPipeline().resourceRenderer();
        final float x = rectangle.getX();
        final float y = rectangle.getY();
        final float width = rectangle.getWidth();
        final float height = rectangle.getHeight();
        final BatchResourceRenderer renderer = resourceRenderer.beginBatch(stack, textureAtlas.resource());
        ResourceRenderContext.ATLAS_RENDERER.blitSprite(this.resourceRenderContext, sprite.uv(), sprite.scaling(), MathHelper.ceil(x), MathHelper.ceil(y), MathHelper.ceil(width), MathHelper.ceil(height), 0, backgroundColor);
        renderer.upload();
    }
    
    @Override
    public void playInteractionSound(final AbstractWidget<?> widget) {
        this.labyAPI.minecraft().sounds().playButtonPress();
    }
    
    @Override
    public boolean hasInteractionSound() {
        return true;
    }
    
    static {
        BUTTON = ResourceLocation.create("minecraft", "widget/button");
        BUTTON_DISABLED = ResourceLocation.create("minecraft", "widget/button_disabled");
        BUTTON_HIGHLIGHTED = ResourceLocation.create("minecraft", "widget/button_highlighted");
    }
}
