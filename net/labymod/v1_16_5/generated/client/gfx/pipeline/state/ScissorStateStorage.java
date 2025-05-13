// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.generated.client.gfx.pipeline.state;

import net.labymod.core.client.gfx.pipeline.state.StateStorage;

public final class ScissorStateStorage implements StateStorage<dem.q>
{
    private boolean enabled;
    
    @Override
    public void store(final dem.q state) {
        this.enabled = state.a.b;
    }
    
    @Override
    public void restore() {
        if (this.enabled) {
            dem.k();
        }
        else {
            dem.j();
        }
    }
    
    @Override
    public ScissorStateStorage copy() {
        final ScissorStateStorage newObject = new ScissorStateStorage();
        newObject.enabled = this.enabled;
        return newObject;
    }
    
    @Override
    public String toString() {
        return "ScissorStateStorage[enabled=" + this.enabled;
    }
}
