// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.generated.client.gfx.pipeline.state;

import com.mojang.blaze3d.platform.GlStateManager;
import net.labymod.core.client.gfx.pipeline.state.StateStorage;

public final class ColorLogicStateStorage implements StateStorage<GlStateManager.c>
{
    private int op;
    private boolean enabled;
    
    @Override
    public void store(final GlStateManager.c state) {
        this.enabled = state.a.b;
        this.op = state.b;
    }
    
    @Override
    public void restore() {
        if (this.enabled) {
            GlStateManager._enableColorLogicOp();
        }
        else {
            GlStateManager._disableColorLogicOp();
        }
        GlStateManager._logicOp(this.op);
    }
    
    @Override
    public ColorLogicStateStorage copy() {
        final ColorLogicStateStorage newObject = new ColorLogicStateStorage();
        newObject.op = this.op;
        newObject.enabled = this.enabled;
        return newObject;
    }
    
    @Override
    public String toString() {
        return "ColorLogicStateStorage[op=" + this.op + ", enabled=" + this.enabled;
    }
}
