// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.hud.alignment;

import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.widgets.hud.ScaledRectangle;
import net.labymod.api.client.gui.screen.widget.widgets.hud.HudWidgetWidget;
import net.labymod.core.client.gui.screen.widget.widgets.hud.HudWidgetInteractionWidget;
import net.labymod.api.util.bounds.MutableRectangle;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.Textures;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;

public class EditButton
{
    private final SelectionRenderer selection;
    private int previousEditOpacity;
    private int editOpacity;
    private boolean lastHoverEditIcon;
    
    public EditButton(final SelectionRenderer selectionRenderer) {
        this.previousEditOpacity = 0;
        this.editOpacity = 0;
        this.lastHoverEditIcon = false;
        this.selection = selectionRenderer;
    }
    
    public void render(final Stack stack, final MutableMouse mouse, final float tickDelta) {
        final HudWidget<?> hudWidget = this.selection.getLastSelectedHudWidget();
        if (hudWidget == null) {
            return;
        }
        final MutableRectangle entireRectangle = this.selection.getEntireRectangle(1);
        if (entireRectangle == null) {
            return;
        }
        final HudWidgetInteractionWidget interactionWidget = this.selection.interactionWidget();
        final HudWidgetWidget widget = interactionWidget.getWidget(hudWidget);
        final boolean isOnRightSideOfScreen = entireRectangle.getCenterX() > interactionWidget.bounds().getCenterX();
        final int size = 8;
        final int offset = 3;
        final ScaledRectangle rectangle = widget.scaledBounds();
        final float iconX = isOnRightSideOfScreen ? (rectangle.getLeft() - size - offset) : (rectangle.getRight() + offset);
        final float iconY = rectangle.getCenterY() - size / 2.0f;
        if (this.selection.isSingleSelection()) {
            this.lastHoverEditIcon = mouse.isInside(iconX, iconY, size, size);
        }
        else {
            this.lastHoverEditIcon = false;
        }
        final float editOpacity = (float)MathHelper.lerp(this.previousEditOpacity, this.editOpacity);
        final int alpha = (int)(editOpacity / 8.0f * 255.0f);
        Textures.SpriteCommon.EDIT.render(stack, iconX, iconY, (float)size, false, ColorFormat.ARGB32.pack(255, 255, 255, alpha));
    }
    
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        if (this.lastHoverEditIcon && this.selection.isSingleSelection()) {
            this.selection.interactionWidget().openSettings(this.selection.getLastSelectedHudWidget());
            return true;
        }
        return false;
    }
    
    public void onTick() {
        this.previousEditOpacity = this.editOpacity;
        if (this.lastHoverEditIcon) {
            if (this.editOpacity < 8) {
                ++this.editOpacity;
            }
        }
        else if (this.selection.isSingleSelection()) {
            if (this.editOpacity < 4) {
                ++this.editOpacity;
            }
            if (this.editOpacity > 4) {
                --this.editOpacity;
            }
        }
        else if (this.editOpacity > 0) {
            --this.editOpacity;
        }
    }
}
