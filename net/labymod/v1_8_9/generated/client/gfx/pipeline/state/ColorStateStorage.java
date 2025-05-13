// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.generated.client.gfx.pipeline.state;

import net.labymod.core.client.gfx.pipeline.state.StateStorage;

public final class ColorStateStorage implements StateStorage<bfl.e>
{
    private float red;
    private float green;
    private float blue;
    private float alpha;
    
    @Override
    public void store(final bfl.e state) {
        this.red = state.a;
        this.green = state.b;
        this.blue = state.c;
        this.alpha = state.d;
    }
    
    @Override
    public void restore() {
        bfl.c(this.red, this.green, this.blue, this.alpha);
    }
    
    @Override
    public ColorStateStorage copy() {
        final ColorStateStorage newObject = new ColorStateStorage();
        newObject.red = this.red;
        newObject.green = this.green;
        newObject.blue = this.blue;
        newObject.alpha = this.alpha;
        return newObject;
    }
    
    @Override
    public String toString() {
        return "ColorStateStorage[red=" + this.red + ", green=" + this.green + ", blue=" + this.blue + ", alpha=" + this.alpha;
    }
}
