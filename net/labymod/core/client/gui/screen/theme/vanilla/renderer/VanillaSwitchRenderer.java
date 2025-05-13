// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.vanilla.renderer;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.render.font.text.TextRenderer;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.button.VanillaButtonRenderer;

public class VanillaSwitchRenderer extends VanillaButtonRenderer
{
    private static final int STATE_WIDTH = 4;
    
    public VanillaSwitchRenderer() {
        super("Switch");
    }
    
    @Override
    public void renderPre(final AbstractWidget<?> widget, final ScreenContext context) {
        final SwitchWidget switchWidget = (SwitchWidget)widget;
        final Bounds bounds = widget.bounds();
        final float buttonWidth = bounds.getWidth();
        this.renderTexture(context.stack(), bounds.rectangle(BoundsType.MIDDLE), false, widget.isHovered(), widget.backgroundColor().get());
        final boolean enabled = switchWidget.getValue();
        final float sliderX = enabled ? (bounds.getX() + bounds.getWidth() - 8.0f) : bounds.getX();
        final Integer textHoverColor = switchWidget.textHoverColor().get();
        this.renderTexture(context.stack(), Rectangle.relative(sliderX, bounds.getY(), 8.0f, bounds.getHeight()), true, textHoverColor != null && widget.isHovered(), enabled ? ColorFormat.ARGB32.pack(85, 255, 20) : ColorFormat.ARGB32.pack(255, 85, 20));
        int color;
        if (widget.isHovered() && textHoverColor != null) {
            color = textHoverColor;
        }
        else {
            color = (enabled ? NamedTextColor.WHITE.getValue() : NamedTextColor.GRAY.getValue());
        }
        final float textX = enabled ? ((buttonWidth - 4.0f) / 2.0f) : ((buttonWidth - 4.0f) / 2.0f + 6.0f);
        final TextRenderer textRenderer = this.labyAPI.renderPipeline().textRenderer();
        textRenderer.text(switchWidget.getText()).pos(bounds.getX() + textX, bounds.getY() + (bounds.getHeight() - (textRenderer.height() - 1.0f)) / 2.0f).color(color | 0xFF000000).shadow(true).centered(true).render(context.stack());
    }
}
