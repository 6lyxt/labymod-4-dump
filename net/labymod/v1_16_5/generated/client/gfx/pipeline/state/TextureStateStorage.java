// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.generated.client.gfx.pipeline.state;

import net.labymod.api.client.gfx.pipeline.texture.TextureTracker;
import net.labymod.core.client.gfx.pipeline.state.StateStorage;

public final class TextureStateStorage implements StateStorage<dem.x>
{
    private final int index;
    private int binding;
    private boolean enabled;
    
    public TextureStateStorage(final int index) {
        this.index = index;
    }
    
    @Override
    public void store(final dem.x state) {
        this.enabled = state.a.b;
        this.binding = state.b;
    }
    
    @Override
    public void restore() {
        if (TextureTracker.getTextureStates()[this.index].changed()) {
            dem.q(33984 + this.index);
            if (this.enabled) {
                dem.K();
            }
            else {
                dem.L();
            }
            dem.s(this.binding);
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
