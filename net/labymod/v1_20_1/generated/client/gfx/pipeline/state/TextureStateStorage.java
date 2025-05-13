// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.generated.client.gfx.pipeline.state;

import net.labymod.api.client.gfx.pipeline.texture.TextureTracker;
import com.mojang.blaze3d.platform.GlStateManager;
import net.labymod.core.client.gfx.pipeline.state.StateStorage;

public final class TextureStateStorage implements StateStorage<GlStateManager.l>
{
    private final int index;
    private int binding;
    
    public TextureStateStorage(final int index) {
        this.index = index;
    }
    
    @Override
    public void store(final GlStateManager.l state) {
        this.binding = state.a;
    }
    
    @Override
    public void restore() {
        if (TextureTracker.getTextureStates()[this.index].changed()) {
            GlStateManager._activeTexture(33984 + this.index);
            GlStateManager._bindTexture(this.binding);
        }
    }
    
    @Override
    public TextureStateStorage copy() {
        final TextureStateStorage newObject = new TextureStateStorage(this.index);
        newObject.binding = this.binding;
        return newObject;
    }
    
    @Override
    public String toString() {
        return "TextureStateStorage[index=" + this.index + ", binding=" + this.binding;
    }
}
