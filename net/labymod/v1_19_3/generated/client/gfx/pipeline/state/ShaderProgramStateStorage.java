// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.generated.client.gfx.pipeline.state;

import com.mojang.blaze3d.platform.GlStateManager;
import net.labymod.core.client.gfx.pipeline.state.custom.Blaze3DShaderProgramState;
import net.labymod.core.client.gfx.pipeline.state.StateStorage;

public final class ShaderProgramStateStorage implements StateStorage<Blaze3DShaderProgramState>
{
    private int programId;
    
    @Override
    public void store(final Blaze3DShaderProgramState state) {
        this.programId = state.getProgramId();
    }
    
    @Override
    public void restore() {
        GlStateManager._glUseProgram(this.programId);
    }
    
    @Override
    public ShaderProgramStateStorage copy() {
        final ShaderProgramStateStorage newObject = new ShaderProgramStateStorage();
        newObject.programId = this.programId;
        return newObject;
    }
    
    @Override
    public String toString() {
        return "ShaderProgramStateStorage[programId=" + this.programId;
    }
}
