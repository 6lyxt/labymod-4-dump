// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.monitor;

import org.lwjgl.glfw.GLFWVidMode;

record VideoMode(int width, int height, int redBits, int greenBits, int blueBits, int refreshRate) {
    public static VideoMode of(final GLFWVidMode.Buffer buffer) {
        return new VideoMode(buffer.width(), buffer.height(), buffer.redBits(), buffer.greenBits(), buffer.blueBits(), buffer.refreshRate());
    }
    
    public static VideoMode of(final GLFWVidMode mode) {
        return new VideoMode(mode.width(), mode.height(), mode.redBits(), mode.greenBits(), mode.blueBits(), mode.refreshRate());
    }
}
