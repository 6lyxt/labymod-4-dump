// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.vanilla.renderer;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.input.itemstack.ItemStackPickerWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributeState;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.button.VanillaButtonRenderer;

public class VanillaItemStackPickerRenderer extends VanillaButtonRenderer
{
    public VanillaItemStackPickerRenderer() {
        super("ItemStackPicker");
    }
    
    @Override
    public void renderPre(final AbstractWidget<?> widget, final ScreenContext context) {
        final Bounds bounds = widget.bounds();
        this.renderTexture(context.stack(), bounds.rectangle(BoundsType.MIDDLE), widget.isAttributeStateEnabled(AttributeState.ENABLED), widget.isHovered(), widget.backgroundColor().get());
    }
    
    @Override
    public void renderPost(final AbstractWidget<?> widget, final ScreenContext context) {
        super.renderPost(widget, context);
        final ItemStackPickerWidget pickerWidget = (ItemStackPickerWidget)widget;
        final Bounds bounds = widget.bounds();
        this.labyAPI.minecraft().itemStackRenderer().renderItemStack(context.stack(), pickerWidget.itemStack(), (int)(bounds.getCenterX() - 8.0f), (int)(bounds.getCenterY() - 8.0f));
    }
}
