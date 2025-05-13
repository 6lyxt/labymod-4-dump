// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.draw.hover;

import net.labymod.api.client.render.batch.RectangleRenderContext;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.render.matrix.Stack;
import javax.inject.Inject;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.render.draw.hover.HoverBackgroundEffect;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;

@Singleton
@Implements(key = "vanilla_hover_effect", value = HoverBackgroundEffect.class)
public class VanillaHoverBackgroundEffect extends DefaultHoverBackgroundEffect
{
    private static final float PADDING = 3.0f;
    private static final float LINE_WIDTH = 1.0f;
    private static final int DARK_BACKGROUND = -267386864;
    private static final int LIGHT_BACKGROUND = 1347420415;
    private static final int MEDIUM_BACKGROUND = 1344798847;
    
    @Inject
    public VanillaHoverBackgroundEffect(final LabyAPI labyAPI) {
        super(labyAPI);
    }
    
    @Override
    public float getPadding() {
        return 4.0f;
    }
    
    @Override
    public void render(final Stack stack, final float x, final float y, final float width, final float height, final RenderableComponent component) {
        this.rectangleRenderer.setupGradient(stack, context -> {
            final float left = x - 3.0f;
            final float top = y - 3.0f;
            final float right = x + width + 1.5f;
            final float bottom = y + height + 1.5f;
            context.renderGradientVertically(left, top - 1.0f, right, top, -267386864, -267386864).renderGradientVertically(left, bottom, right, bottom + 1.0f, -267386864, -267386864).renderGradientVertically(left, top, right, bottom, -267386864, -267386864).renderGradientVertically(left - 1.0f, top, left, bottom, -267386864, -267386864).renderGradientVertically(right, top, right + 1.0f, bottom, -267386864, -267386864).renderGradientVertically(left, top + 1.0f, left + 1.0f, bottom - 1.0f, 1347420415, 1344798847).renderGradientVertically(right - 1.0f, top + 1.0f, right, bottom - 1.0f, 1347420415, 1344798847).renderGradientVertically(left, top, right, top + 1.0f, 1347420415, 1347420415).renderGradientVertically(left, bottom - 1.0f, right, bottom, 1344798847, 1344798847);
            return;
        });
        this.componentRendererBuilder.text(component).pos(x, y).color(-1).allowColors(true).render(stack);
    }
}
