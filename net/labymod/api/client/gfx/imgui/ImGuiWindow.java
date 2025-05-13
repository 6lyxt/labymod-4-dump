// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.imgui;

import net.labymod.api.util.math.vector.FloatVector2;
import net.labymod.api.client.gfx.imgui.viewport.ImGuiViewport;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gfx.imgui.type.ImGuiBooleanType;

public abstract class ImGuiWindow
{
    private final String title;
    private final ImGuiBooleanType visible;
    private final int flags;
    
    public ImGuiWindow(final String title, @Nullable final ImGuiBooleanType visible, final int flags) {
        this.title = title;
        this.visible = visible;
        int newFlags = flags;
        newFlags |= 0x2000;
        this.flags = newFlags;
    }
    
    public final void render() {
        this.prepareWindow();
        final boolean opened = LabyImGui.beginWindow(this.title, this.visible, this.flags);
        if (this.showContentIfCollapsed() || opened) {
            this.renderContent();
        }
        LabyImGui.endWindow();
    }
    
    public ImGuiBooleanType getVisible() {
        return this.visible;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    protected void prepareWindow() {
        final ImGuiViewport windowViewport = LabyImGui.getWindowViewport();
        final FloatVector2 windowPosition = windowViewport.position();
        final float x = windowPosition.getX();
        final float y = windowPosition.getY();
        LabyImGui.setNextWindowPosAndSize(x + windowViewport.getWidth() + 5.0f, y, 200.0f, 300.0f);
    }
    
    protected abstract void renderContent();
    
    protected boolean showContentIfCollapsed() {
        return false;
    }
}
