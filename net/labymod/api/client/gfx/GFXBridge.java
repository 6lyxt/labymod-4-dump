// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx;

import java.util.function.Supplier;
import net.labymod.api.client.gfx.util.ShadeType;
import net.labymod.api.client.gfx.pipeline.blaze3d.buffer.Blaze3DBufferSource;
import java.io.IOException;
import java.io.InputStream;
import net.labymod.api.client.gfx.buffer.VertexArrayObject;
import net.labymod.api.client.gfx.buffer.BufferObject;
import net.labymod.api.client.gfx.target.FramebufferTarget;
import net.labymod.api.client.gfx.shader.Shader;
import java.util.function.Function;
import net.labymod.api.client.gfx.buffer.MapBufferAccess;
import java.nio.LongBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.nio.IntBuffer;
import net.labymod.api.client.gfx.buffer.BufferUsage;
import net.labymod.api.client.gfx.buffer.BufferTarget;
import net.labymod.api.client.gfx.color.blend.BlendParameter;
import net.labymod.api.client.gfx.pipeline.backend.memory.MemoryBlock;
import java.nio.ByteBuffer;
import net.labymod.api.client.gfx.texture.PixelStore;
import net.labymod.api.client.gfx.texture.TextureWrapMode;
import net.labymod.api.client.gfx.texture.TextureFilter;
import net.labymod.api.client.gfx.texture.TextureParameterName;
import net.labymod.api.client.gfx.texture.TextureTarget;
import net.labymod.api.client.gfx.pipeline.util.TextureId;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.gfx.target.stencil.StencilOperation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gfx.pipeline.program.RenderProgram;
import net.labymod.api.client.gfx.pipeline.Blaze3DGlStatePipeline;
import net.labymod.api.client.gfx.pipeline.backend.GFXBackend;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable(named = true)
public interface GFXBridge
{
    void printBoundBuffers();
    
    GFXBackend backend();
    
    void setBackend(final GFXBackend p0);
    
    void storeBlaze3DStates();
    
    void restoreBlaze3DStates();
    
    default void storeAndRestoreBlaze3DStates(final Runnable renderer) {
        if (renderer == null) {
            return;
        }
        this.storeBlaze3DStates();
        renderer.run();
        this.restoreBlaze3DStates();
    }
    
    void invalidateBlaze3DBuffers();
    
    Blaze3DGlStatePipeline blaze3DGlStatePipeline();
    
    @Nullable
    RenderProgram getCurrentRenderProgram();
    
    void bindRenderProgram(@NotNull final RenderProgram p0);
    
    default void unbindRenderProgram() {
        this.unbindRenderProgram(true);
    }
    
    void unbindRenderProgram(final boolean p0);
    
    long getCurrentContextHandle();
    
    void enableRescaleNormal();
    
    void disableRescaleNormal();
    
    void enableLighting();
    
    void disableLighting();
    
    void viewport(final int p0, final int p1, final int p2, final int p3);
    
    default void clearDepth() {
        this.clear(AttributeMask.DEPTH_BUFFER_BIT);
    }
    
    void clear(final AttributeMask... p0);
    
    void clearColor(final float p0, final float p1, final float p2, final float p3);
    
    void colorMask(final boolean p0, final boolean p1, final boolean p2, final boolean p3);
    
    void enableStencil();
    
    void disableStencil();
    
    void stencilFunc(final int p0, final int p1, final int p2);
    
    void stencilMask(final int p0);
    
    void stencilOp(final StencilOperation p0, final StencilOperation p1, final StencilOperation p2);
    
    void enableScissor();
    
    void scissor(final int p0, final int p1, final int p2, final int p3);
    
    void disableScissor();
    
    boolean isScissorActive();
    
    int genTextures();
    
    void enableTexture();
    
    void disableTexture();
    
    void setActiveTexture(final int p0);
    
    void setDirectActiveTexture(final int p0);
    
    int getActiveTexture();
    
    default void bindResourceLocation(final ResourceLocation location) {
        this.bindResourceLocation(location, false, false);
    }
    
    void bindResourceLocation(final ResourceLocation p0, final boolean p1, final boolean p2);
    
    default void bindTexture2D(final TextureId id) {
        this.bindTexture(TextureTarget.TEXTURE_2D, id);
    }
    
    default void bindTexture(final TextureTarget target, final int id) {
        this.bindTexture(target, TextureId.of(id));
    }
    
    void bindTexture(final TextureTarget p0, final TextureId p1);
    
    void texParameter(final TextureTarget p0, final TextureParameterName p1, final int p2);
    
    void texParameter(final TextureTarget p0, final TextureParameterName p1, final float p2);
    
    void texParameter(final TextureTarget p0, final TextureParameterName p1, final TextureFilter p2);
    
    void texParameter(final TextureTarget p0, final TextureParameterName p1, final TextureWrapMode p2);
    
    void pixelStore(final PixelStore p0, final int p1);
    
    void pixelStore(final PixelStore p0, final float p1);
    
    void texImage2D(final TextureTarget p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final ByteBuffer p8);
    
    void _texImage2D(final TextureTarget p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final MemoryBlock p8);
    
    void texSubImage2D(final TextureTarget p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final ByteBuffer p8);
    
    int getTexLevelParameterI(final TextureTarget p0, final int p1, final int p2);
    
    void getTextureImage(final TextureTarget p0, final int p1, final int p2, final int p3, final ByteBuffer p4);
    
    void deleteTextures(final int p0);
    
    void enableBlend();
    
    void disableBlend();
    
    void blendEquation(final int p0);
    
    void blendEquationSeparate(final int p0, final int p1);
    
    default void defaultBlend() {
        this.blendSeparate(770, 771, 1, 0);
    }
    
    void blend(final int p0, final int p1);
    
    default void blend(final BlendParameter sourceFactor, final BlendParameter destinationFactor) {
        this.blend(sourceFactor.getId(), destinationFactor.getId());
    }
    
    void blendSeparate(final int p0, final int p1, final int p2, final int p3);
    
    default void blendSeparate(final BlendParameter sourceFactorRGB, final BlendParameter destinationFactorRGB, final BlendParameter sourceFactorAlpha, final BlendParameter destinationFactorAlpha) {
        this.blendSeparate(sourceFactorRGB.getId(), destinationFactorRGB.getId(), sourceFactorAlpha.getId(), destinationFactorAlpha.getId());
    }
    
    @Deprecated(forRemoval = true, since = "4.2.41")
    default void blendSeparate(final GFXObject sourceFactorRGB, final GFXObject destinationFactorRGB, final GFXObject sourceFactorAlpha, final GFXObject destinationFactorAlpha) {
        this.blendSeparate(sourceFactorRGB.getHandle(), destinationFactorRGB.getHandle(), sourceFactorAlpha.getHandle(), destinationFactorAlpha.getHandle());
    }
    
    void enableDepth();
    
    void depthFunc(final int p0);
    
    @Deprecated(forRemoval = true, since = "4.2.41")
    default void depthFunc(final GFXObject depthFunction) {
        this.depthFunc(depthFunction.getHandle());
    }
    
    void depthMask(final boolean p0);
    
    void disableDepth();
    
    void enableCull();
    
    void disableCull();
    
    void enablePolygonOffset();
    
    void disablePolygonOffset();
    
    void polygonOffset(final float p0, final float p1);
    
    void invalidateBuffers();
    
    int genBuffers();
    
    void deleteBuffers(final int p0);
    
    void bindBuffer(final BufferTarget p0, final int p1);
    
    default void unbindBuffer(final BufferTarget target) {
        this.bindBuffer(target, 0);
    }
    
    void bufferData(final BufferTarget p0, final ByteBuffer p1, final BufferUsage p2);
    
    void bufferData(final BufferTarget p0, final IntBuffer p1, final BufferUsage p2);
    
    void bufferData(final BufferTarget p0, final ShortBuffer p1, final BufferUsage p2);
    
    void bufferData(final BufferTarget p0, final FloatBuffer p1, final BufferUsage p2);
    
    void bufferData(final BufferTarget p0, final DoubleBuffer p1, final BufferUsage p2);
    
    void bufferData(final BufferTarget p0, final LongBuffer p1, final BufferUsage p2);
    
    void bufferData(final BufferTarget p0, final long p1, final BufferUsage p2);
    
    void bufferData(final BufferTarget p0, final MemoryBlock p1, final BufferUsage p2);
    
    void bindBufferBase(final BufferTarget p0, final int p1, final int p2);
    
    void bindBufferRange(final BufferTarget p0, final int p1, final int p2, final long p3, final long p4);
    
    ByteBuffer mapBuffer(final BufferTarget p0, final MapBufferAccess p1);
    
    boolean unmapBuffer(final BufferTarget p0);
    
    int genVertexArrays();
    
    void bindVertexArray(final int p0);
    
    default void unbindVertexArray() {
        this.bindVertexArray(0);
    }
    
    void deleteVertexArrays(final int p0);
    
    int createProgram();
    
    void attachShader(final int p0, final int p1);
    
    void deleteShader(final int p0);
    
    void shaderSource(final int p0, final CharSequence p1);
    
    void compileShader(final int p0);
    
    void onShaderCompileError(final int p0, final Function<String, RuntimeException> p1);
    
    int createShader(final Shader.Type p0);
    
    void useProgram(final int p0);
    
    void linkProgram(final int p0);
    
    void deleteProgram(final int p0);
    
    void onProgramLinkError(final int p0, final Function<String, RuntimeException> p1);
    
    void bindAttributeLocation(final int p0, final int p1, final CharSequence p2);
    
    void drawArrays(final DrawingMode p0, final int p1, final int p2);
    
    void drawArraysInstanced(final DrawingMode p0, final int p1, final int p2, final int p3);
    
    void drawElements(final DrawingMode p0, final int p1, final DataType p2);
    
    void drawElementsInstanced(final DrawingMode p0, final int p1, final DataType p2, final int p3);
    
    void enableVertexAttributeArray(final int p0);
    
    void disableVertexAttributeArray(final int p0);
    
    void vertexAttributePointer(final int p0, final int p1, final DataType p2, final boolean p3, final int p4, final long p5);
    
    void vertexAttributeIntPointer(final int p0, final int p1, final DataType p2, final int p3, final long p4);
    
    int getUniformLocation(final int p0, final CharSequence p1);
    
    void uniform1fv(final int p0, final FloatBuffer p1);
    
    void uniform2fv(final int p0, final FloatBuffer p1);
    
    void uniform3fv(final int p0, final FloatBuffer p1);
    
    void uniform4fv(final int p0, final FloatBuffer p1);
    
    void uniform1i(final int p0, final int p1);
    
    void uniform1iv(final int p0, final IntBuffer p1);
    
    void uniform2iv(final int p0, final IntBuffer p1);
    
    void uniform3iv(final int p0, final IntBuffer p1);
    
    void uniform4iv(final int p0, final IntBuffer p1);
    
    void uniformMatrix2fv(final int p0, final boolean p1, final FloatBuffer p2);
    
    void uniformMatrix3fv(final int p0, final boolean p1, final FloatBuffer p2);
    
    void uniformMatrix4fv(final int p0, final boolean p1, final FloatBuffer p2);
    
    int getUniformBlockIndex(final int p0, final CharSequence p1);
    
    void uniformBlockBinding(final int p0, final int p1, final int p2);
    
    int genFramebuffers();
    
    void bindFramebuffer(final FramebufferTarget p0, final int p1);
    
    void framebufferTexture2D(final FramebufferTarget p0, final int p1, final TextureTarget p2, final int p3, final int p4);
    
    void deleteFramebuffers(final int p0);
    
    void onCreateFramebufferError(final Function<String, RuntimeException> p0);
    
    int getBindingFramebuffer();
    
    BufferObject createBuffer(final BufferTarget p0, final BufferUsage p1);
    
    VertexArrayObject createVertexArray();
    
    GFXCapabilities capabilities();
    
    void initializeCapabilities();
    
    void setCursorPosition(final double p0, final double p1);
    
    void setGLFWIcon(final long p0, final InputStream p1) throws IOException;
    
    Blaze3DBufferSource blaze3DBufferSource();
    
    int getInteger(final int p0);
    
    void color4f(final float p0, final float p1, final float p2, final float p3);
    
    default void shadeSmooth() {
        this.shadeModel(ShadeType.SMOOTH);
    }
    
    default void shadeFlat() {
        this.shadeModel(ShadeType.FLAT);
    }
    
    void shadeModel(final ShadeType p0);
    
    default void linesSmooth() {
    }
    
    void polygonMode(final int p0, final int p1);
    
    void glPushDebugGroup(final int p0, final CharSequence p1);
    
    void glPushDebugGroup(final int p0, final Supplier<CharSequence> p1);
    
    void glPopDebugGroup();
    
    String getRenderer();
}
