// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.generated.client.gfx.pipeline.state;

import com.mojang.blaze3d.platform.GlStateManager;
import net.labymod.core.client.gfx.pipeline.state.StateStorage;

public final class ScissorStateStorage implements StateStorage<GlStateManager.i>
{
    private boolean enabled;
    
    @Override
    public void store(final GlStateManager.i state) {
        this.enabled = state.a.b;
    }
    
    @Override
    public void restore() {
        if (this.enabled) {
            GlStateManager._enableScissorTest();
        }
        else {
            GlStateManager._disableScissorTest();
        }
    }
    
    @Override
    public ScissorStateStorage copy() {
        final ScissorStateStorage newObject = new ScissorStateStorage();
        newObject.enabled = this.enabled;
        return newObject;
    }
    
    @Override
    public String toString() {
        return "ScissorStateStorage[enabled=" + this.enabled;
    }
}
