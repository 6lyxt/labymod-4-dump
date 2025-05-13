// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.hud.position;

import net.labymod.api.util.bounds.area.RectangleAreaPosition;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;

public enum HudWidgetAnchor
{
    LEFT_TOP, 
    CENTER_TOP, 
    RIGHT_TOP, 
    LEFT_BOTTOM, 
    CENTER_BOTTOM, 
    RIGHT_BOTTOM;
    
    @NotNull
    public static HudWidgetAnchor of(final HudWidget<?> hudWidget) {
        final HudWidgetConfig config = (HudWidgetConfig)hudWidget.getConfig();
        return of(config.areaIdentifier(), config.horizontalAlignment().get());
    }
    
    @NotNull
    public static HudWidgetAnchor of(final RectangleAreaPosition position, HorizontalHudWidgetAlignment horizontalAlignment) {
        if (horizontalAlignment == HorizontalHudWidgetAlignment.AUTO) {
            final boolean isLocatedAtLeftArea = position == RectangleAreaPosition.TOP_LEFT || position == RectangleAreaPosition.MIDDLE_LEFT || position == RectangleAreaPosition.BOTTOM_LEFT;
            final boolean isLocatedAtCenter = position == RectangleAreaPosition.TOP_CENTER || position == RectangleAreaPosition.MIDDLE_CENTER || position == RectangleAreaPosition.BOTTOM_CENTER;
            if (isLocatedAtLeftArea) {
                horizontalAlignment = HorizontalHudWidgetAlignment.LEFT;
            }
            else if (isLocatedAtCenter) {
                horizontalAlignment = HorizontalHudWidgetAlignment.CENTER;
            }
            else {
                horizontalAlignment = HorizontalHudWidgetAlignment.RIGHT;
            }
        }
        final boolean isLocatedAtTopArea = position == RectangleAreaPosition.TOP_LEFT || position == RectangleAreaPosition.TOP_CENTER || position == RectangleAreaPosition.TOP_RIGHT || position == RectangleAreaPosition.MIDDLE_LEFT || position == RectangleAreaPosition.MIDDLE_CENTER || position == RectangleAreaPosition.MIDDLE_RIGHT;
        switch (horizontalAlignment) {
            case LEFT: {
                return isLocatedAtTopArea ? HudWidgetAnchor.LEFT_TOP : HudWidgetAnchor.LEFT_BOTTOM;
            }
            case CENTER: {
                return isLocatedAtTopArea ? HudWidgetAnchor.CENTER_TOP : HudWidgetAnchor.CENTER_BOTTOM;
            }
            case RIGHT: {
                return isLocatedAtTopArea ? HudWidgetAnchor.RIGHT_TOP : HudWidgetAnchor.RIGHT_BOTTOM;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + String.valueOf(horizontalAlignment));
            }
        }
    }
    
    public float getShiftX(final HudSize size) {
        return this.getShiftX(size.getScaledWidth());
    }
    
    public float getShiftY(final HudSize size) {
        return this.getShiftY(size.getScaledHeight());
    }
    
    public float getShiftX(final float width) {
        switch (this.ordinal()) {
            case 0:
            case 3: {
                return 0.0f;
            }
            case 1:
            case 4: {
                return width / 2.0f;
            }
            case 2:
            case 5: {
                return width;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + String.valueOf(this));
            }
        }
    }
    
    public float getShiftY(final float height) {
        switch (this.ordinal()) {
            case 0:
            case 1:
            case 2: {
                return 0.0f;
            }
            case 3:
            case 4:
            case 5: {
                return height;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + String.valueOf(this));
            }
        }
    }
    
    public float getGapX(final float outer, final float inner) {
        switch (this.ordinal()) {
            case 1:
            case 4: {
                return (outer - inner) / 2.0f;
            }
            case 2:
            case 5: {
                return outer - inner;
            }
            default: {
                return 0.0f;
            }
        }
    }
    
    public boolean isRight() {
        return this == HudWidgetAnchor.RIGHT_TOP || this == HudWidgetAnchor.RIGHT_BOTTOM;
    }
    
    public boolean isLeft() {
        return this == HudWidgetAnchor.LEFT_TOP || this == HudWidgetAnchor.LEFT_BOTTOM;
    }
    
    public boolean isCenter() {
        return this == HudWidgetAnchor.CENTER_TOP || this == HudWidgetAnchor.CENTER_BOTTOM;
    }
}
