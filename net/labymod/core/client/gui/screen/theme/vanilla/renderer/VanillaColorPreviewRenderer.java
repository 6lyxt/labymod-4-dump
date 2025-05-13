// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.vanilla.renderer;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.BorderRadius;
import net.labymod.api.util.bounds.ReasonableMutableRectangle;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.icon.Icon;

public class VanillaColorPreviewRenderer extends VanillaBackgroundRenderer
{
    private final Icon icon;
    
    public VanillaColorPreviewRenderer() {
        this.name = "ColorPreview";
        this.icon = Icon.texture(Textures.TRANSPARENT);
    }
    
    @Override
    public void renderPre(final AbstractWidget<?> widget, final ScreenContext context) {
        final int backgroundColor = widget.backgroundColor().get();
        if (backgroundColor != 0 && (backgroundColor & 0xFF000000) >>> 24 == 255) {
            super.renderPre(widget, context);
            return;
        }
        final ReasonableMutableRectangle bounds = widget.bounds().rectangle(BoundsType.MIDDLE);
        this.icon.spriteSize((int)(256.0f * bounds.getWidth() / 6.0f), (int)(256.0f * bounds.getHeight() / 6.0f));
        final BorderRadius borderRadius = widget.getBorderRadius();
        if (borderRadius != null && borderRadius.isSet()) {
            this.icon.setBorderRadius(borderRadius);
        }
        this.icon.render(context.stack(), bounds);
        super.renderPre(widget, context);
    }
}
