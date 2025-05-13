// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.generated.client.gfx.pipeline.state;

import net.labymod.core.client.gfx.pipeline.state.data.ClearColorData;
import net.labymod.core.client.gfx.pipeline.state.StateStorage;

public final class ClearStateStorage implements StateStorage<bfl.d>
{
    private double depth;
    private ClearColorData color;
    private int stencil;
    
    public ClearStateStorage() {
        this.color = new ClearColorData();
    }
    
    @Override
    public void store(final bfl.d state) {
        this.depth = state.a;
        this.stencil = state.c;
        this.color.setRed(state.b.a);
        this.color.setGreen(state.b.b);
        this.color.setBlue(state.b.c);
        this.color.setAlpha(state.b.d);
    }
    
    @Override
    public void restore() {
        bfl.a(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), this.color.getAlpha());
        bfl.a(this.depth);
    }
    
    @Override
    public ClearStateStorage copy() {
        final ClearStateStorage newObject = new ClearStateStorage();
        newObject.depth = this.depth;
        newObject.color = this.color.copy();
        newObject.stencil = this.stencil;
        return newObject;
    }
    
    @Override
    public String toString() {
        return "ClearStateStorage[depth=" + this.depth + ", color=" + String.valueOf(this.color) + ", stencil=" + this.stencil;
    }
}
