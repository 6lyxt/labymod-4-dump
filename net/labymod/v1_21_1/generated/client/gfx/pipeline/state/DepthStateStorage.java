// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_1.generated.client.gfx.pipeline.state;

import com.mojang.blaze3d.platform.GlStateManager;
import net.labymod.core.client.gfx.pipeline.state.StateStorage;

public final class DepthStateStorage implements StateStorage<GlStateManager.f>
{
    private int func;
    private boolean writeDepth;
    private boolean enabled;
    
    @Override
    public void store(final GlStateManager.f state) {
        this.enabled = state.a.b;
        this.writeDepth = state.b;
        this.func = state.c;
    }
    
    @Override
    public void restore() {
        if (this.enabled) {
            GlStateManager._enableDepthTest();
        }
        else {
            GlStateManager._disableDepthTest();
        }
        GlStateManager._depthMask(this.writeDepth);
        GlStateManager._depthFunc(this.func);
    }
    
    @Override
    public DepthStateStorage copy() {
        final DepthStateStorage newObject = new DepthStateStorage();
        newObject.func = this.func;
        newObject.writeDepth = this.writeDepth;
        newObject.enabled = this.enabled;
        return newObject;
    }
    
    @Override
    public String toString() {
        return "DepthStateStorage[func=" + this.func + ", writeDepth=" + this.writeDepth + ", enabled=" + this.enabled;
    }
}
