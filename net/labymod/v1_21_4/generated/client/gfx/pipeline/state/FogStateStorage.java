// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.generated.client.gfx.pipeline.state;

import com.mojang.blaze3d.systems.RenderSystem;
import net.labymod.core.client.gfx.pipeline.state.StateStorage;

public final class FogStateStorage implements StateStorage<Void>
{
    private glo parameters;
    
    public FogStateStorage() {
        this.parameters = glo.a;
    }
    
    @Override
    public void store(final Void unused) {
        this.parameters = RenderSystem.getShaderFog();
    }
    
    @Override
    public void restore() {
        RenderSystem.setShaderFog(this.parameters);
    }
    
    @Override
    public FogStateStorage copy() {
        final FogStateStorage newObject = new FogStateStorage();
        newObject.parameters = new glo(this.parameters.a(), this.parameters.b(), this.parameters.c(), this.parameters.d(), this.parameters.e(), this.parameters.f(), this.parameters.g());
        return newObject;
    }
    
    @Override
    public String toString() {
        return "FogStateStorage[parameters=" + String.valueOf(this.parameters);
    }
}
