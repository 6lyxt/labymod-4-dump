// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.session.model;

import org.jetbrains.annotations.Nullable;
import com.google.gson.annotations.SerializedName;
import java.util.UUID;

public class MojangTextureChangedResponse
{
    @SerializedName("id")
    private UUID uniqueId;
    @SerializedName("name")
    private String username;
    private MojangTexture[] skins;
    private MojangTexture[] capes;
    
    public UUID getUniqueId() {
        return this.uniqueId;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    @Nullable
    public MojangTexture[] getSkins() {
        return this.skins;
    }
    
    @Nullable
    public MojangTexture[] getCapes() {
        return this.capes;
    }
    
    @Nullable
    public MojangTexture getActiveCape() {
        for (final MojangTexture cape : this.capes) {
            if (cape.isActive()) {
                return cape;
            }
        }
        return null;
    }
    
    public void filterActiveTextures() {
        this.skins = this.filterActiveTextures(this.skins);
        this.capes = this.filterActiveTextures(this.capes);
    }
    
    private MojangTexture[] filterActiveTextures(final MojangTexture[] textures) {
        if (textures == null) {
            return null;
        }
        int activeTextures = 0;
        for (final MojangTexture texture : textures) {
            if (texture.getState() == MojangTextureState.ACTIVE) {
                ++activeTextures;
            }
        }
        final MojangTexture[] activeTexturesArray = new MojangTexture[activeTextures];
        int activeTexturesIndex = 0;
        for (final MojangTexture texture2 : textures) {
            if (texture2.getState() == MojangTextureState.ACTIVE) {
                activeTexturesArray[activeTexturesIndex++] = texture2;
            }
        }
        return activeTexturesArray;
    }
}
