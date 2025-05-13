// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.generated.client.gfx.pipeline.state;

import net.labymod.core.client.gfx.pipeline.state.StateStorage;

public final class AlphaStateStorage implements StateStorage<bfl.a>
{
    private float ref;
    private int func;
    private boolean enabled;
    
    @Override
    public void store(final bfl.a state) {
        this.enabled = state.a.b;
        this.func = state.b;
        this.ref = state.c;
    }
    
    @Override
    public void restore() {
        if (this.enabled) {
            bfl.d();
        }
        else {
            bfl.c();
        }
        bfl.a(this.func, this.ref);
    }
    
    @Override
    public AlphaStateStorage copy() {
        final AlphaStateStorage newObject = new AlphaStateStorage();
        newObject.ref = this.ref;
        newObject.func = this.func;
        newObject.enabled = this.enabled;
        return newObject;
    }
    
    @Override
    public String toString() {
        return "AlphaStateStorage[ref=" + this.ref + ", func=" + this.func + ", enabled=" + this.enabled;
    }
}
