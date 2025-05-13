// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.imgui.element;

import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.WidgetStyleSheetUpdater;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.OffsetSide;
import net.labymod.api.util.math.vector.FloatVector2;
import net.labymod.api.client.gfx.imgui.LabyImGui;
import imgui.ImDrawList;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import imgui.ImVec2;
import imgui.ImGui;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;

public final class BoundsElement
{
    private static final float BOUNDS_SPACE = 25.0f;
    private static final float BOUNDS_INNER_HEIGHT = 30.0f;
    
    private BoundsElement() {
    }
    
    public static void renderBounds(final Bounds bounds) {
        final ImVec2 pos = ImGui.getWindowPos();
        final ImVec2 windowContentRegionMin = ImGui.getWindowContentRegionMin();
        final ImVec2 windowContentRegionMax = ImGui.getWindowContentRegionMax();
        final float windowContentWidth = windowContentRegionMax.x - windowContentRegionMin.x;
        final float windowContentHeight = windowContentRegionMax.y - windowContentRegionMin.y;
        final float x = pos.x + windowContentRegionMin.x;
        final float y = pos.y + windowContentRegionMin.y;
        final ImVec2 center = new ImVec2(x + windowContentWidth * 0.5f, y + windowContentHeight * 0.5f);
        final String positionText = toPrettyValue(bounds.getX(BoundsType.OUTER), true) + ", " + toPrettyValue(bounds.getY(BoundsType.OUTER), true);
        final float boundsInnerWidth = windowContentWidth / 2.0f;
        final ImDrawList drawList = ImGui.getForegroundDrawList();
        renderText(drawList, center, positionText, x, y, boundsInnerWidth, 6.0f, -8912897);
        renderText(drawList, center, "margin", x, y, boundsInnerWidth, 4.0f, getColorByType(BoundsType.OUTER));
        renderText(drawList, center, "border", x, y, boundsInnerWidth, 2.0f, getColorByType(BoundsType.BORDER));
        renderText(drawList, center, "padding", x, y, boundsInnerWidth, 1.0f, getColorByType(BoundsType.MIDDLE));
        renderBounds(drawList, center, boundsInnerWidth + 150.0f, 180.0f, bounds, BoundsType.OUTER, 25.0f);
        renderBounds(drawList, center, boundsInnerWidth + 100.0f, 130.0f, bounds, BoundsType.BORDER, 25.0f);
        renderBounds(drawList, center, boundsInnerWidth + 50.0f, 80.0f, bounds, BoundsType.MIDDLE, 25.0f);
        renderBounds(drawList, center, boundsInnerWidth, 30.0f, bounds, BoundsType.INNER, 25.0f);
    }
    
    private static void renderText(final ImDrawList drawList, final ImVec2 center, final String text, final float x, final float y, final float boundsInnerWidth, final float space, final int color) {
        if (space == 1.0f) {
            final FloatVector2 windowContentRegionMin = LabyImGui.getWindowContentRegionMin();
            final float textY = center.y - 30.0f;
            if (textY >= y - ImGui.getFontSize() / 2.0f) {
                final float textX = center.x - boundsInnerWidth / 2.0f;
                if (textX >= x - windowContentRegionMin.getX() / 2.0f) {
                    drawList.addText(textX, textY, color, text);
                }
            }
            return;
        }
        final FloatVector2 windowContentRegionMin = LabyImGui.getWindowContentRegionMin();
        final float textY = center.y - (30.0f + 25.0f * space) / 2.0f - 14.0f;
        if (textY >= y - ImGui.getFontSize() / 2.0f) {
            final float textX = center.x - (boundsInnerWidth + 25.0f * space) / 2.0f;
            if (textX >= x - windowContentRegionMin.getX() / 2.0f) {
                drawList.addText(textX, textY, color, text);
            }
        }
    }
    
    private static void renderBounds(final ImDrawList drawList, final ImVec2 center, final float width, final float height, final Bounds bounds, final BoundsType type, final float space) {
        final float offsetX = center.x - width / 2.0f;
        final float offsetY = center.y - height / 2.0f;
        final ImVec2 pos = ImGui.getWindowPos();
        final ImVec2 windowContentRegionMin = ImGui.getWindowContentRegionMin();
        final float x = pos.x + windowContentRegionMin.x;
        final float y = pos.y + windowContentRegionMin.y;
        if (offsetX <= x - windowContentRegionMin.x / 2.0f) {
            return;
        }
        if (offsetY <= y - ImGui.getFontSize() / 2.0f) {
            return;
        }
        drawList.addRect(offsetX, offsetY, offsetX + width, offsetY + height, getColorByType(type), 0.0f);
        if (type == BoundsType.INNER) {
            renderLabel(drawList, center, bounds, BoundsType.INNER, null, 1.0f, 0.0f);
        }
        else {
            renderLabel(drawList, center, bounds, type, OffsetSide.LEFT, -width / 2.0f + space / 2.0f, 0.0f);
            renderLabel(drawList, center, bounds, type, OffsetSide.BOTTOM, 1.0f, height / 2.0f - space / 2.0f);
            renderLabel(drawList, center, bounds, type, OffsetSide.RIGHT, width / 2.0f - space / 2.0f, 0.0f);
            renderLabel(drawList, center, bounds, type, OffsetSide.TOP, 1.0f, -height / 2.0f + space / 2.0f);
        }
    }
    
    private static void renderLabel(final ImDrawList drawList, final ImVec2 center, final Bounds bounds, final BoundsType type, @Nullable final OffsetSide side, final float offsetX, final float offsetY) {
        String labelContent;
        if (side == null) {
            labelContent = toPrettyValue(bounds.getWidth(type), true) + " x " + toPrettyValue(bounds.getHeight(type), true);
        }
        else {
            final WidgetStyleSheetUpdater updater = bounds.getUpdater();
            float offset = 0.0f;
            if (type == BoundsType.BORDER) {
                offset = updater.getBorder(side);
            }
            else if (type == BoundsType.MIDDLE) {
                offset = updater.getPadding(side);
            }
            else {
                offset = updater.getMargin(side);
            }
            labelContent = toPrettyValue(offset, false);
        }
        final ImVec2 textSize = ImGui.calcTextSize(labelContent);
        float textX = center.x + offsetX;
        float textY = center.y + offsetY;
        textX -= textSize.x / 2.0f;
        textY -= textSize.y / 2.0f;
        drawList.addText(textX, textY, getColorByType(type), labelContent);
    }
    
    private static int getColorByType(final BoundsType type) {
        int color;
        if (type == BoundsType.INNER) {
            color = -11043425;
        }
        else if (type == BoundsType.MIDDLE) {
            color = -10256809;
        }
        else if (type == BoundsType.BORDER) {
            color = -4807309;
        }
        else {
            color = -5209260;
        }
        return ColorFormat.ARGB32.packTo(ColorFormat.ABGR32, color);
    }
    
    private static String toPrettyValue(final float value, final boolean showZero) {
        if (Float.isNaN(value)) {
            return "NaN";
        }
        if (value == 0.0f && !showZero) {
            return "-";
        }
        if ((int)value == value) {
            return String.valueOf((int)value);
        }
        return String.valueOf((int)(value * 10.0f) / 10.0f);
    }
}
