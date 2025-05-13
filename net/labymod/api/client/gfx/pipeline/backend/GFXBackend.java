// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.backend;

import net.labymod.api.client.gfx.pipeline.backend.natives.GLFWNatives;
import net.labymod.api.client.gfx.pipeline.backend.lwjgl.input.ClipboardController;
import net.labymod.api.client.gfx.pipeline.backend.memory.MemoryHandler;
import net.labymod.api.client.gfx.GFXCapabilities;

public interface GFXBackend
{
    String getName();
    
    GFXCapabilities capabilities();
    
    MemoryHandler memoryHandler();
    
    ClipboardController clipboardController();
    
    GLFWNatives glfwNatives();
}
