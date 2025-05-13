// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.imgui;

import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.util.math.vector.FloatVector2;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gfx.imgui.type.ImGuiBooleanType;
import net.labymod.api.client.gfx.imgui.viewport.ImGuiViewport;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface ImGuiAccessor
{
    void setNextWindowPos(final float p0, final float p1, final int p2);
    
    void setNextWindowSize(final float p0, final float p1, final int p2);
    
    void setNextWindowPosAndSize(final float p0, final float p1, final float p2, final float p3);
    
    ImGuiViewport getMainViewport();
    
    ImGuiViewport getWindowViewport();
    
    boolean beginWindow(final String p0);
    
    boolean beginWindow(final String p0, @Nullable final ImGuiBooleanType p1, final int p2);
    
    void text(final String p0);
    
    void text(final String p0, final int p1);
    
    void text(final String p0, final int p1, final int p2, final int p3, final int p4);
    
    void text(final String p0, final float p1, final float p2, final float p3, final float p4);
    
    void text(final String p0, final int p1, final int p2);
    
    FloatVector2 calculateTextSize(final String p0);
    
    boolean colorPicker4(final String p0, final float[] p1);
    
    void addRectangle(final float p0, final float p1, final float p2, final float p3, final int p4, final float p5);
    
    void separator();
    
    boolean collapsingHeader(final String p0);
    
    boolean collapsingHeader(final String p0, final int p1);
    
    boolean collapsingHeader(final String p0, final ImGuiBooleanType p1);
    
    boolean collapsingHeader(final String p0, final ImGuiBooleanType p1, final int p2);
    
    void pushTree();
    
    void popTree();
    
    boolean button(final String p0);
    
    boolean button(final String p0, final float p1, final float p2);
    
    boolean checkbox(final String p0, final ImGuiBooleanType p1);
    
    void beginTooltip();
    
    void setTooltip(final String p0);
    
    void endTooltip();
    
    boolean isItemHovered();
    
    void sameLine();
    
    void sameLine(final float p0);
    
    void sameLine(final float p0, final float p1);
    
    boolean isItemClicked();
    
    void beginGroup();
    
    void endGroup();
    
    void endWindow();
    
    boolean treeNodeEx(final String p0, final int p1);
    
    void setNextWindowSizeConstraints(final float p0, final float p1, final float p2, final float p3);
    
    void renderBounds(final Bounds p0);
    
    void pushId(final int p0);
    
    void pushId(final String p0);
    
    void popId();
    
    void pushStyleColor(final int p0, final int p1);
    
    void pushStyleColor(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void pushStyleColor(final int p0, final float p1, final float p2, final float p3, final float p4);
    
    void popStyleColor();
    
    FloatVector2 getWindowContentRegionMin();
    
    FloatVector2 getWindowContentRegionMax();
    
    FloatVector2 getWindowPosition();
    
    boolean beginCombo(final String p0, final String p1);
    
    boolean beginCombo(final String p0, final String p1, final int p2);
    
    void endCombo();
    
    void setItemDefaultFocus();
    
    boolean selectable(final String p0, final boolean p1);
    
    void addRectangleFilled(final float p0, final float p1, final float p2, final float p3, final int p4, final float p5);
    
    void image(final int p0, final float p1, final float p2);
    
    void image(final ResourceLocation p0, final float p1, final float p2);
    
    void image(final int p0, final float p1, final float p2, final float p3, final float p4);
    
    void image(final int p0, final float p1, final float p2, final float p3, final float p4, final float p5, final float p6);
    
    void image(final int p0, final float p1, final float p2, final float p3, final float p4, final float p5, final float p6, final int p7);
    
    FloatVector2 getItemRectMin();
    
    FloatVector2 getItemRectMax();
    
    boolean isKeyPressed(final Key p0);
    
    boolean isKeyPressed(final Key p0, final boolean p1);
    
    boolean isKeyReleased(final Key p0);
    
    boolean isKeyDown(final Key p0);
    
    boolean isMouseClicked(final Key p0);
    
    boolean isWindowHovered();
    
    boolean isWindowFocused();
}
