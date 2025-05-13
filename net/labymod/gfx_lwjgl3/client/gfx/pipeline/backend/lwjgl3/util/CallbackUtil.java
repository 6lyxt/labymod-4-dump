// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util;

import org.lwjgl.system.Callback;

public final class CallbackUtil
{
    private CallbackUtil() {
    }
    
    public static void free(final Callback callback) {
        if (callback == null) {
            return;
        }
        callback.free();
    }
}
