// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.vanilla.renderer;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.Textures;
import net.labymod.api.client.render.draw.ResourceRenderer;
import net.labymod.api.client.gui.screen.widget.widgets.input.CheckBoxWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.button.VanillaButtonRenderer;

public class VanillaCheckBoxRenderer extends VanillaButtonRenderer
{
    public VanillaCheckBoxRenderer() {
        super("CheckBox");
    }
    
    @Override
    public void renderPre(final AbstractWidget<?> widget, final ScreenContext context) {
        final Bounds bounds = widget.bounds();
        this.renderTexture(context.stack(), bounds.rectangle(BoundsType.MIDDLE), false, widget.isHovered(), widget.backgroundColor().get());
    }
    
    @Override
    public void renderPost(final AbstractWidget<?> widget, final ScreenContext context) {
        final Bounds bounds = widget.bounds();
        final float x = (float)Math.round(bounds.getX(BoundsType.MIDDLE));
        final float y = (float)Math.round(bounds.getY(BoundsType.MIDDLE));
        final float width = (float)Math.round(bounds.getWidth(BoundsType.MIDDLE));
        final float height = (float)Math.round(bounds.getHeight(BoundsType.MIDDLE));
        final CheckBoxWidget checkBoxWidget = (CheckBoxWidget)widget;
        if (checkBoxWidget.state() == CheckBoxWidget.State.CHECKED) {
            this.labyAPI.renderPipeline().resourceRenderer().texture(Textures.SpriteCommon.TEXTURE).pos(x, y).size(width, height).sprite(32.0f, 0.0f, 32.0f, 32.0f).render(context.stack());
        }
        if (checkBoxWidget.state() == CheckBoxWidget.State.PARTLY) {
            final float padding = height / 5.0f;
            this.labyAPI.renderPipeline().rectangleRenderer().renderRectangle(context.stack(), x + padding, y + padding, x + width - padding, y + height - padding, -16730112);
        }
    }
    
    @Override
    public boolean hasInteractionSound() {
        return false;
    }
}
