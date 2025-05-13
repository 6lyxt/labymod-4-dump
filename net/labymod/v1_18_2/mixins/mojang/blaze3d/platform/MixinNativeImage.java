// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.mojang.blaze3d.platform;

import org.lwjgl.stb.STBImage;
import java.nio.channels.Channels;
import java.io.OutputStream;
import java.io.IOException;
import java.nio.channels.WritableByteChannel;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.core.client.accessor.resource.texture.NativeImageAccessor;

@Mixin({ dsn.class })
public abstract class MixinNativeImage implements NativeImageAccessor
{
    @Shadow
    private long k;
    
    @Shadow
    protected abstract boolean a(final WritableByteChannel p0) throws IOException;
    
    @Override
    public void writeToStream(final OutputStream outputStream) throws IOException {
        final WritableByteChannel channel = Channels.newChannel(outputStream);
        if (!this.a(channel)) {
            throw new IOException("Could not write image to byte array: " + STBImage.stbi_failure_reason());
        }
    }
    
    @Override
    public boolean isFreed() {
        return this.k == 0L;
    }
}
