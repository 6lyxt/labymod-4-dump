// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.fancy.renderer;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.BorderRadius;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.Textures;
import net.labymod.api.client.render.draw.ResourceRenderer;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.input.CheckBoxWidget;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaBackgroundRenderer;

public class FancyCheckBoxRenderer extends VanillaBackgroundRenderer
{
    public FancyCheckBoxRenderer() {
        super("CheckBox");
    }
    
    @Override
    public void renderPost(final AbstractWidget<?> abstractWidget, final ScreenContext context) {
        if (abstractWidget instanceof final CheckBoxWidget checkBoxWidget) {
            final Bounds bounds = checkBoxWidget.bounds();
            final float x = bounds.getX(BoundsType.INNER);
            final float y = bounds.getY(BoundsType.INNER);
            final float width = bounds.getWidth(BoundsType.INNER);
            final float height = bounds.getHeight(BoundsType.INNER);
            if (checkBoxWidget.state() == CheckBoxWidget.State.CHECKED) {
                this.labyAPI.renderPipeline().resourceRenderer().texture(Textures.SpriteCommon.TEXTURE).pos(x, y).size(width, height).sprite(32.0f, 0.0f, 32.0f, 32.0f).render(context.stack());
            }
            if (checkBoxWidget.state() == CheckBoxWidget.State.PARTLY) {
                final float padding = height / 10.0f;
                final BorderRadius borderRadius = abstractWidget.getBorderRadius();
                if (borderRadius == null) {
                    return;
                }
                this.rectangleRenderer.pos(x + padding, y + padding, x + width - padding, y + height - padding).rounded(borderRadius.getLeftTop(), borderRadius.getRightTop(), borderRadius.getLeftBottom(), borderRadius.getRightBottom()).lowerEdgeSoftness(borderRadius.getLowerEdgeSoftness()).upperEdgeSoftness(borderRadius.getUpperEdgeSoftness()).color(-16730112).render(context.stack());
            }
        }
    }
}
