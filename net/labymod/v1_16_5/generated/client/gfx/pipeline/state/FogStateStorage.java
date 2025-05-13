// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.generated.client.gfx.pipeline.state;

import net.labymod.core.client.gfx.pipeline.state.StateStorage;

public final class FogStateStorage implements StateStorage<dem.n>
{
    private int mode;
    private float density;
    private float start;
    private float end;
    private boolean enabled;
    
    @Override
    public void store(final dem.n state) {
        this.enabled = state.a.b;
        this.mode = state.b;
        this.density = state.c;
        this.start = state.d;
        this.end = state.e;
    }
    
    @Override
    public void restore() {
        if (this.enabled) {
            dem.A();
        }
        else {
            dem.B();
        }
        dem.o(this.mode);
        dem.a(this.density);
        dem.b(this.start);
        dem.c(this.end);
    }
    
    @Override
    public FogStateStorage copy() {
        final FogStateStorage newObject = new FogStateStorage();
        newObject.mode = this.mode;
        newObject.density = this.density;
        newObject.start = this.start;
        newObject.end = this.end;
        newObject.enabled = this.enabled;
        return newObject;
    }
    
    @Override
    public String toString() {
        return "FogStateStorage[mode=" + this.mode + ", density=" + this.density + ", start=" + this.start + ", end=" + this.end + ", enabled=" + this.enabled;
    }
}
