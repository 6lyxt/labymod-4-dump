// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.world.rplace.art;

import net.labymod.core.main.LabyMod;
import net.labymod.api.client.resources.texture.GameImageTexture;
import net.labymod.api.client.resources.Resources;
import net.labymod.api.client.resources.texture.Texture;
import java.util.UUID;
import java.io.IOException;
import net.labymod.api.Laby;
import java.util.HashMap;
import java.io.InputStream;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.Map;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.core.client.world.rplace.RPlaceRegistry;
import net.labymod.api.service.Identifiable;

public class PixelArt implements Identifiable
{
    private static final RPlaceRegistry PLACE_REGISTRY;
    private final GameImage gameImage;
    private final int width;
    private final int height;
    private final int x;
    private final int y;
    private final int z;
    private final Map<Integer, ColoredBlock> offset2block;
    private ResourceLocation resourceLocation;
    private final boolean labyConnect;
    private boolean disposed;
    
    public PixelArt(final InputStream in, final int x, final int y, final int z, final int size, final boolean labyConnect) throws IOException {
        this.offset2block = new HashMap<Integer, ColoredBlock>();
        final GameImage gameImage = Laby.references().gameImageProvider().getImage(in);
        if (gameImage == null) {
            throw new IOException("Failed to load image");
        }
        if (size > 0) {
            final double aspectRatio = gameImage.getWidth() / (double)gameImage.getHeight();
            int width;
            int height;
            if (aspectRatio > 1.0) {
                width = size;
                height = (int)(size / aspectRatio);
            }
            else {
                width = (int)(size * aspectRatio);
                height = size;
            }
            this.gameImage = gameImage.scale(width, height);
            gameImage.close();
        }
        else {
            this.gameImage = gameImage;
        }
        this.x = x;
        this.y = y;
        this.z = z;
        this.labyConnect = labyConnect;
        this.width = this.gameImage.getWidth();
        this.height = this.gameImage.getHeight();
    }
    
    public void load() {
        Laby.labyAPI().minecraft().executeOnRenderThread(() -> {
            final Resources resources = Laby.labyAPI().renderPipeline().resources();
            final ResourceLocation resourceLocation = resources.resourceLocationFactory().create("labymod", "pixelart/" + String.valueOf(UUID.randomUUID()) + ".png");
            final GameImageTexture texture = resources.gameImageTextureFactory().create(resourceLocation, this.gameImage);
            resources.textureRepository().register(resourceLocation, texture);
            this.resourceLocation = resourceLocation;
        });
    }
    
    public void dispose() {
        if (this.resourceLocation != null) {
            this.disposed = true;
            Laby.labyAPI().renderPipeline().resources().textureRepository().releaseTexture(this.resourceLocation);
            this.gameImage.close();
            this.resourceLocation = null;
        }
    }
    
    public ColoredBlock getBlockAt(final int x, final int y) {
        if (x < 0 || x >= this.width || y < 0 || y >= this.height || this.disposed) {
            return null;
        }
        final int offset = pack(x, y);
        return this.offset2block.computeIfAbsent(offset, integer -> {
            final int argb = this.gameImage.getARGB(x, y);
            return PixelArt.PLACE_REGISTRY.getNearestBlock(argb);
        });
    }
    
    public ResourceLocation getResourceLocation() {
        return this.resourceLocation;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public int getCenterX() {
        return this.x;
    }
    
    public int getCenterZ() {
        return this.z;
    }
    
    public int getFromX() {
        return this.x + this.width / 2;
    }
    
    public int getFromZ() {
        return this.z + this.height / 2;
    }
    
    public int getToX() {
        return this.x - this.width / 2;
    }
    
    public int getToZ() {
        return this.z - this.height / 2;
    }
    
    public int getMinX() {
        return Math.min(this.getFromX(), this.getToX());
    }
    
    public int getMaxX() {
        return Math.max(this.getFromX(), this.getToX());
    }
    
    public int getMinZ() {
        return Math.min(this.getFromZ(), this.getToZ());
    }
    
    public int getMaxZ() {
        return Math.max(this.getFromZ(), this.getToZ());
    }
    
    public int getY() {
        return this.y;
    }
    
    public boolean isLabyConnect() {
        return this.labyConnect;
    }
    
    public boolean intersects(final PixelArt art) {
        return this.getMinX() <= art.getMaxX() && this.getMaxX() >= art.getMinX() && this.getMinZ() <= art.getMaxZ() && this.getMaxZ() >= art.getMinZ();
    }
    
    public static int pack(final int x, final int y) {
        return (x & 0xFFFF) | (y & 0xFFFF) << 16;
    }
    
    public static int unpackX(final int packed) {
        return packed & 0xFFFF;
    }
    
    public static int unpackY(final int packed) {
        return packed >> 16 & 0xFFFF;
    }
    
    @Override
    public String getId() {
        return String.valueOf(pack(this.x, this.y));
    }
    
    static {
        PLACE_REGISTRY = LabyMod.references().rPlaceRegistry();
    }
}
