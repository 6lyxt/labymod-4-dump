// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.generated.client.gfx.pipeline.state;

import net.labymod.core.client.gfx.pipeline.state.StateStorage;

public final class DepthStateStorage implements StateStorage<bfl.j>
{
    private int func;
    private boolean writeDepth;
    private boolean enabled;
    
    @Override
    public void store(final bfl.j state) {
        this.enabled = state.a.b;
        this.writeDepth = state.b;
        this.func = state.c;
    }
    
    @Override
    public void restore() {
        if (this.enabled) {
            bfl.j();
        }
        else {
            bfl.i();
        }
        bfl.a(this.writeDepth);
        bfl.c(this.func);
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
