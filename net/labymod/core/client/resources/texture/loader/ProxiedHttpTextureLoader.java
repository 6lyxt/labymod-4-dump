// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.resources.texture.loader;

import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.BuildData;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.core.uri.loader.LinkAttachmentLoader;
import net.labymod.api.util.io.web.request.types.GsonRequest;
import net.labymod.api.util.io.web.request.AbstractRequest;
import net.labymod.api.labyconnect.TokenStorage;
import java.io.IOException;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.texture.CompletableTextureImage;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.resources.ResourceLocation;
import java.net.URI;
import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.api.client.resources.texture.TextureLoader;

public class ProxiedHttpTextureLoader implements TextureLoader
{
    private final TextureRepository textureRepository;
    
    public ProxiedHttpTextureLoader(final TextureRepository textureRepository) {
        this.textureRepository = textureRepository;
    }
    
    @Override
    public boolean canLoad(final URI uri) {
        return uri.getScheme().equals("labyproxy") || uri.getScheme().equals("labyproxys");
    }
    
    @Override
    public void loadTexture(final URI uri, @Nullable final ResourceLocation location, final CompletableTextureImage target) throws IOException {
        final String scheme = uri.getScheme();
        final String httpUri = switch (scheme) {
            case "labyproxy" -> uri.toString().replaceFirst("labyproxy://", "http://");
            case "labyproxys" -> uri.toString().replaceFirst("labyproxys://", "https://");
            default -> throw new IllegalStateException("Unexpected value: " + uri.getScheme());
        };
        final LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
        if (session == null) {
            throw new IOException("Texture could not be downloaded: Not connected to LabyConnect");
        }
        final TokenStorage.Token token = session.tokenStorage().getToken(TokenStorage.Purpose.JWT, session.self().getUniqueId());
        if (token == null || token.isExpired()) {
            throw new IOException("Texture could not be downloaded: No JWT token found");
        }
        final Response<LinkAttachmentLoader.LinkPreviewResponse> response = Request.ofGson(LinkAttachmentLoader.LinkPreviewResponse.class).url("https://link-preview.laby.net/request-preview?url=" + URLEncoder.encode(httpUri, StandardCharsets.UTF_8), new Object[0]).authorization("Bearer", token.getToken()).userAgent(BuildData.getUserAgent()).executeSync();
        if (response.getStatusCode() != 200) {
            throw new IOException("Texture could not be downloaded: Response code " + response.getStatusCode());
        }
        if (!"image".equals(response.get().getType())) {
            target.stopLoadingOnError();
            return;
        }
        this.textureRepository.executeTextureLoader(response.get().getImageUrl(), target);
    }
}
