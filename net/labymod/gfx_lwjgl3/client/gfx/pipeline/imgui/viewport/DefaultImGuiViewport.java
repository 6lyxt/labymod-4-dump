// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.imgui.viewport;

import imgui.ImVec2;
import net.labymod.api.util.math.vector.FloatVector2;
import net.labymod.api.client.gfx.imgui.viewport.ImGuiViewport;

public class DefaultImGuiViewport implements ImGuiViewport
{
    private final imgui.ImGuiViewport viewport;
    
    public DefaultImGuiViewport(final imgui.ImGuiViewport viewport) {
        this.viewport = viewport;
    }
    
    @Override
    public int getId() {
        return this.viewport.getID();
    }
    
    @Override
    public void setId(final int id) {
        this.viewport.setID(id);
    }
    
    @Override
    public int getFlags() {
        return this.viewport.getFlags();
    }
    
    @Override
    public void setFlags(final int flags) {
        this.viewport.setFlags(flags);
    }
    
    @Override
    public void addFlags(final int flags) {
        this.viewport.addFlags(flags);
    }
    
    @Override
    public void removeFlags(final int flags) {
        this.viewport.removeFlags(flags);
    }
    
    @Override
    public boolean hasFlags(final int flags) {
        return this.viewport.hasFlags(flags);
    }
    
    @Override
    public FloatVector2 position() {
        final ImVec2 pos = this.viewport.getPos();
        return new FloatVector2(pos.x, pos.y);
    }
    
    @Override
    public FloatVector2 size() {
        final ImVec2 size = this.viewport.getSize();
        return new FloatVector2(size.x, size.y);
    }
    
    @Override
    public int getParentViewportId() {
        return this.viewport.getParentViewportId();
    }
    
    @Override
    public void setParentViewportId(final int id) {
        this.viewport.setParentViewportId(id);
    }
    
    @Override
    public long getWindowPointer() {
        return this.viewport.getPlatformHandle();
    }
    
    @Override
    public FloatVector2 center() {
        final ImVec2 center = this.viewport.getCenter();
        return new FloatVector2(center.x, center.y);
    }
}
