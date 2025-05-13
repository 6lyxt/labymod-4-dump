// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util;

import java.util.function.BooleanSupplier;
import net.labymod.api.client.Minecraft;
import net.labymod.api.Laby;

public class ThreadSafe
{
    public static void executeOnRenderThread(final Runnable runnable) {
        Laby.labyAPI().minecraft().executeOnRenderThread(runnable);
    }
    
    public static boolean isNotOnRenderThread() {
        return !isRenderThread();
    }
    
    public static boolean isRenderThread() {
        final Minecraft minecraft = Laby.labyAPI().minecraft();
        return minecraft == null || minecraft.isOnRenderThread();
    }
    
    public static void ensureRenderThread(final BooleanSupplier supplier) {
        if (supplier.getAsBoolean()) {
            ensureRenderThread();
        }
    }
    
    public static void ensureRenderThread() {
        if (!isRenderThread()) {
            throw new IllegalThreadStateException("Method called from wrong thread, use Laby.labyAPI().minecraft().executeOnRenderThread()");
        }
    }
    
    public static void ensureThread(final BooleanSupplier tester, final String method) {
        if (!tester.getAsBoolean()) {
            throw new IllegalThreadStateException("Method called from wrong thread, use " + method);
        }
    }
}
