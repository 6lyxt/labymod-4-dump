// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3;

import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.LWJGL3GLFWNatives;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.input.LWJGL3ClipboardController;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.memory.LWJGL3MemoryHandler;
import net.labymod.api.client.gfx.pipeline.backend.natives.GLFWNatives;
import net.labymod.api.client.gfx.pipeline.backend.lwjgl.input.ClipboardController;
import net.labymod.api.client.gfx.GFXCapabilities;
import net.labymod.api.client.gfx.pipeline.backend.memory.MemoryHandler;
import net.labymod.api.client.gfx.pipeline.backend.GFXBackend;
import net.labymod.api.service.annotation.AutoService;
import net.labymod.api.client.gfx.pipeline.backend.lwjgl.LWJGLBackend;

@AutoService(GFXBackend.class)
public class LWJGL3Backend implements LWJGLBackend
{
    private MemoryHandler memoryHandler;
    private GFXCapabilities capabilities;
    private ClipboardController clipboardController;
    private GLFWNatives glfwNatives;
    
    @Override
    public GFXCapabilities capabilities() {
        if (this.capabilities == null) {
            this.capabilities = new LWJGL3GFXCapabilities();
        }
        return this.capabilities;
    }
    
    @Override
    public MemoryHandler memoryHandler() {
        if (this.memoryHandler == null) {
            this.memoryHandler = new LWJGL3MemoryHandler();
        }
        return this.memoryHandler;
    }
    
    @Override
    public ClipboardController clipboardController() {
        if (this.clipboardController == null) {
            this.clipboardController = new LWJGL3ClipboardController();
        }
        return this.clipboardController;
    }
    
    @Override
    public GLFWNatives glfwNatives() {
        if (this.glfwNatives == null) {
            this.glfwNatives = new LWJGL3GLFWNatives();
        }
        return this.glfwNatives;
    }
    
    @Override
    public String getName() {
        return "lwjgl3";
    }
}
