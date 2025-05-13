// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.draw.hover;

import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.render.matrix.Stack;
import javax.inject.Inject;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.render.draw.hover.HoverBackgroundEffect;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;

@Singleton
@Implements(key = "fancy_hover_effect", value = HoverBackgroundEffect.class)
public class FancyHoverBackgroundEffect extends DefaultHoverBackgroundEffect
{
    private static final float PADDING = 3.0f;
    private static final int DARK_BACKGROUND = -267386864;
    private static final int LIGHT_BACKGROUND = 1347420415;
    
    @Inject
    public FancyHoverBackgroundEffect(final LabyAPI labyAPI) {
        super(labyAPI);
    }
    
    @Override
    public float getPadding() {
        return 3.0f;
    }
    
    @Override
    public float getScale() {
        return 0.75f;
    }
    
    @Override
    public void render(final Stack stack, final float x, final float y, final float width, final float height, final RenderableComponent component) {
        this.rectangleRenderer.pos(x - 3.0f, y - 3.0f, x + width + 1.5f, y + height + 1.5f).color(ColorFormat.ARGB32.pack(-267386864, 240)).rounded(3.0f * this.getScale() + 1.0f).lowerEdgeSoftness(-0.2f).borderColor(ColorFormat.ARGB32.pack(180, 180, 180, 255)).borderThickness(2.0f).borderSoftness(4.0f).render(stack);
        this.componentRendererBuilder.text(component).useFloatingPointPosition(true).pos(x, y).scale(this.getScale()).color(-1).allowColors(true).render(stack);
    }
}
