// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.generated.client.gfx.pipeline.state;

import net.labymod.api.client.gfx.pipeline.texture.TextureTracker;
import com.mojang.blaze3d.platform.GlStateManager;
import net.labymod.core.client.gfx.pipeline.state.StateStorage;

public final class TextureStateStorage implements StateStorage<GlStateManager.l>
{
    private final int index;
    private int binding;
    private boolean enabled;
    
    public TextureStateStorage(final int index) {
        this.index = index;
    }
    
    @Override
    public void store(final GlStateManager.l state) {
        this.enabled = state.a;
        this.binding = state.b;
    }
    
    @Override
    public void restore() {
        if (TextureTracker.getTextureStates()[this.index].changed()) {
            GlStateManager._activeTexture(33984 + this.index);
            if (this.enabled) {
                GlStateManager._enableTexture();
            }
            else {
                GlStateManager._disableTexture();
            }
            GlStateManager._bindTexture(this.binding);
        }
    }
    
    @Override
    public TextureStateStorage copy() {
        final TextureStateStorage newObject = new TextureStateStorage(this.index);
        newObject.binding = this.binding;
        newObject.enabled = this.enabled;
        return newObject;
    }
    
    @Override
    public String toString() {
        return "TextureStateStorage[index=" + this.index + ", binding=" + this.binding + ", enabled=" + this.enabled;
    }
}
