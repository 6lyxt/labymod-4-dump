// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.fancy.renderer;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.overlay.WidgetReference;
import net.labymod.api.client.gfx.pipeline.util.Scissor;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.popup.DropdownPopupWidget;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

public class FancyDropdownPopupRenderer extends FancyBackgroundRenderer
{
    public FancyDropdownPopupRenderer() {
        this.name = "DropdownPopup";
    }
    
    @Override
    public void renderWidget(final AbstractWidget<?> widget, final ScreenContext context) {
        if (widget instanceof final DropdownPopupWidget dropdownPopupWidget) {
            final DropdownPopupWidget<?> popupWidget = dropdownPopupWidget;
            final DropdownWidget<?> dropdown = popupWidget.getDropdown();
            final Rectangle rectangle = widget.bounds().rectangle(BoundsType.OUTER);
            final float progress = dropdown.getAnimationProgress();
            final float height = rectangle.getHeight();
            final Scissor scissor = this.labyAPI.gfxRenderPipeline().scissor();
            try {
                scissor.push(context.stack(), rectangle.getX(), rectangle.getY() + (dropdown.isDropUp() ? (height * (1.0f - progress)) : 0.0f), rectangle.getWidth(), height * progress);
                super.renderWidget(widget, context);
            }
            finally {
                scissor.pop();
            }
            return;
        }
        super.renderWidget(widget, context);
    }
    
    @Override
    public void renderPre(final AbstractWidget<?> widget, final ScreenContext context) {
        if (!(widget instanceof DropdownPopupWidget)) {
            super.renderPre(widget, context);
            return;
        }
        final DropdownPopupWidget<?> popupWidget = (DropdownPopupWidget)widget;
        final DropdownWidget<?> dropdown = popupWidget.getDropdown();
        final float progress = dropdown.getAnimationProgress();
        final WidgetReference reference = dropdown.getReference();
        if (progress == 0.0f || reference == null) {
            return;
        }
        final Rectangle popupRect = popupWidget.bounds().rectangle(BoundsType.OUTER);
        final float popupHeight = reference.widget().getEffectiveHeight() + 2.0f;
        final Rectangle rectangle = Rectangle.relative(popupRect.getX(), popupRect.getY() + (dropdown.isDropUp() ? (popupHeight * (1.0f - progress)) : 0.0f), popupRect.getWidth(), popupHeight * progress);
        final int backgroundColor = widget.backgroundColor().get();
        final int alpha = (int)Math.max((float)(backgroundColor >> 24 & 0xFF), progress * 255.0f);
        super.renderBackground(context.stack(), widget, rectangle, rectangle, (backgroundColor & 0xFFFFFF) | alpha << 24);
    }
}
