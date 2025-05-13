// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.fancy.renderer;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.widgets.input.itemstack.ItemStackPickerWidget;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaBackgroundRenderer;

public class FancyItemStackPickerRenderer extends VanillaBackgroundRenderer
{
    public FancyItemStackPickerRenderer() {
        super("ItemStackPicker");
    }
    
    @Override
    public void renderPost(final AbstractWidget<?> widget, final ScreenContext context) {
        final ItemStackPickerWidget pickerWidget = (ItemStackPickerWidget)widget;
        final Bounds bounds = widget.bounds();
        super.renderPost(widget, context);
        this.labyAPI.minecraft().itemStackRenderer().renderItemStack(context.stack(), pickerWidget.itemStack(), (int)(bounds.getCenterX() - 8.0f), (int)(bounds.getCenterY() - 8.0f));
    }
}
