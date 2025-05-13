// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.generated.client.gfx.pipeline.state;

import net.labymod.core.client.gfx.pipeline.state.StateStorage;

public final class ColorLogicStateStorage implements StateStorage<dem.e>
{
    private int op;
    private boolean enabled;
    
    @Override
    public void store(final dem.e state) {
        this.enabled = state.a.b;
        this.op = state.b;
    }
    
    @Override
    public void restore() {
        if (this.enabled) {
            dem.I();
        }
        else {
            dem.J();
        }
        dem.p(this.op);
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
