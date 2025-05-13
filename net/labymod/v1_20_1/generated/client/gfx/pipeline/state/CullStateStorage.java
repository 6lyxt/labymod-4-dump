// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.generated.client.gfx.pipeline.state;

import com.mojang.blaze3d.platform.GlStateManager;
import net.labymod.core.client.gfx.pipeline.state.StateStorage;

public final class CullStateStorage implements StateStorage<GlStateManager.e>
{
    private int mode;
    private boolean enabled;
    
    @Override
    public void store(final GlStateManager.e state) {
        this.enabled = state.a.b;
        this.mode = state.b;
    }
    
    @Override
    public void restore() {
        if (this.enabled) {
            GlStateManager._enableCull();
        }
        else {
            GlStateManager._disableCull();
        }
    }
    
    @Override
    public CullStateStorage copy() {
        final CullStateStorage newObject = new CullStateStorage();
        newObject.mode = this.mode;
        newObject.enabled = this.enabled;
        return newObject;
    }
    
    @Override
    public String toString() {
        return "CullStateStorage[mode=" + this.mode + ", enabled=" + this.enabled;
    }
}
