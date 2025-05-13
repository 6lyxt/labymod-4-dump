// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.lang;

public final class Runnables
{
    private static final Runnable EMPTY_RUNNABLE;
    
    public static Runnable doNothing() {
        return Runnables.EMPTY_RUNNABLE;
    }
    
    public static void runIfNotNull(final Runnable runnable) {
        if (runnable == null) {
            return;
        }
        runnable.run();
    }
    
    static {
        EMPTY_RUNNABLE = (() -> {});
    }
}
