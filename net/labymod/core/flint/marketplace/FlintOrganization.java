// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.flint.marketplace;

import net.labymod.api.Laby;
import java.util.Locale;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.icon.Icon;
import com.google.gson.annotations.SerializedName;
import net.labymod.api.client.resources.texture.TextureRepository;

public class FlintOrganization
{
    private static final TextureRepository TEXTURE_REPOSITORY;
    private boolean trusted;
    @SerializedName("vanity_name")
    private String vanityName;
    @SerializedName("display_name")
    private String displayName;
    @SerializedName("icon_hash")
    private String iconHash;
    @SerializedName("flint_url")
    private String flintUrl;
    
    public FlintOrganization(final String displayName) {
        this.displayName = displayName;
    }
    
    public Icon getIcon() {
        return Icon.completable(FlintOrganization.TEXTURE_REPOSITORY.loadCacheResourceAsync("labymod", this.iconHash, this.getIconUrl(), Textures.DEFAULT_SERVER_ICON));
    }
    
    public String getVanityName() {
        return this.vanityName;
    }
    
    public String getDisplayName() {
        return this.displayName;
    }
    
    public String getFlintUrl() {
        return this.flintUrl;
    }
    
    public String getIconUrl() {
        return String.format(Locale.ROOT, "https://flintmc.net/brand/organization/%s.png", this.iconHash);
    }
    
    public boolean isTrusted() {
        return this.trusted;
    }
    
    static {
        TEXTURE_REPOSITORY = Laby.references().textureRepository();
    }
}
