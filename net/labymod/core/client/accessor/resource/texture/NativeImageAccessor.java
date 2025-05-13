// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.accessor.resource.texture;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public interface NativeImageAccessor
{
    void writeToStream(final OutputStream p0) throws IOException;
    
    boolean isFreed();
    
    default byte[] toByteArray() throws IOException {
        try (final ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            this.writeToStream(outputStream);
            return outputStream.toByteArray();
        }
        catch (final IOException exception) {
            throw new IOException("Could not write image to byte array", exception);
        }
    }
}
