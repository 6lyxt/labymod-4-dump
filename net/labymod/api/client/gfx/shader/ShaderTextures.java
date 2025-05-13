// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.shader;

public final class ShaderTextures
{
    private static final int[] SHADER_TEXTURES;
    
    public static void setShaderTexture(final int slot, final int texture) {
        if (slot < 0 || slot >= ShaderTextures.SHADER_TEXTURES.length) {
            return;
        }
        ShaderTextures.SHADER_TEXTURES[slot] = texture;
    }
    
    public static int getShaderTexture(final int slot) {
        return (slot >= 0 && slot < ShaderTextures.SHADER_TEXTURES.length) ? ShaderTextures.SHADER_TEXTURES[slot] : 0;
    }
    
    static {
        SHADER_TEXTURES = new int[12];
    }
}
