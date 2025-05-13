// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.vanilla.renderer.button;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

public class VanillaButtonDropdownRenderer extends VanillaButtonRenderer
{
    public VanillaButtonDropdownRenderer() {
        this.name = "ButtonDropdown";
    }
    
    @Override
    public void renderPost(final AbstractWidget<?> widget, final ScreenContext context) {
        if (widget instanceof final DropdownWidget dropdownWidget2) {
            final DropdownWidget<?> dropdownWidget = dropdownWidget2;
            final Bounds bounds = widget.bounds();
            final float x = bounds.getX(BoundsType.MIDDLE);
            final float y = bounds.getY(BoundsType.MIDDLE);
            final float width = bounds.getWidth(BoundsType.MIDDLE);
            final float height = bounds.getHeight(BoundsType.MIDDLE);
            final boolean open = dropdownWidget.isOpen();
            final boolean hasEntries = dropdownWidget.hasEntries();
            if (hasEntries) {
                final Icon icon = open ? Textures.SpriteCommon.SMALL_UP_SHADOW : Textures.SpriteCommon.SMALL_DOWN_SHADOW;
                icon.render(context.stack(), x + width - 12.0f, y + height / 2.0f - 4.0f, 8.0f);
            }
            Textures.SpriteCommon.DELIMITER.render(context.stack(), x + width - 8.0f - 12.0f, y + 2.0f, 8.0f, height - 4.0f);
        }
    }
}
