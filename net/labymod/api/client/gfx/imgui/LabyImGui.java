// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.imgui;

import net.labymod.api.Laby;
import net.labymod.api.client.gfx.imgui.type.ImGuiIntegerType;
import net.labymod.api.client.gfx.imgui.type.ImGuiLongType;
import net.labymod.api.client.gfx.imgui.type.ImGuiFloatType;
import net.labymod.api.client.gfx.imgui.type.ImGuiDoubleType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.util.math.vector.FloatVector2;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.ColorUtil;
import net.labymod.api.client.component.format.TextColor;
import java.util.function.Supplier;
import net.labymod.api.client.gfx.imgui.type.ImGuiBooleanType;
import net.labymod.api.client.gfx.imgui.viewport.ImGuiViewport;
import net.labymod.api.client.gfx.imgui.type.ImGuiTypeProvider;

public final class LabyImGui
{
    private static final ImGuiAccessor ACCESSOR;
    private static final ImGuiTypeProvider TYPE_PROVIDER;
    
    public static void setNextWindowPos(final float x, final float y, final int conditions) {
        LabyImGui.ACCESSOR.setNextWindowPos(x, y, conditions);
    }
    
    public static void setNextWindowSize(final float width, final float height, final int conditions) {
        LabyImGui.ACCESSOR.setNextWindowSize(width, height, conditions);
    }
    
    public static void setNextWindowPosAndSize(final float x, final float y, final float width, final float height) {
        LabyImGui.ACCESSOR.setNextWindowPosAndSize(x, y, width, height);
    }
    
    public static ImGuiViewport getMainViewport() {
        return LabyImGui.ACCESSOR.getMainViewport();
    }
    
    public static ImGuiViewport getWindowViewport() {
        return LabyImGui.ACCESSOR.getWindowViewport();
    }
    
    public static boolean beginWindow(final String title) {
        return LabyImGui.ACCESSOR.beginWindow(title);
    }
    
    public static boolean beginWindow(final String title, final ImGuiBooleanType open, final int windowFlags) {
        return LabyImGui.ACCESSOR.beginWindow(title, open, windowFlags);
    }
    
    public static void separator() {
        LabyImGui.ACCESSOR.separator();
    }
    
    public static void textWithTooltip(final String text, final Supplier<String> tooltip) {
        text(text);
        if (isItemHovered()) {
            setTooltip(tooltip.get());
        }
    }
    
    public static void keyValuePair(final String key, final int value) {
        keyValuePair(key, Integer.toString(value));
    }
    
    public static void keyValuePair(final String key, final String value) {
        text("  " + key);
        sameLine(0.0f, 0.0f);
        text(": ");
        sameLine(0.0f, 0.0f);
        text(value);
    }
    
    public static void text(final String text) {
        LabyImGui.ACCESSOR.text(text);
    }
    
    public static void text(final String text, final TextColor color) {
        text(text, ColorUtil.adjustedColor(color.getValue()));
    }
    
    public static void text(final String text, final int argb) {
        LabyImGui.ACCESSOR.text(text, argb);
    }
    
    public static void text(final String text, final int red, final int green, final int blue, final int alpha) {
        LabyImGui.ACCESSOR.text(text, red, green, blue, alpha);
    }
    
    public static void text(final String text, final float red, final float green, final float blue, final float alpha) {
        LabyImGui.ACCESSOR.text(text, red, green, blue, alpha);
    }
    
    public static void text(final String text, final int normalArgb, final int hoverArgb) {
        LabyImGui.ACCESSOR.text(text, normalArgb, hoverArgb);
    }
    
    public static boolean colorPicker4(final String label, final float[] colors) {
        return LabyImGui.ACCESSOR.colorPicker4(label, colors);
    }
    
    public static void addRectangle(final float minX, final float minY, final float maxX, final float maxY, final int color, final float rounding) {
        LabyImGui.ACCESSOR.addRectangle(minX, minY, maxX, maxY, color, rounding);
    }
    
    public static void pushTree() {
        LabyImGui.ACCESSOR.pushTree();
    }
    
    public static boolean collapsingHeader(final String label) {
        return LabyImGui.ACCESSOR.collapsingHeader(label);
    }
    
    public static void endWindow() {
        LabyImGui.ACCESSOR.endWindow();
    }
    
    public static boolean collapsingHeader(final String label, final int nodeFlags) {
        return LabyImGui.ACCESSOR.collapsingHeader(label, nodeFlags);
    }
    
    public static boolean collapsingHeader(final String label, final ImGuiBooleanType visible) {
        return LabyImGui.ACCESSOR.collapsingHeader(label, visible);
    }
    
    public static boolean collapsingHeader(final String label, final ImGuiBooleanType visible, final int nodeFlags) {
        return LabyImGui.ACCESSOR.collapsingHeader(label, visible, nodeFlags);
    }
    
    public static void popTree() {
        LabyImGui.ACCESSOR.popTree();
    }
    
    public static boolean button(final String label) {
        return LabyImGui.ACCESSOR.button(label);
    }
    
    public static boolean button(final String label, final float width, final float height) {
        return LabyImGui.ACCESSOR.button(label, width, height);
    }
    
    public static boolean checkbox(final String label, final ImGuiBooleanType active) {
        return LabyImGui.ACCESSOR.checkbox(label, active);
    }
    
    public static void beginTooltip() {
        LabyImGui.ACCESSOR.beginTooltip();
    }
    
    public static void setTooltip(final String text) {
        LabyImGui.ACCESSOR.setTooltip(text);
    }
    
    public static void endTooltip() {
        LabyImGui.ACCESSOR.endTooltip();
    }
    
    public static boolean isItemHovered() {
        return LabyImGui.ACCESSOR.isItemHovered();
    }
    
    public static boolean isItemClicked() {
        return LabyImGui.ACCESSOR.isItemClicked();
    }
    
    public static void sameLine() {
        LabyImGui.ACCESSOR.sameLine();
    }
    
    public static void sameLine(final float offsetFromStartX) {
        LabyImGui.ACCESSOR.sameLine(offsetFromStartX);
    }
    
    public static void sameLine(final float offsetFromStartX, final float spacing) {
        LabyImGui.ACCESSOR.sameLine(offsetFromStartX, spacing);
    }
    
    public static void beginGroup() {
        LabyImGui.ACCESSOR.beginGroup();
    }
    
    public static void endGroup() {
        LabyImGui.ACCESSOR.endGroup();
    }
    
    public static boolean treeNodeEx(final String label, final int flags) {
        return LabyImGui.ACCESSOR.treeNodeEx(label, flags);
    }
    
    public static void image(final int textureId, final float sizeX, final float sizeY) {
        LabyImGui.ACCESSOR.image(textureId, sizeX, sizeY);
    }
    
    public static void image(final ResourceLocation location, final float sizeX, final float sizeY) {
        LabyImGui.ACCESSOR.image(location, sizeX, sizeY);
    }
    
    public static void image(final int textureId, final float sizeX, final float sizeY, final float minU, final float minV) {
        LabyImGui.ACCESSOR.image(textureId, sizeX, sizeY, minU, minV);
    }
    
    public static void image(final int textureId, final float sizeX, final float sizeY, final float minU, final float minV, final float maxU, final float maxV) {
        LabyImGui.ACCESSOR.image(textureId, sizeX, sizeY, minU, minV, maxU, maxV);
    }
    
    public static void image(final int textureId, final float sizeX, final float sizeY, final float minU, final float minV, final float maxU, final float maxV, final int color) {
        LabyImGui.ACCESSOR.image(textureId, sizeX, sizeY, minU, minV, maxU, maxV, color);
    }
    
    public static void renderBounds(final Bounds bounds) {
        LabyImGui.ACCESSOR.renderBounds(bounds);
    }
    
    public static void setNextWindowSizeConstraints(final float minWidth, final float minHeight, final float maxWidth, final float maxHeight) {
        LabyImGui.ACCESSOR.setNextWindowSizeConstraints(minWidth, minHeight, maxWidth, maxHeight);
    }
    
    public static FloatVector2 calculateTextSize(final String text) {
        return LabyImGui.ACCESSOR.calculateTextSize(text);
    }
    
    public static void pushId(final int id) {
        LabyImGui.ACCESSOR.pushId(id);
    }
    
    public static void pushId(final String id) {
        LabyImGui.ACCESSOR.pushId(id);
    }
    
    public static void popId() {
        LabyImGui.ACCESSOR.popId();
    }
    
    public static void pushStyleColor(final int imGuiColor, final int color) {
        LabyImGui.ACCESSOR.pushStyleColor(imGuiColor, color);
    }
    
    public static void pushStyleColor(final int imGuiColor, final int red, final int green, final int blue, final int alpha) {
        LabyImGui.ACCESSOR.pushStyleColor(imGuiColor, red, green, blue, alpha);
    }
    
    public static void pushStyleColor(final int imGuiColor, final float red, final float green, final float blue, final float alpha) {
        LabyImGui.ACCESSOR.pushStyleColor(imGuiColor, red, green, blue, alpha);
    }
    
    public static void popStyleColor() {
        LabyImGui.ACCESSOR.popStyleColor();
    }
    
    public static FloatVector2 getWindowContentRegionMin() {
        return LabyImGui.ACCESSOR.getWindowContentRegionMin();
    }
    
    public static FloatVector2 getWindowContentRegionMax() {
        return LabyImGui.ACCESSOR.getWindowContentRegionMax();
    }
    
    public static FloatVector2 getWindowPosition() {
        return LabyImGui.ACCESSOR.getWindowPosition();
    }
    
    public static boolean beginCombo(final String label, final String previewValue) {
        return LabyImGui.ACCESSOR.beginCombo(label, previewValue);
    }
    
    public static boolean beginCombo(final String label, final String previewValue, final int flags) {
        return LabyImGui.ACCESSOR.beginCombo(label, previewValue, flags);
    }
    
    public static void endCombo() {
        LabyImGui.ACCESSOR.endCombo();
    }
    
    public static void setItemDefaultFocus() {
        LabyImGui.ACCESSOR.setItemDefaultFocus();
    }
    
    public static boolean selectable(final String label, final boolean selected) {
        return LabyImGui.ACCESSOR.selectable(label, selected);
    }
    
    public static void addRectangleFilled(final float minX, final float minY, final float maxX, final float maxY, final int color, final float rounding) {
        LabyImGui.ACCESSOR.addRectangleFilled(minX, minY, maxX, maxY, color, rounding);
    }
    
    public static FloatVector2 getItemRectMin() {
        return LabyImGui.ACCESSOR.getItemRectMin();
    }
    
    public static FloatVector2 getItemRectMax() {
        return LabyImGui.ACCESSOR.getItemRectMax();
    }
    
    public static boolean isKeyPressed(final Key key) {
        return LabyImGui.ACCESSOR.isKeyPressed(key);
    }
    
    public static boolean isKeyPressed(final Key key, final boolean repeat) {
        return LabyImGui.ACCESSOR.isKeyPressed(key, repeat);
    }
    
    public static boolean isKeyReleased(final Key key) {
        return LabyImGui.ACCESSOR.isKeyReleased(key);
    }
    
    public static boolean isKeyDown(final Key key) {
        return LabyImGui.ACCESSOR.isKeyDown(key);
    }
    
    public static boolean isMouseClicked(final Key mouseButton) {
        return LabyImGui.ACCESSOR.isMouseClicked(mouseButton);
    }
    
    public static boolean isWindowHovered() {
        return LabyImGui.ACCESSOR.isWindowHovered();
    }
    
    public static boolean isWindowFocused() {
        return LabyImGui.ACCESSOR.isWindowFocused();
    }
    
    public static ImGuiBooleanType booleanType() {
        return LabyImGui.TYPE_PROVIDER.booleanType();
    }
    
    public static ImGuiDoubleType doubleType(final double value) {
        return LabyImGui.TYPE_PROVIDER.doubleType(value);
    }
    
    public static ImGuiBooleanType booleanType(final boolean value) {
        return LabyImGui.TYPE_PROVIDER.booleanType(value);
    }
    
    public static ImGuiBooleanType booleanType(final ImGuiBooleanType other) {
        return LabyImGui.TYPE_PROVIDER.booleanType(other);
    }
    
    public static ImGuiDoubleType doubleType() {
        return LabyImGui.TYPE_PROVIDER.doubleType();
    }
    
    public static ImGuiDoubleType doubleType(final ImGuiDoubleType other) {
        return LabyImGui.TYPE_PROVIDER.doubleType(other);
    }
    
    public static ImGuiFloatType floatType() {
        return LabyImGui.TYPE_PROVIDER.floatType();
    }
    
    public static ImGuiLongType longType() {
        return LabyImGui.TYPE_PROVIDER.longType();
    }
    
    public static ImGuiFloatType floatType(final float value) {
        return LabyImGui.TYPE_PROVIDER.floatType(value);
    }
    
    public static ImGuiIntegerType integerType(final ImGuiIntegerType other) {
        return LabyImGui.TYPE_PROVIDER.integerType(other);
    }
    
    public static ImGuiLongType longType(final long value) {
        return LabyImGui.TYPE_PROVIDER.longType(value);
    }
    
    public static ImGuiFloatType floatType(final ImGuiFloatType other) {
        return LabyImGui.TYPE_PROVIDER.floatType(other);
    }
    
    public static ImGuiIntegerType integerType() {
        return LabyImGui.TYPE_PROVIDER.integerType();
    }
    
    public static ImGuiIntegerType integerType(final int value) {
        return LabyImGui.TYPE_PROVIDER.integerType(value);
    }
    
    public static ImGuiLongType longType(final ImGuiLongType other) {
        return LabyImGui.TYPE_PROVIDER.longType(other);
    }
    
    static {
        ACCESSOR = Laby.references().imGuiAccessor();
        TYPE_PROVIDER = Laby.references().imGuiTypeProvider();
    }
}
