// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.imgui;

import net.labymod.api.util.ide.IdeUtil;
import net.labymod.api.util.logging.Logging;
import imgui.assertion.ImAssertCallback;

public class LabyImAssertCallback extends ImAssertCallback
{
    private static final Logging LOGGER;
    
    public void imAssert(final String assertion, final int line, final String file) {
        if (IdeUtil.RUNNING_IN_IDE) {
            LabyImAssertCallback.LOGGER.error("Dear ImGui Assertion Failed: " + assertion, new Object[0]);
            LabyImAssertCallback.LOGGER.error("Assertion Located At: {}:{}", file, line);
        }
    }
    
    public void imAssertCallback(final String assertion, final int line, final String file) {
    }
    
    static {
        LOGGER = Logging.getLogger();
    }
}
