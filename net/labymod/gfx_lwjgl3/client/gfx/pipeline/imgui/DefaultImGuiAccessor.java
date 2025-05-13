// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.imgui;

import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.resources.texture.Texture;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.imgui.element.BoundsElement;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import imgui.ImDrawList;
import net.labymod.api.util.math.vector.FloatVector2;
import imgui.ImVec2;
import net.labymod.api.util.color.format.ColorFormat;
import imgui.type.ImBoolean;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.imgui.type.ImGuiBooleanType;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.imgui.viewport.DefaultImGuiViewport;
import net.labymod.api.client.gfx.imgui.viewport.ImGuiViewport;
import imgui.ImGui;
import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gfx.imgui.ImGuiAccessor;

@Singleton
@Implements(ImGuiAccessor.class)
public class DefaultImGuiAccessor implements ImGuiAccessor
{
    private final TextureRepository textureRepository;
    
    public DefaultImGuiAccessor(final TextureRepository textureRepository) {
        this.textureRepository = textureRepository;
    }
    
    @Override
    public void setNextWindowPos(final float x, final float y, final int conditions) {
        ImGui.setNextWindowPos(x, y, conditions);
    }
    
    @Override
    public void setNextWindowSize(final float width, final float height, final int conditions) {
        ImGui.setNextWindowSize(width, height, conditions);
    }
    
    @Override
    public void setNextWindowPosAndSize(final float x, final float y, final float width, final float height) {
        this.setNextWindowPos(x, y, 4);
        this.setNextWindowSize(width, height, 4);
    }
    
    @Override
    public ImGuiViewport getMainViewport() {
        return new DefaultImGuiViewport(ImGui.getMainViewport());
    }
    
    @Override
    public ImGuiViewport getWindowViewport() {
        return new DefaultImGuiViewport(ImGui.getWindowViewport());
    }
    
    @Override
    public boolean beginWindow(final String title) {
        return ImGui.begin(title);
    }
    
    @Override
    public boolean beginWindow(final String title, final ImGuiBooleanType open, int windowFlags) {
        if (Laby.labyAPI().minecraft().isMouseLocked()) {
            windowFlags |= 0x60204;
        }
        boolean windowOpened;
        if (open == null) {
            windowOpened = ImGui.begin(title, windowFlags);
        }
        else {
            windowOpened = ImGui.begin(title, (ImBoolean)open.asImGuiType(), windowFlags);
        }
        final imgui.ImGuiViewport viewport = ImGui.getWindowViewport();
        viewport.setFlags(512);
        return windowOpened;
    }
    
    @Override
    public void text(final String text) {
        ImGui.text(text);
    }
    
    @Override
    public void text(final String text, final int argb) {
        ImGui.textColored(ColorFormat.ARGB32.packTo(ColorFormat.ABGR32, argb), text);
    }
    
    @Override
    public void text(final String text, final int red, final int green, final int blue, final int alpha) {
        ImGui.textColored(red, green, blue, alpha, text);
    }
    
    @Override
    public void text(final String text, final float red, final float green, final float blue, final float alpha) {
        ImGui.textColored(red, green, blue, alpha, text);
    }
    
    @Override
    public void text(final String text, final int normalArgb, final int hoverArgb) {
        ImGui.beginGroup();
        final ImVec2 textSize = ImGui.calcTextSize(text);
        ImGui.invisibleButton("##" + text, textSize.x, textSize.y);
        ImGui.setCursorPosY(ImGui.getCursorPosY() - textSize.y - 4.0f);
        if (ImGui.isItemHovered()) {
            this.text(text, hoverArgb);
        }
        else {
            this.text(text, normalArgb);
        }
        ImGui.endGroup();
    }
    
    @Override
    public FloatVector2 calculateTextSize(final String text) {
        final ImVec2 textSize = ImGui.calcTextSize(text);
        return new FloatVector2(textSize.x, textSize.y);
    }
    
    @Override
    public boolean colorPicker4(final String label, final float[] colors) {
        return ImGui.colorPicker4(label, colors, 16777376);
    }
    
    @Override
    public void addRectangle(final float minX, final float minY, final float maxX, final float maxY, final int color, final float rounding) {
        final ImDrawList drawList = ImGui.getForegroundDrawList();
        final float itemMinX = ImGui.getCursorScreenPosX();
        final float itemMinY = ImGui.getCursorScreenPosY();
        final float mx = minX + itemMinX;
        final float my = minY + itemMinY;
        drawList.addRectFilled(mx, my, mx + maxX, my + maxY, color, rounding);
        drawList.addRect(mx, my, mx + maxX, my + maxY, ColorFormat.ABGR32.pack(100, 100, 100, 255), rounding);
    }
    
    @Override
    public void separator() {
        ImGui.separator();
    }
    
    @Override
    public boolean collapsingHeader(final String label) {
        return ImGui.collapsingHeader(label);
    }
    
    @Override
    public boolean collapsingHeader(final String label, final int nodeFlags) {
        return ImGui.collapsingHeader(label, nodeFlags);
    }
    
    @Override
    public boolean collapsingHeader(final String label, final ImGuiBooleanType visible) {
        return ImGui.collapsingHeader(label, (ImBoolean)visible.asImGuiType());
    }
    
    @Override
    public boolean collapsingHeader(final String label, final ImGuiBooleanType visible, final int nodeFlags) {
        return ImGui.collapsingHeader(label, (ImBoolean)visible.asImGuiType(), nodeFlags);
    }
    
    @Override
    public void pushTree() {
        ImGui.treePush();
    }
    
    @Override
    public void popTree() {
        ImGui.treePop();
    }
    
    @Override
    public boolean button(final String label) {
        return ImGui.button(label);
    }
    
    @Override
    public boolean button(final String label, final float width, final float height) {
        return ImGui.button(label, width, height);
    }
    
    @Override
    public boolean checkbox(final String label, final ImGuiBooleanType active) {
        return ImGui.checkbox(label, (ImBoolean)active.asImGuiType());
    }
    
    @Override
    public void beginTooltip() {
        ImGui.beginTooltip();
    }
    
    @Override
    public void setTooltip(final String text) {
        ImGui.setTooltip(text);
    }
    
    @Override
    public void endTooltip() {
        ImGui.endTooltip();
    }
    
    @Override
    public boolean isItemHovered() {
        return ImGui.isItemHovered();
    }
    
    @Override
    public boolean isItemClicked() {
        return ImGui.isItemClicked();
    }
    
    @Override
    public void sameLine() {
        ImGui.sameLine();
    }
    
    @Override
    public void sameLine(final float offsetFromStartX) {
        ImGui.sameLine(offsetFromStartX);
    }
    
    @Override
    public void sameLine(final float offsetFromStartX, final float spacing) {
        ImGui.sameLine(offsetFromStartX, spacing);
    }
    
    @Override
    public void beginGroup() {
        ImGui.beginGroup();
    }
    
    @Override
    public void endGroup() {
        ImGui.endGroup();
    }
    
    @Override
    public void endWindow() {
        ImGui.end();
    }
    
    @Override
    public boolean treeNodeEx(final String label, final int flags) {
        return ImGui.treeNodeEx(label, flags);
    }
    
    @Override
    public void setNextWindowSizeConstraints(final float minWidth, final float minHeight, final float maxWidth, final float maxHeight) {
        ImGui.setNextWindowSizeConstraints(minWidth, minHeight, maxWidth, maxHeight);
    }
    
    @Override
    public void renderBounds(final Bounds bounds) {
        BoundsElement.renderBounds(bounds);
    }
    
    @Override
    public void pushId(final int id) {
        ImGui.pushID(id);
    }
    
    @Override
    public void pushId(final String id) {
        ImGui.pushID(id);
    }
    
    @Override
    public void popId() {
        ImGui.popID();
    }
    
    @Override
    public void pushStyleColor(final int imGuiColor, final int color) {
        ImGui.pushStyleColor(imGuiColor, color);
    }
    
    @Override
    public void pushStyleColor(final int imGuiColor, final int red, final int green, final int blue, final int alpha) {
        ImGui.pushStyleColor(imGuiColor, red, green, blue, alpha);
    }
    
    @Override
    public void pushStyleColor(final int imGuiColor, final float red, final float green, final float blue, final float alpha) {
        ImGui.pushStyleColor(imGuiColor, red, green, blue, alpha);
    }
    
    @Override
    public void popStyleColor() {
        ImGui.popStyleColor();
    }
    
    @Override
    public FloatVector2 getWindowContentRegionMin() {
        final ImVec2 windowContentRegionMin = ImGui.getWindowContentRegionMin();
        return new FloatVector2(windowContentRegionMin.x, windowContentRegionMin.y);
    }
    
    @Override
    public FloatVector2 getWindowContentRegionMax() {
        final ImVec2 windowContentRegionMax = ImGui.getWindowContentRegionMax();
        return new FloatVector2(windowContentRegionMax.x, windowContentRegionMax.y);
    }
    
    @Override
    public FloatVector2 getWindowPosition() {
        final ImVec2 pos = ImGui.getWindowPos();
        return new FloatVector2(pos.x, pos.y);
    }
    
    @Override
    public boolean beginCombo(final String label, final String previewValue) {
        return this.beginCombo(label, previewValue, 0);
    }
    
    @Override
    public boolean beginCombo(final String label, final String previewValue, final int flags) {
        return ImGui.beginCombo(label, previewValue, flags);
    }
    
    @Override
    public void endCombo() {
        ImGui.endCombo();
    }
    
    @Override
    public void setItemDefaultFocus() {
        ImGui.setItemDefaultFocus();
    }
    
    @Override
    public boolean selectable(final String label, final boolean selected) {
        return ImGui.selectable(label, selected);
    }
    
    @Override
    public void addRectangleFilled(final float minX, final float minY, final float maxX, final float maxY, final int color, final float rounding) {
        final ImDrawList drawList = ImGui.getWindowDrawList();
        drawList.addRectFilled(minX, minY, maxX, maxY, color, rounding);
    }
    
    @Override
    public void image(final int textureId, final float sizeX, final float sizeY) {
        ImGui.image(textureId, sizeX, sizeY);
    }
    
    @Override
    public void image(final ResourceLocation location, final float sizeX, final float sizeY) {
        final Texture texture = this.textureRepository.getTexture(location);
        if (texture == null) {
            return;
        }
        this.image(texture.getTextureId(), sizeX, sizeY);
    }
    
    @Override
    public void image(final int textureId, final float sizeX, final float sizeY, final float minU, final float minV) {
        ImGui.image(textureId, sizeX, sizeY, minU, minV);
    }
    
    @Override
    public void image(final int textureId, final float sizeX, final float sizeY, final float minU, final float minV, final float maxU, final float maxV) {
        ImGui.image(textureId, sizeX, sizeY, minU, minV, maxU, maxV);
    }
    
    @Override
    public void image(final int textureId, final float sizeX, final float sizeY, final float minU, final float minV, final float maxU, final float maxV, final int color) {
        final ColorFormat format = ColorFormat.ARGB32;
        final float red = (float)format.red(color);
        final float green = (float)format.green(color);
        final float blue = (float)format.blue(color);
        final float alpha = (float)format.alpha(color);
        ImGui.image(textureId, sizeX, sizeY, minU, minV, maxU, maxV, red, green, blue, alpha);
    }
    
    @Override
    public FloatVector2 getItemRectMin() {
        final ImVec2 min = ImGui.getItemRectMin();
        return new FloatVector2(min.x, min.y);
    }
    
    @Override
    public FloatVector2 getItemRectMax() {
        final ImVec2 max = ImGui.getItemRectMax();
        return new FloatVector2(max.x, max.y);
    }
    
    @Override
    public boolean isKeyPressed(final Key key) {
        return ImGui.isKeyPressed(key.getId());
    }
    
    @Override
    public boolean isKeyPressed(final Key key, final boolean repeat) {
        return ImGui.isKeyPressed(key.getId(), repeat);
    }
    
    @Override
    public boolean isKeyReleased(final Key key) {
        return ImGui.isKeyReleased(key.getId());
    }
    
    @Override
    public boolean isKeyDown(final Key key) {
        return ImGui.isKeyDown(key.getId());
    }
    
    @Override
    public boolean isMouseClicked(final Key mouseButton) {
        return ImGui.isMouseClicked(mouseButton.getId());
    }
    
    @Override
    public boolean isWindowHovered() {
        return ImGui.isWindowHovered();
    }
    
    @Override
    public boolean isWindowFocused() {
        return ImGui.isWindowFocused();
    }
}
