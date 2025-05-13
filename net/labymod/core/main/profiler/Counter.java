// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.profiler;

import net.labymod.api.client.gfx.imgui.LabyImGui;

public class Counter
{
    private final int type;
    private final String name;
    private int count;
    
    public Counter(final int type, final String name) {
        this.type = type;
        this.name = name;
    }
    
    public void update() {
        ++this.count;
    }
    
    public void reset() {
        this.count = 0;
    }
    
    public int getType() {
        return this.type;
    }
    
    public int getCount() {
        return this.count;
    }
    
    public void renderImGui() {
        LabyImGui.keyValuePair(this.name, this.getCount());
    }
}
