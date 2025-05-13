// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.generated.client.gfx.pipeline.state;

import net.labymod.core.client.gfx.pipeline.state.StateStorage;

public final class ColorMaskStateStorage implements StateStorage<dem.f>
{
    private boolean red;
    private boolean green;
    private boolean blue;
    private boolean alpha;
    
    @Override
    public void store(final dem.f state) {
        this.red = state.a;
        this.green = state.b;
        this.blue = state.c;
        this.alpha = state.d;
    }
    
    @Override
    public void restore() {
        dem.a(this.red, this.green, this.blue, this.alpha);
    }
    
    @Override
    public ColorMaskStateStorage copy() {
        final ColorMaskStateStorage newObject = new ColorMaskStateStorage();
        newObject.red = this.red;
        newObject.green = this.green;
        newObject.blue = this.blue;
        newObject.alpha = this.alpha;
        return newObject;
    }
    
    @Override
    public String toString() {
        return "ColorMaskStateStorage[red=" + this.red + ", green=" + this.green + ", blue=" + this.blue + ", alpha=" + this.alpha;
    }
}
