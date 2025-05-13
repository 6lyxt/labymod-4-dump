// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.generated.client.gfx.pipeline.state;

import com.mojang.blaze3d.platform.GlStateManager;
import net.labymod.core.client.gfx.pipeline.state.StateStorage;

public final class PolygonOffsetStateStorage implements StateStorage<GlStateManager.h>
{
    private boolean fillEnabled;
    private float units;
    private boolean lineEnabled;
    private float factor;
    
    @Override
    public void store(final GlStateManager.h state) {
        this.fillEnabled = state.a.b;
        this.lineEnabled = state.b.b;
        this.factor = state.c;
        this.units = state.d;
    }
    
    @Override
    public void restore() {
        if (this.fillEnabled) {
            GlStateManager._enablePolygonOffset();
        }
        else {
            GlStateManager._disablePolygonOffset();
        }
        GlStateManager._polygonOffset(this.factor, this.units);
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
