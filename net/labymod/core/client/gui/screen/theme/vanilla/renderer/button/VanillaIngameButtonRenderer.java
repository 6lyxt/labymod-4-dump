// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.vanilla.renderer.button;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributeState;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaBackgroundRenderer;

public class VanillaIngameButtonRenderer extends VanillaBackgroundRenderer
{
    public VanillaIngameButtonRenderer() {
        super("ButtonInGame");
    }
    
    @Override
    public void renderPre(final AbstractWidget<?> widget, final ScreenContext context) {
        final int backgroundColor = widget.backgroundColor().get();
        if (backgroundColor == 0) {
            widget.backgroundColor().set(widget.isAttributeStateEnabled(AttributeState.ENABLED) ? (widget.isHovered() ? ColorFormat.ARGB32.pack(20, 20, 20, 220) : ColorFormat.ARGB32.pack(0, 0, 0, 170)) : ColorFormat.ARGB32.pack(0, 0, 0, 180));
        }
        super.renderPre(widget, context);
        widget.backgroundColor().set(backgroundColor);
    }
}
