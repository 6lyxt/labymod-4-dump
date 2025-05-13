// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw;

import org.lwjgl.glfw.GLFWNativeWin32;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import net.labymod.api.client.gfx.pipeline.backend.natives.GLFWNatives;

public class LWJGL3GLFWNatives implements GLFWNatives
{
    private final LongList windows;
    
    public LWJGL3GLFWNatives() {
        this.windows = (LongList)new LongArrayList();
    }
    
    @Override
    public long getWin32Window(final long pointer) {
        return GLFWNativeWin32.glfwGetWin32Window(pointer);
    }
    
    @Override
    public LongList getWindows() {
        return this.windows;
    }
}
