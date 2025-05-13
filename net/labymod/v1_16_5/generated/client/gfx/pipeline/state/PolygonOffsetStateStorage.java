// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.generated.client.gfx.pipeline.state;

import net.labymod.core.client.gfx.pipeline.state.StateStorage;

public final class PolygonOffsetStateStorage implements StateStorage<dem.p>
{
    private boolean fillEnabled;
    private float units;
    private boolean lineEnabled;
    private float factor;
    
    @Override
    public void store(final dem.p state) {
        this.fillEnabled = state.a.b;
        this.lineEnabled = state.b.b;
        this.factor = state.c;
        this.units = state.d;
    }
    
    @Override
    public void restore() {
        if (this.fillEnabled) {
            dem.E();
        }
        else {
            dem.F();
        }
        dem.a(this.factor, this.units);
    }
    
    @Override
    public PolygonOffsetStateStorage copy() {
        final PolygonOffsetStateStorage newObject = new PolygonOffsetStateStorage();
        newObject.fillEnabled = this.fillEnabled;
        newObject.units = this.units;
        newObject.lineEnabled = this.lineEnabled;
        newObject.factor = this.factor;
        return newObject;
    }
    
    @Override
    public String toString() {
        return "PolygonOffsetStateStorage[fillEnabled=" + this.fillEnabled + ", units=" + this.units + ", lineEnabled=" + this.lineEnabled + ", factor=" + this.factor;
    }
}
