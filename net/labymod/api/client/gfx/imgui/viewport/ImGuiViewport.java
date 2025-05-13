// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.imgui.viewport;

import net.labymod.api.util.math.vector.FloatVector2;

public interface ImGuiViewport
{
    int getId();
    
    void setId(final int p0);
    
    int getFlags();
    
    void setFlags(final int p0);
    
    void addFlags(final int p0);
    
    void removeFlags(final int p0);
    
    boolean hasFlags(final int p0);
    
    default float getX() {
        return this.position().getX();
    }
    
    default float getY() {
        return this.position().getY();
    }
    
    FloatVector2 position();
    
    default float getWidth() {
        return this.size().getX();
    }
    
    default float getHeight() {
        return this.size().getY();
    }
    
    FloatVector2 size();
    
    int getParentViewportId();
    
    void setParentViewportId(final int p0);
    
    long getWindowPointer();
    
    FloatVector2 center();
}
