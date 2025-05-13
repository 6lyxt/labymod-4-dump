// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.client.resources.texture;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.AbstractMinecraftTextures;

public class VersionedMinecraftTextures extends AbstractMinecraftTextures
{
    private static final ResourceLocation GUI_ATLAS;
    
    @Override
    public ResourceLocation widgetsTexture() {
        return VersionedMinecraftTextures.GUI_ATLAS;
    }
    
    @Override
    public ResourceLocation iconsTexture() {
        return VersionedMinecraftTextures.GUI_ATLAS;
    }
    
    @Override
    public ResourceLocation serverSelectionTexture() {
        return VersionedMinecraftTextures.GUI_ATLAS;
    }
    
    @Override
    public ResourceLocation barsTexture() {
        return VersionedMinecraftTextures.GUI_ATLAS;
    }
    
    static {
        GUI_ATLAS = ResourceLocation.create("minecraft", "textures/atlas/gui.png");
    }
}
