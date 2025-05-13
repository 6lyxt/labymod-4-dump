// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.input;

import java.nio.Buffer;
import org.lwjgl.system.MemoryUtil;
import java.nio.charset.StandardCharsets;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.system.Callback;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util.CallbackUtil;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallbackI;
import org.lwjgl.BufferUtils;
import java.nio.ByteBuffer;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.client.gfx.pipeline.backend.lwjgl.input.ClipboardController;

public final class LWJGL3ClipboardController implements ClipboardController
{
    private static final Logging LOGGER;
    private final ByteBuffer clipboardBuffer;
    
    public LWJGL3ClipboardController() {
        this.clipboardBuffer = BufferUtils.createByteBuffer(8192);
    }
    
    @Override
    public String getClipboardContent(final long windowHandle) {
        return this.getClipboardContent(windowHandle, (error, description) -> {
            if (error == 65545) {
                return;
            }
            final String readableDescription = MemoryUtil.memUTF8(description);
            LWJGL3ClipboardController.LOGGER.error("{}: {}", error, readableDescription);
        });
    }
    
    public String getClipboardContent(final long windowHandle, final GLFWErrorCallbackI errorCallback) {
        final GLFWErrorCallback defaultCallback = GLFW.glfwSetErrorCallback(errorCallback);
        String clipboardContent = GLFW.glfwGetClipboardString(windowHandle);
        clipboardContent = ((clipboardContent == null) ? "" : clipboardContent);
        final GLFWErrorCallback customErrorCallback = GLFW.glfwSetErrorCallback((GLFWErrorCallbackI)defaultCallback);
        CallbackUtil.free((Callback)customErrorCallback);
        return clipboardContent;
    }
    
    @Override
    public void setClipboardContent(final long windowHandle, final String content) {
        final byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);
        final int contentCapacity = contentBytes.length + 1;
        if (contentCapacity < this.clipboardBuffer.capacity()) {
            this.setClipboardContent(windowHandle, this.clipboardBuffer, contentBytes);
        }
        else {
            this.setClipboardContent(windowHandle, contentBytes, contentCapacity);
        }
    }
    
    private void setClipboardContent(final long windowHandle, final byte[] data, final int length) {
        final ByteBuffer buffer = MemoryUtil.memAlloc(length);
        try {
            this.setClipboardContent(windowHandle, buffer, data);
        }
        finally {
            MemoryUtil.memFree((Buffer)buffer);
        }
    }
    
    private void setClipboardContent(final long windowHandle, final ByteBuffer clipboardBuffer, final byte[] data) {
        clipboardBuffer.clear();
        clipboardBuffer.put(data);
        clipboardBuffer.put((byte)0);
        clipboardBuffer.flip();
        GLFW.glfwSetClipboardString(windowHandle, clipboardBuffer);
    }
    
    static {
        LOGGER = Logging.create(LWJGL3ClipboardController.class);
    }
}
