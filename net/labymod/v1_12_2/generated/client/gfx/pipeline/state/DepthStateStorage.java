// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.generated.client.gfx.pipeline.state;

import net.labymod.core.client.gfx.pipeline.state.StateStorage;

public final class DepthStateStorage implements StateStorage<bus.k>
{
    private int func;
    private boolean writeDepth;
    private boolean enabled;
    
    @Override
    public void store(final bus.k state) {
        this.enabled = state.a.b;
        this.writeDepth = state.b;
        this.func = state.c;
    }
    
    @Override
    public void restore() {
        if (this.enabled) {
            bus.k();
        }
        else {
            bus.j();
        }
        bus.a(this.writeDepth);
        bus.c(this.func);
    }
    
    @Override
    public DepthStateStorage copy() {
        final DepthStateStorage newObject = new DepthStateStorage();
        newObject.func = this.func;
        newObject.writeDepth = this.writeDepth;
        newObject.enabled = this.enabled;
        return newObject;
    }
    
    @Override
    public String toString() {
        return "DepthStateStorage[func=" + this.func + ", writeDepth=" + this.writeDepth + ", enabled=" + this.enabled;
    }
}
