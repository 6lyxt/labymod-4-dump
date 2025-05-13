// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.texture;

import net.labymod.api.util.function.Functional;

public class TextureTracker
{
    private static final int MAX_TEXTURES = 256;
    private static final TextureState[] TEXTURE_STATES;
    private static TextureState activeTextureState;
    
    public static void setActiveTexture(final int activeTexture) {
        TextureTracker.activeTextureState = TextureTracker.TEXTURE_STATES[activeTexture];
    }
    
    public static void bindTexture(final int texture) {
        TextureTracker.activeTextureState.setBinding(texture);
    }
    
    public static void enableTexture() {
        TextureTracker.activeTextureState.enable();
    }
    
    public static void disableTexture() {
        TextureTracker.activeTextureState.disable();
    }
    
    public static TextureState[] getTextureStates() {
        return TextureTracker.TEXTURE_STATES;
    }
    
    static {
        TEXTURE_STATES = Functional.of(new TextureState[256], states -> {
            for (int index = 0; index < 256; ++index) {
                states[index] = new TextureState();
            }
            return;
        });
        TextureTracker.activeTextureState = TextureTracker.TEXTURE_STATES[0];
    }
}
