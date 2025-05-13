// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.resources.texture;

import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.List;
import net.labymod.api.loader.MinecraftVersions;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicReference;
import net.labymod.api.client.gfx.pipeline.backend.memory.MemoryWriter;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.gfx.texture.PixelStore;
import java.nio.ByteBuffer;
import net.labymod.api.client.gfx.texture.TextureParameterName;
import net.labymod.api.client.gfx.pipeline.util.TextureId;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.backend.memory.MemoryBlock;
import java.util.function.Supplier;
import java.util.concurrent.CompletableFuture;
import net.labymod.api.client.gfx.texture.TextureTarget;
import net.labymod.api.client.gfx.pipeline.backend.memory.MemoryHandler;
import net.labymod.api.client.gfx.GFXBridge;

public final class TextureAllocator
{
    public static final String ASYNCHRONOUS_METADATA_KEY = "asynchronous";
    private static final boolean UNPACK_IMAGE;
    private static final GFXBridge GFX;
    private static final MemoryHandler MEMORY_HANDLER;
    private static final PixelBufferHolder PIXEL_BUFFER_HOLDER;
    private static final int RGBA_COMPONENTS = 4;
    
    public static CompletableFuture<Void> allocateAndUpload(final TextureTarget target, final int id, final GameImage image, final Runnable uploadListener) {
        return upload(target, id, image, true, uploadListener);
    }
    
    public static CompletableFuture<Void> upload(final TextureTarget target, final int id, final GameImage image, final Runnable uploadListener) {
        return upload(target, id, image, false, uploadListener);
    }
    
    public static CompletableFuture<Void> upload(final TextureTarget target, final int id, final Supplier<GameImage> imageSupplier, final Runnable uploadListener) {
        return upload(target, id, imageSupplier, false, uploadListener);
    }
    
    public static void preallocateTexture(final TextureTarget target, final int id, final int width, final int height, final MemoryBlock memoryBlock) {
        preallocateTexture(target, id, 0, width, height, memoryBlock);
    }
    
    public static void preallocateTexture(final TextureTarget target, final int id, final int level, final int width, final int height, final MemoryBlock memoryBlock) {
        final GFXBridge gfx = Laby.gfx();
        gfx.deleteTextures(id);
        gfx.bindTexture(target, TextureId.of(id));
        if (level >= 0) {
            gfx.texParameter(target, TextureParameterName.TEXTURE_MAX_LEVEL, level);
            gfx.texParameter(target, TextureParameterName.TEXTURE_MIN_LOD, 0.0f);
            gfx.texParameter(target, TextureParameterName.TEXTURE_MAX_LOD, (float)level);
            gfx.texParameter(target, TextureParameterName.TEXTURE_LOD_BIAS, 0.0f);
        }
        for (int index = 0; index < level; ++index) {
            gfx._texImage2D(target, index, 32856, width >> index, height >> index, 0, 6408, 5121, memoryBlock);
        }
    }
    
    public static void allocateTexture(final LabyTexture texture, final int width, final int height) {
        allocateTexture(texture, 0, width, height);
    }
    
    public static void allocateTexture(final LabyTexture texture, final int level, final int width, final int height) {
        final GFXBridge gfx = Laby.gfx();
        texture.bind();
        final TextureTarget target = texture.getTarget();
        if (level >= 0) {
            gfx.texParameter(target, TextureParameterName.TEXTURE_MAX_LEVEL, level);
            gfx.texParameter(target, TextureParameterName.TEXTURE_MIN_LOD, 0.0f);
            gfx.texParameter(target, TextureParameterName.TEXTURE_MAX_LOD, (float)level);
            gfx.texParameter(target, TextureParameterName.TEXTURE_LOD_BIAS, 0.0f);
        }
        for (int index = 0; index <= level; ++index) {
            gfx.texImage2D(target, index, 32856, width >> index, height >> index, 0, 6408, 5121, null);
        }
    }
    
    public static void upload(final LabyTexture texture, final GameImage image) {
        upload(texture, image, 0, 0, 0);
    }
    
    public static void upload(final LabyTexture texture, final GameImage image, final int level, final int xOffset, final int yOffset) {
        upload(texture, image, level, xOffset, yOffset, 0, 0, image.getWidth(), image.getHeight());
    }
    
    public static void upload(final LabyTexture texture, final GameImage image, final int level, final int xOffset, final int yOffset, final int unpackSkipPixels, final int unpackSkipRows, final int width, final int height) {
        texture.bind();
        if (image.isFreed()) {
            throw new IllegalStateException();
        }
        if (width == image.getWidth()) {
            TextureAllocator.GFX.pixelStore(PixelStore.UNPACK_ROW_LENGTH, 0);
        }
        else {
            TextureAllocator.GFX.pixelStore(PixelStore.UNPACK_ROW_LENGTH, image.getWidth());
        }
        TextureAllocator.GFX.pixelStore(PixelStore.UNPACK_SKIP_PIXELS, unpackSkipPixels);
        TextureAllocator.GFX.pixelStore(PixelStore.UNPACK_SKIP_ROWS, unpackSkipRows);
        final MemoryBlock memoryBlock = TextureAllocator.MEMORY_HANDLER.mallocBlock(image.getWidth() * image.getHeight() * 4L);
        final MemoryWriter writer = memoryBlock.getOrCreateWriter();
        writer.start();
        final ColorFormat format = ColorFormat.ARGB32;
        for (int y = 0; y < image.getHeight(); ++y) {
            for (int x = 0; x < image.getWidth(); ++x) {
                final int argb = image.getARGB(x, y);
                writer.put((byte)format.red(argb));
                writer.put((byte)format.green(argb));
                writer.put((byte)format.blue(argb));
                writer.put((byte)format.alpha(argb));
            }
        }
        writer.finish();
        TextureAllocator.GFX.texSubImage2D(texture.getTarget(), level, xOffset, yOffset, width, height, 6408, 5121, memoryBlock.asBuffer());
        memoryBlock.free();
    }
    
    private static CompletableFuture<Void> upload(final TextureTarget target, final int id, final GameImage image, final boolean allocate, final Runnable uploadListener) {
        return Laby.references().asynchronousTextureUploader().prepareAndUploadTexture(() -> prepareImageBuffer(id, image), () -> uploadImageBuffer(target, id, image, allocate, uploadListener));
    }
    
    private static CompletableFuture<Void> upload(final TextureTarget target, final int id, final Supplier<GameImage> imageSupplier, final boolean allocate, final Runnable uploadListener) {
        final AtomicReference<GameImage> ref = new AtomicReference<GameImage>();
        return Laby.references().asynchronousTextureUploader().prepareAndUploadTexture(() -> {
            final GameImage image = imageSupplier.get();
            ref.set(image);
            prepareImageBuffer(id, image);
        }, () -> uploadImageBuffer(target, id, ref.get(), allocate, uploadListener));
    }
    
    private static void prepareImageBuffer(final int id, final GameImage image) {
        final int imageWidth = image.getWidth();
        final int imageHeight = image.getHeight();
        final int[] pixels = new int[imageWidth * imageHeight];
        final BufferedImage bufferedImage = image.getImage();
        bufferedImage.getRGB(0, 0, imageWidth, imageHeight, pixels, 0, imageWidth);
        final MemoryBlock memoryBlock = TextureAllocator.MEMORY_HANDLER.mallocBlock(pixels.length * 4L);
        final MemoryWriter writer = memoryBlock.getOrCreateWriter();
        writer.start();
        final ColorFormat colorFormat = ColorFormat.ARGB32;
        for (final int pixel : pixels) {
            writer.put((byte)colorFormat.red(pixel));
            writer.put((byte)colorFormat.green(pixel));
            writer.put((byte)colorFormat.blue(pixel));
            writer.put((byte)colorFormat.alpha(pixel));
        }
        writer.finish();
        TextureAllocator.PIXEL_BUFFER_HOLDER.add(id, memoryBlock);
    }
    
    private static void uploadImageBuffer(final TextureTarget target, final int id, final GameImage image, final boolean allocate, final Runnable uploadListener) {
        final PixelBuffer pixelBuffer = TextureAllocator.PIXEL_BUFFER_HOLDER.findFirstBuffer(id);
        if (pixelBuffer == null) {
            return;
        }
        final MemoryBlock memoryBlock = pixelBuffer.memoryBlock();
        if (allocate) {
            preallocateTexture(target, id, image.getWidth(), image.getHeight(), memoryBlock);
        }
        final GFXBridge gfx = Laby.gfx();
        gfx.bindTexture(target, TextureId.of(id));
        if (TextureAllocator.UNPACK_IMAGE) {
            gfx.pixelStore(PixelStore.UNPACK_ROW_LENGTH, image.getWidth());
            gfx.pixelStore(PixelStore.UNPACK_SKIP_PIXELS, 0);
            gfx.pixelStore(PixelStore.UNPACK_SKIP_ROWS, 0);
            gfx.pixelStore(PixelStore.UNPACK_ALIGNMENT, 4);
        }
        gfx._texImage2D(target, 0, 32856, image.getWidth(), image.getHeight(), 0, 6408, 5121, memoryBlock);
        memoryBlock.free();
        TextureAllocator.PIXEL_BUFFER_HOLDER.remove(pixelBuffer);
        if (uploadListener != null) {
            uploadListener.run();
        }
    }
    
    static {
        UNPACK_IMAGE = !MinecraftVersions.V1_12_2.orOlder();
        GFX = Laby.gfx();
        MEMORY_HANDLER = Laby.gfx().backend().memoryHandler();
        PIXEL_BUFFER_HOLDER = new PixelBufferHolder();
    }
    
    private static class PixelBufferHolder
    {
        private final List<PixelBuffer> pixelBuffers;
        
        private PixelBufferHolder() {
            this.pixelBuffers = new ArrayList<PixelBuffer>();
        }
        
        public void add(final int textureId, final MemoryBlock block) {
            this.pixelBuffers.add(new PixelBuffer(textureId, block));
        }
        
        public void remove(final PixelBuffer buffer) {
            this.pixelBuffers.remove(buffer);
        }
        
        public PixelBuffer findFirstBuffer(final int textureId) {
            for (int size = this.pixelBuffers.size(), index = 0; index < size; ++index) {
                final PixelBuffer pixelBuffer = this.get(index);
                if (pixelBuffer != null) {
                    if (pixelBuffer.textureId() == textureId) {
                        return pixelBuffer;
                    }
                }
            }
            return null;
        }
        
        @Nullable
        private PixelBuffer get(final int index) {
            try {
                return this.pixelBuffers.get(index);
            }
            catch (final IndexOutOfBoundsException exception) {
                return null;
            }
        }
    }
    
    record PixelBuffer(int textureId, MemoryBlock memoryBlock) {}
}
