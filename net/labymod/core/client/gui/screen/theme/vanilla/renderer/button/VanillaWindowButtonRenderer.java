// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.vanilla.renderer.button;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.core.client.gui.screen.theme.vanilla.util.SpriteRenderer;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributeState;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

public class VanillaWindowButtonRenderer extends VanillaButtonRenderer
{
    public VanillaWindowButtonRenderer() {
        super("WindowButton");
    }
    
    @Override
    public void renderPre(final AbstractWidget<?> widget, final ScreenContext context) {
        int spriteX = 48;
        if (widget.isHovered()) {
            spriteX += 16;
        }
        if (!widget.isAttributeStateEnabled(AttributeState.ENABLED)) {
            spriteX = 80;
        }
        SpriteRenderer.renderInBounds16(context.stack(), widget.bounds(), Textures.POPUP_BACKGROUND, spriteX, 32, 128, 64);
    }
}
