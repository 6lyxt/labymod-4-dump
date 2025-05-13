// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.resources.texture.loader;

import net.labymod.api.util.io.web.request.AbstractRequest;
import javax.imageio.ImageIO;
import java.io.InputStream;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.util.io.web.request.Response;
import java.net.URL;
import net.labymod.api.client.resources.texture.TextureImage;
import java.util.NoSuchElementException;
import java.io.IOException;
import java.util.Locale;
import net.labymod.api.client.session.MinecraftServices;
import net.labymod.api.util.io.web.WebInputStream;
import net.labymod.api.BuildData;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.types.InputStreamRequest;
import net.labymod.api.client.resources.texture.CompletableTextureImage;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.resources.ResourceLocation;
import java.net.URI;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.texture.GameImageProvider;
import net.labymod.api.client.resources.texture.TextureLoader;

public class HttpTextureLoader implements TextureLoader
{
    private final GameImageProvider provider;
    
    public HttpTextureLoader() {
        this.provider = Laby.references().gameImageProvider();
    }
    
    @Override
    public boolean canLoad(final URI uri) {
        return uri.getScheme().equals("http") || uri.getScheme().equals("https");
    }
    
    @Override
    public void loadTexture(final URI uri, @Nullable final ResourceLocation location, final CompletableTextureImage target) throws IOException {
        final URL webUrl = uri.toURL();
        final String url = webUrl.toString();
        int responseCode = -1;
        String contentType = null;
        WebInputStream stream;
        try {
            final Response<WebInputStream> response = ((AbstractRequest<WebInputStream, R>)((AbstractRequest<T, InputStreamRequest>)((AbstractRequest<T, InputStreamRequest>)Request.ofInputStream()).url(url, new Object[0])).userAgent(BuildData.getUserAgent())).executeSync();
            responseCode = response.getStatusCode();
            stream = response.get();
            final String skinType = response.getHeaders().get("x-skin-type");
            if (skinType != null && location != null) {
                final MinecraftServices.SkinVariant variant = MinecraftServices.SkinVariant.of(skinType);
                final String id = (variant == null) ? MinecraftServices.SkinVariant.CLASSIC.getId() : variant.getId();
                location.metadata().set("variant", id);
            }
            contentType = response.getHeaders().get("Content-Type");
        }
        catch (final NoSuchElementException exception) {
            target.stopLoadingOnError();
            throw new IOException((responseCode == -1) ? "Texture could not be downloaded" : String.format(Locale.ROOT, "Texture download failed because the URL %s returned a response code of %s", webUrl, responseCode), exception);
        }
        GameImage gameImage;
        if (contentType == null || contentType.equals("image/png")) {
            gameImage = this.loadPngGameImage(stream.getInputStream());
        }
        else {
            gameImage = this.loadGameImage(stream.getInputStream());
        }
        final TextureImage textureImage = new TextureImage(gameImage);
        target.executeCompletableListeners(textureImage);
    }
    
    private GameImage loadPngGameImage(final InputStream inputStream) throws IOException {
        return this.provider.getImage(inputStream);
    }
    
    private GameImage loadGameImage(final InputStream inputStream) throws IOException {
        try {
            return this.provider.getImage(ImageIO.read(inputStream));
        }
        catch (final Exception e) {
            throw new IOException("Failed to load webp image", e);
        }
    }
}
