// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.generated.client.gfx.pipeline.state;

import java.util.Arrays;
import com.mojang.blaze3d.systems.RenderSystem;
import net.labymod.core.client.gfx.pipeline.state.StateStorage;

public final class FogStateStorage implements StateStorage<Void>
{
    private float[] color;
    private float start;
    private float end;
    
    public FogStateStorage() {
        this.color = new float[] { 1.0f, 1.0f, 1.0f, 1.0f };
    }
    
    @Override
    public void store(final Void unused) {
        this.start = RenderSystem.getShaderFogStart();
        this.end = RenderSystem.getShaderFogEnd();
        this.color = RenderSystem.getShaderColor();
    }
    
    @Override
    public void restore() {
        RenderSystem.setShaderFogStart(this.start);
        RenderSystem.setShaderFogEnd(this.end);
        RenderSystem.setShaderColor(this.color[0], this.color[1], this.color[2], this.color[3]);
    }
    
    @Override
    public FogStateStorage copy() {
        final FogStateStorage newObject = new FogStateStorage();
        newObject.color = Arrays.copyOf(this.color, this.color.length);
        newObject.start = this.start;
        newObject.end = this.end;
        return newObject;
    }
    
    @Override
    public String toString() {
        return "FogStateStorage[color=" + String.valueOf(this.color) + ", start=" + this.start + ", end=" + this.end;
    }
}
