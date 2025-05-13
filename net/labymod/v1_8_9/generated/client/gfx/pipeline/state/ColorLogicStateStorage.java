// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.generated.client.gfx.pipeline.state;

import net.labymod.core.client.gfx.pipeline.state.StateStorage;

public final class ColorLogicStateStorage implements StateStorage<bfl.f>
{
    private int op;
    private boolean enabled;
    
    @Override
    public void store(final bfl.f state) {
        this.enabled = state.a.b;
        this.op = state.b;
    }
    
    @Override
    public void restore() {
        if (this.enabled) {
            bfl.u();
        }
        else {
            bfl.v();
        }
        bfl.f(this.op);
    }
    
    @Override
    public ColorLogicStateStorage copy() {
        final ColorLogicStateStorage newObject = new ColorLogicStateStorage();
        newObject.op = this.op;
        newObject.enabled = this.enabled;
        return newObject;
    }
    
    @Override
    public String toString() {
        return "ColorLogicStateStorage[op=" + this.op + ", enabled=" + this.enabled;
    }
}
