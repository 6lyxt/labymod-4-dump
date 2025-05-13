// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.labynet.models.textures;

import net.labymod.api.client.resources.texture.ThemeTextureLocation;
import net.labymod.api.client.gui.icon.Icon;
import com.google.gson.annotations.SerializedName;
import net.labymod.api.client.session.MinecraftServices;
import net.labymod.api.client.resources.ResourceLocation;

public class Skin extends Texture
{
    public static final ResourceLocation LOADING;
    @SerializedName("skin_variant")
    private MinecraftServices.SkinVariant skinVariant;
    private transient Icon previewIcon;
    
    public Skin(final MinecraftServices.SkinVariant skinVariant, final String imageHash, final String tags, final int useCount) {
        super(imageHash, tags, useCount);
        this.skinVariant = skinVariant;
    }
    
    public Skin(final MinecraftServices.SkinVariant skinVariant, final String imageHash) {
        super(imageHash, "", 0);
        this.skinVariant = skinVariant;
    }
    
    public Skin() {
    }
    
    public MinecraftServices.SkinVariant skinVariant() {
        return this.skinVariant;
    }
    
    public Icon previewIcon() {
        if (this.previewIcon == null) {
            this.previewIcon = Icon.url(this.getPreviewUrl(), Skin.LOADING);
        }
        return this.previewIcon;
    }
    
    static {
        LOADING = ThemeTextureLocation.of("activities/playercustomization/loading_skin").resource();
    }
}
