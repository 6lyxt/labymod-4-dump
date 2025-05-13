// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.fancy.renderer;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.sound.SoundType;
import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.overlay.WidgetReference;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

public class FancyDropdownRenderer extends FancyBackgroundRenderer
{
    public FancyDropdownRenderer() {
        this.name = "Dropdown";
    }
    
    @Override
    public void renderPre(final AbstractWidget<?> widget, final ScreenContext context) {
        if (!(widget instanceof DropdownWidget)) {
            super.renderPre(widget, context);
            return;
        }
        final DropdownWidget<?> dropdown = (DropdownWidget)widget;
        final float progress = dropdown.getAnimationProgress();
        final WidgetReference reference = dropdown.getReference();
        if (progress == 0.0f || reference == null) {
            super.renderPre(widget, context);
            return;
        }
        final Bounds bounds = dropdown.bounds();
        final float popupHeight = reference.widget().getEffectiveHeight();
        final Rectangle rectangle = Rectangle.relative(bounds.getX(), bounds.getY() - (dropdown.isDropUp() ? (popupHeight * progress) : 0.0f), bounds.getWidth(), bounds.getHeight() + popupHeight * progress);
        final int backgroundColor = widget.backgroundColor().get();
        final int alpha = (int)Math.max((float)(backgroundColor >> 24 & 0xFF), progress * 255.0f);
        super.renderBackground(context.stack(), widget, rectangle, rectangle, (backgroundColor & 0xFFFFFF) | alpha << 24);
    }
    
    @Override
    public void renderPost(final AbstractWidget<?> widget, final ScreenContext context) {
        super.renderPost(widget, context);
        if (widget instanceof final DropdownWidget dropdownWidget) {
            final DropdownWidget<?> dropdown = dropdownWidget;
            final Bounds bounds = widget.bounds();
            final float rotation = (dropdown.isOpen() ? 1 : -1) * (dropdown.getAnimationProgress() * 180.0f);
            final Stack stack = context.stack();
            stack.push();
            stack.translate(bounds.getX() + bounds.getWidth() - 8.0f, bounds.getY() + bounds.getHeight() / 2.0f, 0.0f);
            stack.rotate(rotation, 0.0f, 0.0f, 1.0f);
            if (dropdown.hasEntries()) {
                Textures.SpriteCommon.SMALL_DOWN_SHADOW.render(stack, -4.0f, -4.0f, 8.0f);
            }
            stack.pop();
        }
    }
    
    @Override
    public void playInteractionSound(final AbstractWidget<?> widget) {
        Laby.references().soundService().play(SoundType.BUTTON_CLICK);
    }
    
    @Override
    public boolean hasInteractionSound() {
        return true;
    }
}
