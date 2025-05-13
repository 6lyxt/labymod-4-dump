// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.memory;

import java.util.Locale;
import java.util.function.Function;
import java.nio.Buffer;
import org.lwjgl.system.MemoryUtil;

public class MemoryOperations
{
    private MemoryOperations() {
        throw new UnsupportedOperationException("Cannot instantiate this class.");
    }
    
    public static long malloc(final long size) {
        final long address = MemoryUtil.nmemAlloc(size);
        return getOrThrow(address, memoryAddress -> newOutOfMemoryError("Failed to allocate %s bytes of memory.", size));
    }
    
    public static long calloc(final long num, final long size) {
        final long address = MemoryUtil.nmemCalloc(num, size);
        return getOrThrow(address, memoryAddress -> newOutOfMemoryError("Failed to allocate %s elements of size %s each", num, size));
    }
    
    public static long realloc(final long address, final long size) {
        final long newAddress = MemoryUtil.nmemRealloc(address, size);
        return getOrThrow(newAddress, memoryAddress -> newOutOfMemoryError("Failed to reallocate memory. Attempted to allocate %s bytes for the address: %s", size, getFormattedAddress(address)));
    }
    
    public static String getFormattedAddress(final long address) {
        return "0x" + Long.toHexString(address);
    }
    
    public static void free(final Buffer buffer) {
        MemoryUtil.memFree(buffer);
    }
    
    public static void free(final long address) {
        MemoryUtil.nmemFree(address);
    }
    
    public static void clear(final long address, final long bytes) {
        set(address, 0, bytes);
    }
    
    public static void set(final long address, final int value, final long bytes) {
        MemoryUtil.memSet(address, value, bytes);
    }
    
    private static <T extends Throwable> long getOrThrow(final long address, final Function<Long, T> throwableFunction) throws T, Throwable {
        if (address == 0L) {
            throw throwableFunction.apply(address);
        }
        return address;
    }
    
    private static OutOfMemoryError newOutOfMemoryError(final String message, final Object... args) {
        return new OutOfMemoryError(String.format(Locale.ROOT, message, args));
    }
}
