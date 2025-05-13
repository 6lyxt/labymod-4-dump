// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.resources.texture;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.AbstractMinecraftTextures;

public class VersionedMinecraftTextures extends AbstractMinecraftTextures
{
    private final ResourceLocation SPLASH;
    
    public VersionedMinecraftTextures() {
        this.SPLASH = AbstractMinecraftTextures.resource("textures/gui/", "title/mojang.png");
    }
    
    @Override
    public ResourceLocation panoramaOverlayTexture() {
        return null;
    }
    
    @Override
    public ResourceLocation splashTexture() {
        return this.SPLASH;
    }
}
