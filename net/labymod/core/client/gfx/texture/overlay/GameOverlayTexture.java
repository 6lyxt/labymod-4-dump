// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.texture.overlay;

import net.labymod.api.client.resources.texture.GameImage;

public interface GameOverlayTexture
{
    public static final float TEXTURE_SCALE = 0.6666667f;
    
    GameImage image();
    
    void upload();
    
    DynamicOverlayTexture dynamicTexture();
}
