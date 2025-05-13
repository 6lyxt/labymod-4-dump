// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx;

import org.lwjgl.opengl.GL43;
import net.labymod.api.util.debug.Renderdoc;
import java.util.function.Supplier;
import java.io.IOException;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.util.GLFWUtil;
import java.io.InputStream;
import net.labymod.api.util.function.IntIntFunction;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.opengl.OpenGLFramebuffer;
import net.labymod.api.client.gfx.target.FramebufferTarget;
import org.lwjgl.opengl.GL30;
import net.labymod.api.client.gfx.DataType;
import org.lwjgl.opengl.GL31;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.opengl.OpenGLPreconditions;
import net.labymod.core.main.profiler.RenderProfiler;
import net.labymod.api.client.gfx.DrawingMode;
import java.util.function.Function;
import org.lwjgl.PointerBuffer;
import java.nio.Buffer;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import net.labymod.api.util.CharSequences;
import java.nio.charset.StandardCharsets;
import net.labymod.api.client.gfx.buffer.MapBufferAccess;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.opengl.OpenGLBufferBinder;
import java.nio.LongBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.nio.IntBuffer;
import net.labymod.api.client.gfx.buffer.BufferUsage;
import org.lwjgl.opengl.GL15;
import net.labymod.api.client.gfx.buffer.BufferTarget;
import net.labymod.api.client.gfx.texture.PixelStore;
import net.labymod.api.client.gfx.pipeline.backend.memory.MemoryBlock;
import java.nio.ByteBuffer;
import net.labymod.api.client.gfx.texture.TextureFilter;
import net.labymod.api.client.gfx.texture.TextureWrapMode;
import net.labymod.api.client.gfx.texture.TextureParameterName;
import net.labymod.api.client.gfx.shader.ShaderTextures;
import net.labymod.api.client.gfx.pipeline.util.TextureId;
import net.labymod.api.client.gfx.texture.TextureTarget;
import org.lwjgl.opengl.GL14;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import net.labymod.api.client.gfx.pipeline.Blaze3DGlStatePipeline;
import net.labymod.api.util.Lazy;
import net.labymod.core.client.gfx.AbstractGFXBridge;

public abstract class LWJGL3GFXBridge extends AbstractGFXBridge
{
    private final Lazy<String> renderer;
    
    protected LWJGL3GFXBridge(final Blaze3DGlStatePipeline blaze3DGlStatePipeline) {
        super(blaze3DGlStatePipeline);
        this.renderer = Lazy.of(() -> GL11.glGetString(7937));
    }
    
    @Override
    public long getCurrentContextHandle() {
        return GLFW.glfwGetCurrentContext();
    }
    
    @Override
    public void enableStencil() {
        GL11.glEnable(2960);
    }
    
    @Override
    public void disableStencil() {
        GL11.glDisable(2960);
    }
    
    @Override
    public void blendEquationSeparate(final int modeRGB, final int modeAlpha) {
        GL14.glBlendFuncSeparate(modeRGB, modeAlpha, modeRGB, modeAlpha);
    }
    
    @Override
    public int genTextures() {
        return GL11.glGenTextures();
    }
    
    @Override
    public void setActiveTexture(final int slot) {
        this.setDirectActiveTexture(33984 + slot);
    }
    
    @Override
    public void bindTexture(final TextureTarget target, final TextureId id) {
        ShaderTextures.setShaderTexture(this.getActiveTexture() - 33984, id.getId());
        if (target == TextureTarget.TEXTURE_2D) {
            this.blaze3DGlStatePipeline.bindTexture(id);
            return;
        }
        GL11.glBindTexture(target.getId(), id.getId());
    }
    
    @Override
    public void texParameter(final TextureTarget target, final TextureParameterName parameterName, final float value) {
        GL11.glTexParameterf(target.getId(), parameterName.getId(), value);
    }
    
    @Override
    public void texParameter(final TextureTarget target, final TextureParameterName parameterName, final TextureWrapMode wrapMode) {
        this.texParameter(target, parameterName, wrapMode.getId());
    }
    
    @Override
    public void texParameter(final TextureTarget target, final TextureParameterName parameterName, final TextureFilter filter) {
        this.texParameter(target, parameterName, filter.getId());
    }
    
    @Override
    public void texParameter(final TextureTarget target, final TextureParameterName parameterName, final int value) {
        GL11.glTexParameteri(target.getId(), parameterName.getId(), value);
    }
    
    @Override
    public void texImage2D(final TextureTarget target, final int level, final int internalFormat, final int width, final int height, final int border, final int format, final int type, final ByteBuffer pixels) {
        GL11.glTexImage2D(target.getId(), level, internalFormat, width, height, border, format, type, pixels);
    }
    
    @Override
    public void _texImage2D(final TextureTarget target, final int level, final int internalFormat, final int width, final int height, final int border, final int format, final int type, final MemoryBlock memoryBlock) {
        GL11.glTexImage2D(target.getId(), level, internalFormat, width, height, border, format, type, memoryBlock.address());
    }
    
    @Override
    public void pixelStore(final PixelStore parameterName, final int value) {
        GL11.glPixelStorei(parameterName.getId(), value);
    }
    
    @Override
    public void pixelStore(final PixelStore parameterName, final float value) {
        GL11.glPixelStoref(parameterName.getId(), value);
    }
    
    @Override
    public void texSubImage2D(final TextureTarget target, final int level, final int xOffset, final int yOffset, final int width, final int height, final int format, final int type, final ByteBuffer pixels) {
        GL11.glTexSubImage2D(target.getId(), level, xOffset, yOffset, width, height, format, type, pixels);
    }
    
    @Override
    public int getTexLevelParameterI(final TextureTarget target, final int level, final int parameter) {
        return GL11.glGetTexLevelParameteri(target.getId(), level, parameter);
    }
    
    @Override
    public void getTextureImage(final TextureTarget target, final int level, final int format, final int type, final ByteBuffer buffer) {
        GL11.glGetTexImage(target.getId(), level, format, type, buffer);
    }
    
    @Override
    public void bindBuffer(final BufferTarget target, final int buffer) {
        GL15.glBindBuffer(target.getId(), buffer);
        this.buffers.put((Object)target, buffer);
    }
    
    @Override
    public void bufferData(final BufferTarget target, final ByteBuffer data, final BufferUsage usage) {
        GL15.glBufferData(target.getId(), data, usage.getId());
    }
    
    @Override
    public void bufferData(final BufferTarget target, final IntBuffer data, final BufferUsage usage) {
        GL15.glBufferData(target.getId(), data, usage.getId());
    }
    
    @Override
    public void bufferData(final BufferTarget target, final ShortBuffer data, final BufferUsage usage) {
        GL15.glBufferData(target.getId(), data, usage.getId());
    }
    
    @Override
    public void bufferData(final BufferTarget target, final FloatBuffer data, final BufferUsage usage) {
        GL15.glBufferData(target.getId(), data, usage.getId());
    }
    
    @Override
    public void bufferData(final BufferTarget target, final DoubleBuffer data, final BufferUsage usage) {
        GL15.glBufferData(target.getId(), data, usage.getId());
    }
    
    @Override
    public void bufferData(final BufferTarget target, final LongBuffer data, final BufferUsage usage) {
        GL15.glBufferData(target.getId(), data, usage.getId());
    }
    
    @Override
    public void bufferData(final BufferTarget target, final long size, final BufferUsage usage) {
        GL15.glBufferData(target.getId(), size, usage.getId());
    }
    
    @Override
    public void bufferData(final BufferTarget target, final MemoryBlock block, final BufferUsage usage) {
        GL15.nglBufferData(target.getId(), block.size(), block.address(), usage.getId());
    }
    
    @Override
    public void bindBufferBase(final BufferTarget target, final int index, final int buffer) {
        OpenGLBufferBinder.bindBufferBase(target, index, buffer);
    }
    
    @Override
    public void bindBufferRange(final BufferTarget target, final int index, final int buffer, final long offset, final long size) {
        OpenGLBufferBinder.bindBufferRange(target, index, buffer, offset, size);
    }
    
    @Override
    public ByteBuffer mapBuffer(final BufferTarget target, final MapBufferAccess access) {
        return GL15.glMapBuffer(target.getId(), access.getId());
    }
    
    @Override
    public boolean unmapBuffer(final BufferTarget target) {
        return GL15.glUnmapBuffer(target.getId());
    }
    
    @Override
    public void shaderSource(final int shader, final CharSequence source) {
        final byte[] data = CharSequences.toByteArray(source, StandardCharsets.UTF_8);
        final ByteBuffer sourceBuffer = MemoryUtil.memAlloc(data.length + 1);
        sourceBuffer.put(data);
        sourceBuffer.put((byte)0);
        sourceBuffer.flip();
        try (final MemoryStack stack = MemoryStack.stackPush()) {
            final PointerBuffer pointerBuffer = stack.mallocPointer(1);
            pointerBuffer.put(sourceBuffer);
            GL20.nglShaderSource(shader, 1, pointerBuffer.address0(), 0L);
        }
        MemoryUtil.memFree((Buffer)sourceBuffer);
    }
    
    @Override
    public void compileShader(final int shader) {
        GL20.glCompileShader(shader);
    }
    
    @Override
    public void onShaderCompileError(final int shader, final Function<String, RuntimeException> errorFunction) {
        if (GL20.glGetShaderi(shader, 35713) == 1) {
            return;
        }
        throw errorFunction.apply(GL20.glGetShaderInfoLog(shader));
    }
    
    @Override
    public void onProgramLinkError(final int program, final Function<String, RuntimeException> errorFunction) {
        if (GL20.glGetProgrami(program, 35714) == 1) {
            return;
        }
        throw errorFunction.apply(GL20.glGetProgramInfoLog(program));
    }
    
    @Override
    public void bindAttributeLocation(final int programId, final int index, final CharSequence name) {
        GL20.glBindAttribLocation(programId, index, name);
    }
    
    @Override
    public void drawArrays(final DrawingMode mode, final int first, final int count) {
        GL11.glDrawArrays(mode.getId(), first, count);
        RenderProfiler.increaseRenderCall();
    }
    
    @Override
    public void drawArraysInstanced(final DrawingMode mode, final int first, final int count, final int primcount) {
        if (!OpenGLPreconditions.isGL31Supported()) {
            OpenGLPreconditions.assertValidCode(() -> "The glDrawArraysInstanced function is not available in the current context.");
            return;
        }
        GL31.glDrawArraysInstanced(mode.getId(), first, count, primcount);
        RenderProfiler.increaseRenderCall();
    }
    
    @Override
    public void drawElements(final DrawingMode mode, final int count, final DataType type) {
        GL11.glDrawElements(mode.getId(), count, type.getId(), 0L);
        RenderProfiler.increaseRenderCall();
    }
    
    @Override
    public void drawElementsInstanced(final DrawingMode mode, final int count, final DataType type, final int primcount) {
        if (!OpenGLPreconditions.isGL31Supported()) {
            OpenGLPreconditions.assertValidCode(() -> "The glDrawElementsInstanced function is not available in the current context.");
            return;
        }
        GL31.glDrawElementsInstanced(mode.getId(), count, type.getId(), 0L, primcount);
        RenderProfiler.increaseRenderCall();
    }
    
    @Override
    public void enableVertexAttributeArray(final int index) {
        GL20.glEnableVertexAttribArray(index);
    }
    
    @Override
    public void disableVertexAttributeArray(final int index) {
        GL20.glDisableVertexAttribArray(index);
    }
    
    @Override
    public void vertexAttributePointer(final int index, final int size, final DataType type, final boolean normalized, final int stride, final long pointer) {
        GL20.glVertexAttribPointer(index, size, type.getId(), normalized, stride, pointer);
    }
    
    @Override
    public void vertexAttributeIntPointer(final int index, final int size, final DataType type, final int stride, final long pointer) {
        if (!OpenGLPreconditions.isGL30Supported()) {
            OpenGLPreconditions.assertValidCode(() -> "The glVertexAttribIPointer function is not available in the current context.");
            return;
        }
        GL30.glVertexAttribIPointer(index, size, type.getId(), stride, pointer);
    }
    
    @Override
    public int getUniformLocation(final int programId, final CharSequence name) {
        return GL20.glGetUniformLocation(programId, name);
    }
    
    @Override
    public void uniform1fv(final int location, final FloatBuffer buffer) {
        GL20.glUniform1fv(location, buffer);
    }
    
    @Override
    public void uniform2fv(final int location, final FloatBuffer buffer) {
        GL20.glUniform2fv(location, buffer);
    }
    
    @Override
    public void uniform3fv(final int location, final FloatBuffer buffer) {
        GL20.glUniform3fv(location, buffer);
    }
    
    @Override
    public void uniform4fv(final int location, final FloatBuffer buffer) {
        GL20.glUniform4fv(location, buffer);
    }
    
    @Override
    public void uniform1i(final int location, final int value) {
        GL20.glUniform1i(location, value);
    }
    
    @Override
    public void uniform1iv(final int location, final IntBuffer buffer) {
        GL20.glUniform1iv(location, buffer);
    }
    
    @Override
    public void uniform2iv(final int location, final IntBuffer buffer) {
        GL20.glUniform2iv(location, buffer);
    }
    
    @Override
    public void uniform3iv(final int location, final IntBuffer buffer) {
        GL20.glUniform3iv(location, buffer);
    }
    
    @Override
    public void uniform4iv(final int location, final IntBuffer buffer) {
        GL20.glUniform4iv(location, buffer);
    }
    
    @Override
    public void uniformMatrix2fv(final int location, final boolean transpose, final FloatBuffer matrixBuffer) {
        GL20.glUniformMatrix2fv(location, transpose, matrixBuffer);
    }
    
    @Override
    public void uniformMatrix3fv(final int location, final boolean transpose, final FloatBuffer matrixBuffer) {
        GL20.glUniformMatrix3fv(location, transpose, matrixBuffer);
    }
    
    @Override
    public void uniformMatrix4fv(final int location, final boolean transpose, final FloatBuffer matrixBuffer) {
        GL20.glUniformMatrix4fv(location, transpose, matrixBuffer);
    }
    
    @Override
    public int getInteger(final int pname) {
        return GL11.glGetInteger(pname);
    }
    
    @Override
    public int getUniformBlockIndex(final int programId, final CharSequence name) {
        if (!OpenGLPreconditions.isGL31Supported()) {
            OpenGLPreconditions.assertValidCode(() -> "The glGetUniformBlockIndex function is not available in the current context.");
            return -1;
        }
        return GL31.glGetUniformBlockIndex(programId, name);
    }
    
    @Override
    public void uniformBlockBinding(final int programId, final int index, final int binding) {
        if (!OpenGLPreconditions.isGL31Supported()) {
            OpenGLPreconditions.assertValidCode(() -> "The glUniformBlockBinding function is not available in the current context.");
            return;
        }
        GL31.glUniformBlockBinding(programId, index, binding);
    }
    
    @Override
    public void onCreateFramebufferError(final Function<String, RuntimeException> errorFunction) {
        final String message = OpenGLFramebuffer.checkFramebufferComplete(FramebufferTarget.BOTH, null);
        if (message == null) {
            return;
        }
        throw errorFunction.apply(message);
    }
    
    @Override
    public void setGLFWIcon(final long handle, final InputStream stream) throws IOException {
        GLFWUtil.setIcon(handle, stream);
    }
    
    @Override
    public void glPushDebugGroup(final int id, final CharSequence message) {
        this.glPushDebugGroup(id, () -> message);
    }
    
    @Override
    public void glPushDebugGroup(final int id, final Supplier<CharSequence> message) {
        if (!this.isOpenGl43() || !Renderdoc.isLoaded()) {
            return;
        }
        GL43.glPushDebugGroup(33354, id, (CharSequence)message.get());
    }
    
    @Override
    public void glPopDebugGroup() {
        if (!this.isOpenGl43() || !Renderdoc.isLoaded()) {
            return;
        }
        GL43.glPopDebugGroup();
    }
    
    @Override
    public String getRenderer() {
        return this.renderer.get();
    }
    
    @Override
    public void onError(final Object... args) {
        for (int error = GL11.glGetError(); error != 0; error = GL11.glGetError()) {
            this.printError(error, args);
        }
    }
}
