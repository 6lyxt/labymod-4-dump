// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.session;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.resources.texture.GameImage;
import java.io.IOException;
import net.labymod.api.client.session.model.MojangTextureChangedResponse;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface MinecraftServices
{
    Response<MojangTextureChangedResponse> getProfile();
    
    Response<MojangTextureChangedResponse> changeSkin(final SkinVariant p0, final SkinPayload p1) throws IOException;
    
    Response<MojangTextureChangedResponse> hideCape();
    
    Response<MojangTextureChangedResponse> showCape(final String p0);
    
    public enum SkinVariant
    {
        CLASSIC("classic"), 
        SLIM("slim");
        
        public static final SkinVariant[] VALUES;
        private final String id;
        
        private SkinVariant(final String id) {
            this.id = id;
        }
        
        public static SkinVariant of(final String id) {
            for (final SkinVariant variant : SkinVariant.VALUES) {
                if (variant.id.equalsIgnoreCase(id)) {
                    return variant;
                }
            }
            return null;
        }
        
        public String getId() {
            return this.id;
        }
        
        static {
            VALUES = values();
        }
    }
    
    public static class SkinPayload
    {
        private String url;
        private GameImage gameImage;
        
        public SkinPayload(final String url) {
            this.url = url;
        }
        
        public SkinPayload(final GameImage gameImage) {
            this.gameImage = gameImage;
        }
        
        @Nullable
        public String getUrl() {
            return this.url;
        }
        
        @Nullable
        public GameImage getGameImage() {
            return this.gameImage;
        }
        
        public boolean hasGameImage() {
            return this.gameImage != null;
        }
    }
}
