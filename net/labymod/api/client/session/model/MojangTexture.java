// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.session.model;

import net.labymod.api.client.session.MinecraftServices;
import java.util.UUID;

public class MojangTexture
{
    private UUID id;
    private MojangTextureState state;
    private String url;
    private MinecraftServices.SkinVariant variant;
    private String alias;
    
    public UUID getId() {
        return this.id;
    }
    
    public MojangTextureState getState() {
        return this.state;
    }
    
    public boolean isActive() {
        return this.state == MojangTextureState.ACTIVE;
    }
    
    public String getUrl() {
        return this.url;
    }
    
    public String getTextureHash() {
        return this.url.substring(this.url.lastIndexOf("/") + 1);
    }
    
    public MinecraftServices.SkinVariant getVariant() {
        return this.variant;
    }
    
    public String getAlias() {
        return this.alias;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final MojangTexture that = (MojangTexture)o;
        return this.id.equals(that.id);
    }
    
    @Override
    public int hashCode() {
        return (this.id != null) ? this.id.hashCode() : 0;
    }
}
