// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.resources.texture.loader;

import net.labymod.api.Textures;
import java.io.IOException;
import net.labymod.api.client.network.server.ServerInfo;
import net.labymod.api.client.network.server.ServerAddress;
import net.labymod.api.client.resources.texture.CompletableTextureImage;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.resources.ResourceLocation;
import java.net.URI;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.texture.TextureImage;
import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.api.client.network.server.ServerPinger;
import net.labymod.api.client.resources.texture.TextureLoader;

public class McServerTextureLoader implements TextureLoader
{
    private final ServerPinger serverPinger;
    private final TextureRepository textureRepository;
    private TextureImage defaultIcon;
    
    public McServerTextureLoader(final TextureRepository textureRepository) {
        this.serverPinger = Laby.references().serverPinger();
        this.textureRepository = textureRepository;
    }
    
    @Override
    public boolean canLoad(final URI uri) {
        return uri.getScheme().equals("mcserver");
    }
    
    @Override
    public void loadTexture(final URI uri, @Nullable final ResourceLocation location, final CompletableTextureImage target) throws IOException {
        String host = uri.getHost();
        if (host.equalsIgnoreCase("default")) {
            this.completeDefaultIcon(target);
            return;
        }
        final int port = uri.getPort();
        if (port != -1) {
            host = host + ":" + port;
        }
        final ServerAddress address = ServerAddress.resolve(host);
        final ServerInfo serverInfo = this.serverPinger.pingServer(host, address, 5000);
        if (serverInfo == null || serverInfo.getRawIcon() == null) {
            this.completeDefaultIcon(target);
            return;
        }
        this.textureRepository.executeTextureLoader(serverInfo.getRawIcon(), target);
    }
    
    private void completeDefaultIcon(final CompletableTextureImage target) {
        if (this.defaultIcon != null) {
            target.executeCompletableListeners(this.defaultIcon);
        }
        else {
            Laby.labyAPI().minecraft().executeOnRenderThread(() -> {
                try {
                    target.executeCompletableListeners(this.defaultIcon = new TextureImage(Textures.DEFAULT_SERVER_ICON));
                }
                catch (final IOException exception) {
                    target.stopLoadingOnError();
                    exception.printStackTrace();
                }
            });
        }
    }
}
