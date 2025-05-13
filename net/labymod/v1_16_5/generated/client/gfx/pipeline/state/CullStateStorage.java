// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.generated.client.gfx.pipeline.state;

import net.labymod.core.client.gfx.pipeline.state.StateStorage;

public final class CullStateStorage implements StateStorage<dem.h>
{
    private int mode;
    private boolean enabled;
    
    @Override
    public void store(final dem.h state) {
        this.enabled = state.a.b;
        this.mode = state.b;
    }
    
    @Override
    public void restore() {
        if (this.enabled) {
            dem.C();
        }
        else {
            dem.D();
        }
    }
    
    @Override
    public CullStateStorage copy() {
        final CullStateStorage newObject = new CullStateStorage();
        newObject.mode = this.mode;
        newObject.enabled = this.enabled;
        return newObject;
    }
    
    @Override
    public String toString() {
        return "CullStateStorage[mode=" + this.mode + ", enabled=" + this.enabled;
    }
}
