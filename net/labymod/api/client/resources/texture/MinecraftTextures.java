// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.resources.texture;

import net.labymod.api.client.resources.ResourceLocation;

public interface MinecraftTextures
{
    ResourceLocation widgetsTexture();
    
    ResourceLocation barsTexture();
    
    ResourceLocation screenListBackgroundTexture();
    
    ResourceLocation screenMenuBackgroundTexture();
    
    ResourceLocation screenMenuHeaderSeparatorTexture();
    
    ResourceLocation screenMenuFooterSeparatorTexture();
    
    ResourceLocation iconsTexture();
    
    ResourceLocation serverSelectionTexture();
    
    ResourceLocation minecraftLogoTexture();
    
    ResourceLocation minecraftEditionTexture();
    
    ResourceLocation splashTexture();
    
    ResourceLocation[] panoramaTextures();
    
    ResourceLocation panoramaOverlayTexture();
    
    @Deprecated(forRemoval = true, since = "4.1.23")
    default ResourceLocation backgroundTexture() {
        return this.screenListBackgroundTexture();
    }
}
