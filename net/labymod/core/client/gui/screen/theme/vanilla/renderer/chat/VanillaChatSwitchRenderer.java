// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.vanilla.renderer.chat;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.Textures;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;

public class VanillaChatSwitchRenderer extends ThemeRenderer<SwitchWidget>
{
    public VanillaChatSwitchRenderer() {
        super("ChatSwitch");
    }
    
    @Override
    public void renderPre(final SwitchWidget widget, final ScreenContext context) {
        final Bounds bounds = widget.bounds();
        this.rectangleRenderer.pos(bounds).color(widget.isHovered() ? -2130706433 : 1090519039).render(context.stack());
        if (widget.getValue()) {
            Textures.SpriteCommon.SMALL_CHECKED.render(context.stack(), bounds.getCenterX() - 4.0f, bounds.getCenterY() - 4.0f, 8.0f, 8.0f);
        }
    }
    
    @Override
    public void playInteractionSound(final SwitchWidget widget) {
        this.labyAPI.minecraft().sounds().playButtonPress();
    }
    
    @Override
    public boolean hasInteractionSound() {
        return true;
    }
}
