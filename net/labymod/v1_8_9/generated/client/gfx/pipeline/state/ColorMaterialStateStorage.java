// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.generated.client.gfx.pipeline.state;

import net.labymod.core.client.gfx.pipeline.state.StateStorage;

public final class ColorMaterialStateStorage implements StateStorage<bfl.h>
{
    private int mode;
    private int face;
    private boolean enabled;
    
    @Override
    public void store(final bfl.h state) {
        this.enabled = state.a.b;
        this.face = state.b;
        this.mode = state.c;
    }
    
    @Override
    public void restore() {
        if (this.enabled) {
            bfl.g();
        }
        else {
            bfl.h();
        }
        bfl.a(this.face, this.mode);
    }
    
    @Override
    public ColorMaterialStateStorage copy() {
        final ColorMaterialStateStorage newObject = new ColorMaterialStateStorage();
        newObject.mode = this.mode;
        newObject.face = this.face;
        newObject.enabled = this.enabled;
        return newObject;
    }
    
    @Override
    public String toString() {
        return "ColorMaterialStateStorage[mode=" + this.mode + ", face=" + this.face + ", enabled=" + this.enabled;
    }
}
