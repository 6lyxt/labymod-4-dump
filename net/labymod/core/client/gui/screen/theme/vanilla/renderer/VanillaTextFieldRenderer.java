// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.vanilla.renderer;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.render.font.FontSize;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.core.client.gui.screen.theme.vanilla.util.SpriteRenderer;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

public class VanillaTextFieldRenderer extends VanillaBackgroundRenderer
{
    public VanillaTextFieldRenderer() {
        super("TextField");
    }
    
    @Override
    public void renderPre(final AbstractWidget<?> widget, final ScreenContext context) {
        if (widget instanceof final TextFieldWidget textFieldWidget) {
            switch (textFieldWidget.type().get()) {
                case VANILLA_WINDOW: {
                    SpriteRenderer.renderInBounds16(context.stack(), widget.bounds(), Textures.POPUP_BACKGROUND, 0, 48, 128, 64);
                    return;
                }
            }
        }
        super.renderPre(widget, context);
    }
    
    @Override
    public void renderPost(final AbstractWidget<?> abstractWidget, final ScreenContext context) {
        final TextFieldWidget widget = (TextFieldWidget)abstractWidget;
        try {
            final Rectangle bounds = widget.bounds().rectangle(BoundsType.INNER);
            final float fontHeight = this.componentRenderer.height();
            final float fontSize = widget.fontSize().get().getFontSize();
            final float cursorPositionX = bounds.getX() + widget.getCursorOffsetX();
            final float fontY = bounds.getY() + bounds.getHeight() / 2.0f - (fontHeight / 2.0f - 1.0f) * fontSize;
            final boolean cursorVisible = widget.isCursorVisible() && (TimeUtil.getMillis() - widget.getTimeLastActivity()) % 1000L < 500L;
            final boolean shadow = widget.textShadow().get();
            final Stack stack = context.stack();
            if (widget.shouldDisplayPlaceHolder()) {
                if (widget.placeholder() != null) {
                    this.componentRenderer.builder().text(RenderableComponent.of(widget.placeholder())).pos(bounds.getX(), fontY).scale(fontSize).shadow(shadow).shouldBatch(false).color(widget.placeHolderColor().get()).useFloatingPointPosition(widget.useFloatingPointPosition().get()).render(stack);
                }
            }
            else {
                this.componentRenderer.builder().text(widget.getVisibleText()).pos(bounds.getX(), fontY).scale(fontSize).shouldBatch(false).shadow(shadow).color(widget.textColor().get()).useFloatingPointPosition(widget.useFloatingPointPosition().get()).render(stack);
            }
            if (widget.hasMarked()) {
                stack.push();
                stack.translate(0.0f, 0.0f, 10.0f);
                this.rectangleRenderer.renderRectangle(stack, bounds.getX() + widget.getMarkerStartOffsetX(), fontY - 2.0f * fontSize, bounds.getX() + widget.getMarkerEndOffsetX(), fontY + 8.0f * fontSize, widget.markColor().get());
                stack.pop();
            }
            if (widget.hasMarked()) {
                stack.push();
                stack.translate(0.0f, 0.0f, 15.0f);
                this.componentRenderer.builder().text(widget.getText(widget.getVisibleIndex(widget.getMarkerStartIndex()), widget.getVisibleIndex(widget.getMarkerEndIndex()))).scale(fontSize).shadow(shadow).shouldBatch(false).pos(bounds.getX() + widget.getMarkerStartOffsetX(), fontY).useFloatingPointPosition(widget.useFloatingPointPosition().get()).color(widget.markTextColor().get()).shadow(false).render(stack);
                stack.pop();
            }
            if (cursorVisible && widget.isFocused() && widget.isEditable()) {
                if (widget.isCursorAtEnd()) {
                    this.rectangleRenderer.renderRectangle(stack, cursorPositionX, fontY + 7.0f * fontSize, cursorPositionX + 5.0f * fontSize, fontY + 8.0f * fontSize, widget.textColor().get());
                }
                else {
                    this.rectangleRenderer.renderRectangle(stack, cursorPositionX - 0.5f * fontSize, fontY - 2.0f * fontSize, cursorPositionX + 0.5f * fontSize, fontY + 8.0f * fontSize, widget.textColor().get());
                }
            }
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
