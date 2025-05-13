// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.resources.texture.loader;

import java.io.IOException;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.api.client.resources.texture.GameImageProvider;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.mojang.texture.MojangTextureService;
import net.labymod.api.util.gson.UUIDTypeAdapter;
import java.util.UUID;
import net.labymod.api.client.resources.texture.TextureImage;
import net.labymod.api.mojang.texture.MojangTextureType;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.texture.CompletableTextureImage;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.resources.ResourceLocation;
import java.net.URI;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.client.resources.texture.TextureLoader;

public class FaceTextureLoader implements TextureLoader
{
    private static final Logging LOGGER;
    
    @Override
    public boolean canLoad(final URI uri) {
        return uri.getScheme().equals("face");
    }
    
    @Override
    public void loadTexture(final URI uri, @Nullable final ResourceLocation resourceLocation, final CompletableTextureImage target) throws IOException {
        final MojangTextureService textureService = Laby.references().mojangTextureService();
        final String input = uri.getHost();
        if (input.equalsIgnoreCase("default")) {
            target.executeCompletableListeners(new TextureImage(this.getFace(textureService.getDefaultTexture(MojangTextureType.SKIN))));
            return;
        }
        UUID uuid = null;
        if (input.length() == 36) {
            uuid = UUID.fromString(input);
        }
        else if (input.length() == 32) {
            uuid = UUIDTypeAdapter.fromString(input);
        }
        else if (input.length() < 2 || input.length() > 16) {
            target.stopLoadingOnError();
            throw new IllegalArgumentException("Invalid uuid/name provided in head:// url: " + input);
        }
        final CompletableResourceLocation skin = (uuid != null) ? textureService.getTexture(uuid, MojangTextureType.SKIN) : textureService.getTexture(input, MojangTextureType.SKIN);
        skin.addCompletableListener(() -> {
            if (!skin.isLoading()) {
                final ResourceLocation location = skin.getCompleted();
                if (location != null) {
                    Laby.labyAPI().minecraft().executeNextTick(() -> {
                        final GameImageProvider gameImageProvider = Laby.references().gameImageProvider();
                        final TextureRepository textureRepository = Laby.references().textureRepository();
                        final GameImage image = gameImageProvider.loadImage(textureRepository.getTexture(location));
                        try {
                            target.executeCompletableListeners(new TextureImage(this.getFace(image)));
                        }
                        catch (final Exception exception) {
                            FaceTextureLoader.LOGGER.error("Failed to load skin for {}", uri, exception);
                            target.stopLoadingOnError();
                        }
                    });
                }
            }
        });
    }
    
    private GameImage getFace(final ResourceLocation location) throws IOException {
        final GameImageProvider gameImageProvider = Laby.references().gameImageProvider();
        return this.getFace(gameImageProvider.getImage(location));
    }
    
    private GameImage getFace(final GameImage skin) {
        final GameImageProvider gameImageProvider = Laby.references().gameImageProvider();
        final float factor = skin.getWidth() / 64.0f;
        final int size = (int)(8.0f * factor);
        final GameImage face = gameImageProvider.createImage(size, size);
        face.drawImage(skin, 0, 0, (int)(8.0f * factor), (int)(8.0f * factor), size, size);
        face.drawImageOverlay(skin, 0, 0, (int)(40.0f * factor), (int)(8.0f * factor), size, size);
        return face;
    }
    
    static {
        LOGGER = Logging.create(FaceTextureLoader.class);
    }
}
