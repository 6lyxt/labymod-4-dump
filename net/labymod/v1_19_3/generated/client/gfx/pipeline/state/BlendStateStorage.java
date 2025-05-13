// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.generated.client.gfx.pipeline.state;

import com.mojang.blaze3d.platform.GlStateManager;
import net.labymod.core.client.gfx.pipeline.state.StateStorage;

public final class BlendStateStorage implements StateStorage<GlStateManager.a>
{
    private int destinationRGB;
    private int sourceRGB;
    private int sourceAlpha;
    private boolean enabled;
    private int destinationAlpha;
    
    @Override
    public void store(final GlStateManager.a state) {
        this.enabled = state.a.b;
        this.sourceRGB = state.b;
        this.destinationRGB = state.c;
        this.sourceAlpha = state.d;
        this.destinationAlpha = state.e;
    }
    
    @Override
    public void restore() {
        if (this.enabled) {
            GlStateManager._enableBlend();
        }
        else {
            GlStateManager._disableBlend();
        }
        GlStateManager._blendFuncSeparate(this.sourceRGB, this.destinationRGB, this.sourceAlpha, this.destinationAlpha);
    }
    
    @Override
    public BlendStateStorage copy() {
        final BlendStateStorage newObject = new BlendStateStorage();
        newObject.destinationRGB = this.destinationRGB;
        newObject.sourceRGB = this.sourceRGB;
        newObject.sourceAlpha = this.sourceAlpha;
        newObject.enabled = this.enabled;
        newObject.destinationAlpha = this.destinationAlpha;
        return newObject;
    }
    
    @Override
    public String toString() {
        return "BlendStateStorage[destinationRGB=" + this.destinationRGB + ", sourceRGB=" + this.sourceRGB + ", sourceAlpha=" + this.sourceAlpha + ", enabled=" + this.enabled + ", destinationAlpha=" + this.destinationAlpha;
    }
}
