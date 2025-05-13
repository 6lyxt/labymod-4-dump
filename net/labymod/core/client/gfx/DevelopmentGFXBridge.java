// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx;

import java.util.function.Supplier;
import net.labymod.api.client.gfx.util.ShadeType;
import net.labymod.api.client.gfx.pipeline.blaze3d.buffer.Blaze3DBufferSource;
import java.io.IOException;
import java.io.InputStream;
import net.labymod.api.client.gfx.buffer.VertexArrayObject;
import net.labymod.api.client.gfx.buffer.BufferObject;
import net.labymod.api.client.gfx.target.FramebufferTarget;
import net.labymod.api.client.gfx.DataType;
import net.labymod.api.client.gfx.DrawingMode;
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
import net.labymod.api.client.gfx.pipeline.backend.memory.MemoryBlock;
import java.nio.ByteBuffer;
import net.labymod.api.client.gfx.texture.PixelStore;
import net.labymod.api.client.gfx.texture.TextureFilter;
import net.labymod.api.client.gfx.texture.TextureWrapMode;
import net.labymod.api.client.gfx.texture.TextureParameterName;
import net.labymod.api.client.gfx.texture.TextureTarget;
import net.labymod.api.client.gfx.pipeline.util.TextureId;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.gfx.target.stencil.StencilOperation;
import net.labymod.api.client.gfx.AttributeMask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gfx.pipeline.program.RenderProgram;
import net.labymod.api.client.gfx.pipeline.backend.GFXBackend;
import net.labymod.api.client.gfx.GFXCapabilities;
import javax.inject.Inject;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.Blaze3DGlStatePipeline;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;

@Singleton
@Implements(value = GFXBridge.class, key = "gfx_bridge_development")
public class DevelopmentGFXBridge extends AbstractGFXBridge
{
    private final GFXBridge delegate;
    
    @Inject
    public DevelopmentGFXBridge(final Blaze3DGlStatePipeline blaze3DGlStatePipeline) {
        super(blaze3DGlStatePipeline);
        this.delegate = Laby.references().gfxBridge("gfx_bridge_production");
    }
    
    @Override
    public GFXCapabilities createCapabilities() {
        return ((AbstractGFXBridge)this.delegate).createCapabilities();
    }
    
    @Override
    public void printBoundBuffers() {
        this.delegate.printBoundBuffers();
    }
    
    @Override
    public GFXBackend backend() {
        return this.delegate.backend();
    }
    
    @Override
    public void setBackend(final GFXBackend backend) {
        this.delegate.setBackend(backend);
    }
    
    @Override
    public void storeBlaze3DStates() {
        this.delegate.storeBlaze3DStates();
        this.onError(new Object[0]);
    }
    
    @Override
    public void restoreBlaze3DStates() {
        this.delegate.restoreBlaze3DStates();
        this.onError(new Object[0]);
    }
    
    @Override
    public void invalidateBlaze3DBuffers() {
        this.delegate.invalidateBlaze3DBuffers();
        this.onError(new Object[0]);
    }
    
    @Override
    public Blaze3DGlStatePipeline blaze3DGlStatePipeline() {
        return this.delegate.blaze3DGlStatePipeline();
    }
    
    @Nullable
    @Override
    public RenderProgram getCurrentRenderProgram() {
        return this.delegate.getCurrentRenderProgram();
    }
    
    @Override
    public void bindRenderProgram(@NotNull final RenderProgram renderProgram) {
        this.delegate.bindRenderProgram(renderProgram);
    }
    
    @Override
    public void unbindRenderProgram() {
        this.delegate.unbindRenderProgram();
    }
    
    @Override
    public void unbindRenderProgram(final boolean invalidateBuffers) {
        this.delegate.unbindRenderProgram(invalidateBuffers);
    }
    
    @Override
    public long getCurrentContextHandle() {
        return this.delegate.getCurrentContextHandle();
    }
    
    @Override
    public void viewport(final int x, final int y, final int width, final int height) {
        this.delegate.viewport(x, y, width, height);
        this.onError("x", x, "y", y, "width", width, "height", height);
    }
    
    @Override
    public void clear(final AttributeMask... bits) {
        this.delegate.clear(bits);
        this.onError("bits", bits);
    }
    
    @Override
    public void clearColor(final float red, final float green, final float blue, final float alpha) {
        this.delegate.clearColor(red, green, blue, alpha);
        this.onError("red", red, "green", green, "blue", blue, "alpha", alpha);
    }
    
    @Override
    public void colorMask(final boolean red, final boolean green, final boolean blue, final boolean alpha) {
        this.delegate.colorMask(red, green, blue, alpha);
        this.onError("red", red, "green", green, "blue", blue, "alpha", alpha);
    }
    
    @Override
    public void enableStencil() {
        this.delegate.enableStencil();
        this.onError(new Object[0]);
    }
    
    @Override
    public void disableStencil() {
        this.delegate.disableStencil();
        this.onError(new Object[0]);
    }
    
    @Override
    public void stencilFunc(final int func, final int ref, final int mask) {
        this.delegate.stencilFunc(func, ref, mask);
        this.onError("func", func, "ref", ref, "mask", mask);
    }
    
    @Override
    public void stencilMask(final int mask) {
        this.delegate.stencilMask(mask);
        this.onError("mask", mask);
    }
    
    @Override
    public void stencilOp(final StencilOperation sfail, final StencilOperation dpfail, final StencilOperation dppass) {
        this.delegate.stencilOp(sfail, dpfail, dppass);
        this.onError("sfail", sfail, "dpfail", dpfail, "dppas", dppass);
    }
    
    @Override
    public void enableScissor() {
        this.delegate.enableScissor();
        this.onError(new Object[0]);
    }
    
    @Override
    public void scissor(final int x, final int y, final int width, final int height) {
        this.delegate.scissor(x, y, width, height);
        this.onError("x", x, "y", y, "width", width, "height", height);
    }
    
    @Override
    public void disableScissor() {
        this.delegate.disableScissor();
        this.onError(new Object[0]);
    }
    
    @Override
    public boolean isScissorActive() {
        return this.delegate.isScissorActive();
    }
    
    @Override
    public int genTextures() {
        final int textures = this.delegate.genTextures();
        this.onError("textures", textures);
        return textures;
    }
    
    @Override
    public void enableTexture() {
        this.delegate.enableTexture();
        this.onError(new Object[0]);
    }
    
    @Override
    public void disableTexture() {
        this.delegate.disableTexture();
        this.onError(new Object[0]);
    }
    
    @Override
    public void setActiveTexture(final int slot) {
        this.delegate.setActiveTexture(slot);
        this.onError("active-texture", slot);
    }
    
    @Override
    public void setDirectActiveTexture(final int value) {
        this.delegate.setDirectActiveTexture(value);
        this.onError("active-texture", value);
    }
    
    @Override
    public int getActiveTexture() {
        final int activeTexture = this.delegate.getActiveTexture();
        this.onError("active-texture", activeTexture);
        return activeTexture;
    }
    
    @Override
    public void bindResourceLocation(final ResourceLocation location) {
        this.delegate.bindResourceLocation(location);
        this.onError("location", location);
    }
    
    @Override
    public void bindResourceLocation(final ResourceLocation location, final boolean blur, final boolean mipmap) {
        this.delegate.bindResourceLocation(location, blur, mipmap);
        this.onError("location", location, "blur", blur, "mipmap", mipmap);
    }
    
    @Override
    public void bindTexture2D(final TextureId id) {
        this.delegate.bindTexture2D(id);
        this.onError("id", id.getId());
    }
    
    @Override
    public void bindTexture(final TextureTarget target, final TextureId id) {
        this.delegate.bindTexture(target, id);
        this.onError("target", target, "id", id.getId());
    }
    
    @Override
    public void texParameter(final TextureTarget target, final TextureParameterName parameterName, final float value) {
        this.delegate.texParameter(target, parameterName, value);
        this.onError("target", target, "parameterName", parameterName, "value", value);
    }
    
    @Override
    public void texParameter(final TextureTarget target, final TextureParameterName parameterName, final TextureWrapMode wrapMode) {
        this.delegate.texParameter(target, parameterName, wrapMode);
        this.onError("target", target, "parameterName", parameterName, "wrapMode", wrapMode);
    }
    
    @Override
    public void texParameter(final TextureTarget target, final TextureParameterName parameterName, final TextureFilter filter) {
        this.delegate.texParameter(target, parameterName, filter);
        this.onError("target", target, "parameterName", parameterName, "filter", filter);
    }
    
    @Override
    public void texParameter(final TextureTarget target, final TextureParameterName parameterName, final int value) {
        this.delegate.texParameter(target, parameterName, value);
        this.onError("target", target, "parameterName", parameterName, "value", value);
    }
    
    @Override
    public void pixelStore(final PixelStore parameterName, final int value) {
        this.delegate.pixelStore(parameterName, value);
        this.onError("parameterName", parameterName, "value", value);
    }
    
    @Override
    public void pixelStore(final PixelStore parameterName, final float value) {
        this.delegate.pixelStore(parameterName, value);
        this.onError("parameterName", parameterName, "value", value);
    }
    
    @Override
    public void texImage2D(final TextureTarget target, final int level, final int internalFormat, final int width, final int height, final int border, final int format, final int type, final ByteBuffer pixels) {
        this.delegate.texImage2D(target, level, internalFormat, width, height, border, format, type, pixels);
        this.onError("target", target, "level", level, "internalFormat", internalFormat, "width", width, "height", height, "border", border, "format", format, "type", type, "pixels", pixels);
    }
    
    @Override
    public void _texImage2D(final TextureTarget target, final int level, final int internalFormat, final int width, final int height, final int border, final int format, final int type, final MemoryBlock memoryBlock) {
        this.delegate._texImage2D(target, level, internalFormat, width, height, border, format, type, memoryBlock);
        this.onError("target", target, "level", level, "internalFormat", internalFormat, "width", width, "height", height, "border", border, "format", format, "type", type, "memoryBlock", memoryBlock);
    }
    
    @Override
    public void texSubImage2D(final TextureTarget target, final int level, final int xOffset, final int yOffset, final int width, final int height, final int format, final int type, final ByteBuffer pixels) {
        this.delegate.texSubImage2D(target, level, xOffset, yOffset, width, height, format, type, pixels);
        this.onError("target", target, "level", level, "xOffset", xOffset, "yOffset", yOffset, "width", width, "height", height, "format", format, "type", type, "pixels", pixels);
    }
    
    @Override
    public int getTexLevelParameterI(final TextureTarget target, final int level, final int parameter) {
        final int value = this.delegate.getTexLevelParameterI(target, level, parameter);
        this.onError("target", target, "level", level, "parameter", parameter);
        return value;
    }
    
    @Override
    public void getTextureImage(final TextureTarget target, final int level, final int format, final int type, final ByteBuffer buffer) {
        this.delegate.getTextureImage(target, level, format, type, buffer);
        this.onError("target", target, "level", level, "format", format, "type", type, "buffer", buffer);
    }
    
    @Override
    public void deleteTextures(final int texture) {
        this.delegate.deleteTextures(texture);
        this.onError("texture", texture);
    }
    
    @Override
    public void enableBlend() {
        this.delegate.enableBlend();
        this.onError(new Object[0]);
    }
    
    @Override
    public void disableBlend() {
        this.delegate.disableBlend();
        this.onError(new Object[0]);
    }
    
    @Override
    public void blendEquation(final int mode) {
        this.delegate.blendEquation(mode);
        this.onError("mode", mode);
    }
    
    @Override
    public void blendEquationSeparate(final int modeRGB, final int modeAlpha) {
        this.delegate.blendEquationSeparate(modeRGB, modeAlpha);
        this.onError("modeRGB", modeRGB, "modeAlpha", modeAlpha);
    }
    
    @Override
    public void defaultBlend() {
        this.delegate.defaultBlend();
        this.onError(new Object[0]);
    }
    
    @Override
    public void blend(final int sourceFactor, final int destinationFactor) {
        this.delegate.blend(sourceFactor, destinationFactor);
        this.onError("sourceFactor", sourceFactor, "destinationFactor", destinationFactor);
    }
    
    @Override
    public void blendSeparate(final int srcFactorRGB, final int dstFactorRGB, final int srcFactorAlpha, final int dstFactorAlpha) {
        this.delegate.blendSeparate(srcFactorRGB, dstFactorRGB, srcFactorAlpha, dstFactorAlpha);
        this.onError("srcFactorRGB", srcFactorRGB, "dstFactorRGB", dstFactorRGB, "srcFactorAlpha", srcFactorAlpha, "dstFactorAlpha", dstFactorAlpha);
    }
    
    @Override
    public void enableDepth() {
        this.delegate.enableDepth();
        this.onError(new Object[0]);
    }
    
    @Override
    public void depthFunc(final int func) {
        this.delegate.depthFunc(func);
        this.onError("func", func);
    }
    
    @Override
    public void depthMask(final boolean writeDepth) {
        this.delegate.depthMask(writeDepth);
        this.onError("writeDepth", writeDepth);
    }
    
    @Override
    public void disableDepth() {
        this.delegate.disableDepth();
        this.onError(new Object[0]);
    }
    
    @Override
    public void enableCull() {
        this.delegate.enableCull();
        this.onError(new Object[0]);
    }
    
    @Override
    public void disableCull() {
        this.delegate.disableCull();
        this.onError(new Object[0]);
    }
    
    @Override
    public void enablePolygonOffset() {
        this.delegate.disableCull();
        this.onError(new Object[0]);
    }
    
    @Override
    public void disablePolygonOffset() {
        this.delegate.disablePolygonOffset();
        this.onError(new Object[0]);
    }
    
    @Override
    public void polygonOffset(final float factor, final float units) {
        this.delegate.polygonOffset(factor, units);
        this.onError("factor", factor, "units", units);
    }
    
    @Override
    public void invalidateBuffers() {
        this.delegate.invalidateBuffers();
        this.onError(new Object[0]);
    }
    
    @Override
    public int genBuffers() {
        final int buffers = this.delegate.genBuffers();
        this.onError("buffers", buffers);
        return buffers;
    }
    
    @Override
    public void deleteBuffers(final int buffer) {
        this.delegate.deleteBuffers(buffer);
        this.onError("buffer", buffer);
    }
    
    @Override
    public void bindBuffer(final BufferTarget target, final int buffer) {
        this.delegate.bindBuffer(target, buffer);
        this.onError("target", target, "buffer", buffer);
    }
    
    @Override
    public void unbindBuffer(final BufferTarget target) {
        this.delegate.unbindBuffer(target);
        this.onError("target", target);
    }
    
    @Override
    public void bufferData(final BufferTarget target, final ByteBuffer data, final BufferUsage usage) {
        this.delegate.bufferData(target, data, usage);
        this.onError("target", target, "data", data, "usage", usage);
    }
    
    @Override
    public void bufferData(final BufferTarget target, final IntBuffer data, final BufferUsage usage) {
        this.delegate.bufferData(target, data, usage);
        this.onError("target", target, "data", data, "usage", usage);
    }
    
    @Override
    public void bufferData(final BufferTarget target, final ShortBuffer data, final BufferUsage usage) {
        this.delegate.bufferData(target, data, usage);
        this.onError("target", target, "data", data, "usage", usage);
    }
    
    @Override
    public void bufferData(final BufferTarget target, final FloatBuffer data, final BufferUsage usage) {
        this.delegate.bufferData(target, data, usage);
        this.onError("target", target, "data", data, "usage", usage);
    }
    
    @Override
    public void bufferData(final BufferTarget target, final DoubleBuffer data, final BufferUsage usage) {
        this.delegate.bufferData(target, data, usage);
        this.onError("target", target, "data", data, "usage", usage);
    }
    
    @Override
    public void bufferData(final BufferTarget target, final LongBuffer data, final BufferUsage usage) {
        this.delegate.bufferData(target, data, usage);
        this.onError("target", target, "data", data, "usage", usage);
    }
    
    @Override
    public void bufferData(final BufferTarget target, final long size, final BufferUsage usage) {
        this.delegate.bufferData(target, size, usage);
        this.onError("target", target, "size", size, "usage", usage);
    }
    
    @Override
    public void bufferData(final BufferTarget target, final MemoryBlock block, final BufferUsage usage) {
        this.delegate.bufferData(target, block, usage);
        this.onError("target", target, "block", block, "usage", usage);
    }
    
    @Override
    public void bindBufferBase(final BufferTarget target, final int index, final int buffer) {
        this.delegate.bindBufferBase(target, index, buffer);
        this.onError("target", target, "index", index, "buffer", buffer);
    }
    
    @Override
    public void bindBufferRange(final BufferTarget target, final int index, final int buffer, final long offset, final long size) {
        this.delegate.bindBufferRange(target, index, buffer, offset, size);
        this.onError("target", target, "index", index, "buffer", buffer, "offset", offset, "size", size);
    }
    
    @Override
    public ByteBuffer mapBuffer(final BufferTarget target, final MapBufferAccess access) {
        final ByteBuffer buffer = this.delegate.mapBuffer(target, access);
        this.onError("target", target, "access", access);
        return buffer;
    }
    
    @Override
    public boolean unmapBuffer(final BufferTarget target) {
        final boolean result = this.delegate.unmapBuffer(target);
        this.onError("target", target);
        return result;
    }
    
    @Override
    public int genVertexArrays() {
        final int vertexArrays = this.delegate.genVertexArrays();
        this.onError("vertexArrays", vertexArrays);
        return vertexArrays;
    }
    
    @Override
    public void bindVertexArray(final int array) {
        this.delegate.bindVertexArray(array);
        this.onError("array", array);
    }
    
    @Override
    public void unbindVertexArray() {
        this.delegate.unbindVertexArray();
        this.onError(new Object[0]);
    }
    
    @Override
    public void deleteVertexArrays(final int vao) {
        this.delegate.deleteVertexArrays(vao);
        this.onError("vao", vao);
    }
    
    @Override
    public int createProgram() {
        final int program = this.delegate.createProgram();
        this.onError("program", program);
        return program;
    }
    
    @Override
    public void attachShader(final int program, final int shader) {
        this.delegate.attachShader(program, shader);
        this.onError("program", program, "shader", shader);
    }
    
    @Override
    public void deleteShader(final int shader) {
        this.delegate.deleteShader(shader);
        this.onError("shader", shader);
    }
    
    @Override
    public void shaderSource(final int shader, final CharSequence source) {
        this.delegate.shaderSource(shader, source);
        this.onError("shader", shader, "source", source);
    }
    
    @Override
    public void compileShader(final int shader) {
        this.delegate.compileShader(shader);
        this.onError("shader", shader);
    }
    
    @Override
    public void onShaderCompileError(final int shader, final Function<String, RuntimeException> errorFunction) {
        this.delegate.onShaderCompileError(shader, errorFunction);
        this.onError("shader", shader, "errorFunction", errorFunction);
    }
    
    @Override
    public int createShader(final Shader.Type type) {
        final int shader = this.delegate.createShader(type);
        this.onError("shader", shader, "type", type);
        return shader;
    }
    
    @Override
    public void useProgram(final int programId) {
        this.delegate.useProgram(programId);
        this.onError("programId", programId);
    }
    
    @Override
    public void linkProgram(final int programId) {
        this.delegate.linkProgram(programId);
        this.onError("programId", programId);
    }
    
    @Override
    public void deleteProgram(final int programId) {
        this.delegate.deleteProgram(programId);
        this.onError("programId", programId);
    }
    
    @Override
    public void onProgramLinkError(final int program, final Function<String, RuntimeException> errorFunction) {
        this.delegate.onProgramLinkError(program, errorFunction);
        this.onError("program", program, "errorFunction", errorFunction);
    }
    
    @Override
    public void bindAttributeLocation(final int programId, final int index, final CharSequence name) {
        this.delegate.bindAttributeLocation(programId, index, name);
        this.onError("programId", programId, "index", index, "name", name);
    }
    
    @Override
    public void drawArrays(final DrawingMode mode, final int first, final int count) {
        this.delegate.drawArrays(mode, first, count);
        this.onError("mode", mode, "first", first, "count", count);
    }
    
    @Override
    public void drawArraysInstanced(final DrawingMode mode, final int first, final int count, final int primcount) {
        this.delegate.drawArraysInstanced(mode, first, count, primcount);
        this.onError("mode", mode, "first", first, "count", count, "primcount", primcount);
    }
    
    @Override
    public void drawElements(final DrawingMode mode, final int count, final DataType type) {
        this.delegate.drawElements(mode, count, type);
        this.onError("mode", mode, "count", count, "type", type);
    }
    
    @Override
    public void drawElementsInstanced(final DrawingMode mode, final int count, final DataType type, final int primcount) {
        this.delegate.drawElementsInstanced(mode, count, type, primcount);
        this.onError("mode", mode, "count", count, "type", type, "primcount", primcount);
    }
    
    @Override
    public void enableVertexAttributeArray(final int index) {
        this.delegate.enableVertexAttributeArray(index);
        this.onError("index", index);
    }
    
    @Override
    public void disableVertexAttributeArray(final int index) {
        this.delegate.disableVertexAttributeArray(index);
        this.onError("index", index);
    }
    
    @Override
    public void vertexAttributePointer(final int index, final int size, final DataType type, final boolean normalized, final int stride, final long pointer) {
        this.delegate.vertexAttributePointer(index, size, type, normalized, stride, pointer);
        this.onError("index", index, "size", size, "type", type, "normalized", normalized, "stride", stride, "pointer", pointer);
    }
    
    @Override
    public void vertexAttributeIntPointer(final int index, final int size, final DataType type, final int stride, final long pointer) {
        this.delegate.vertexAttributeIntPointer(index, size, type, stride, pointer);
        this.onError("index", index, "size", size, "type", type, "stride", stride, "pointer", pointer);
    }
    
    @Override
    public int getUniformLocation(final int programId, final CharSequence name) {
        final int uniformLocation = this.delegate.getUniformLocation(programId, name);
        this.onError("uniformLocation", uniformLocation, "programId", programId, "name", name);
        return uniformLocation;
    }
    
    @Override
    public void uniform1fv(final int location, final FloatBuffer buffer) {
        this.delegate.uniform1fv(location, buffer);
        this.onError("location", location, "buffer", buffer);
    }
    
    @Override
    public void uniform2fv(final int location, final FloatBuffer buffer) {
        this.delegate.uniform2fv(location, buffer);
        this.onError("location", location, "buffer", buffer);
    }
    
    @Override
    public void uniform3fv(final int location, final FloatBuffer buffer) {
        this.delegate.uniform3fv(location, buffer);
        this.onError("location", location, "buffer", buffer);
    }
    
    @Override
    public void uniform4fv(final int location, final FloatBuffer buffer) {
        this.delegate.uniform4fv(location, buffer);
        this.onError("location", location, "buffer", buffer);
    }
    
    @Override
    public void uniform1i(final int location, final int value) {
        this.delegate.uniform1i(location, value);
        this.onError("location", location, "value", value);
    }
    
    @Override
    public void uniform1iv(final int location, final IntBuffer buffer) {
        this.delegate.uniform1iv(location, buffer);
        this.onError("location", location, "buffer", buffer);
    }
    
    @Override
    public void uniform2iv(final int location, final IntBuffer buffer) {
        this.delegate.uniform2iv(location, buffer);
        this.onError("location", location, "buffer", buffer);
    }
    
    @Override
    public void uniform3iv(final int location, final IntBuffer buffer) {
        this.delegate.uniform3iv(location, buffer);
        this.onError("location", location, "buffer", buffer);
    }
    
    @Override
    public void uniform4iv(final int location, final IntBuffer buffer) {
        this.delegate.uniform4iv(location, buffer);
        this.onError("location", location, "buffer", buffer);
    }
    
    @Override
    public void uniformMatrix2fv(final int location, final boolean transpose, final FloatBuffer matrixBuffer) {
        this.delegate.uniformMatrix2fv(location, transpose, matrixBuffer);
        this.onError("location", location, "transpose", transpose, "matrixBuffer", matrixBuffer);
    }
    
    @Override
    public void uniformMatrix3fv(final int location, final boolean transpose, final FloatBuffer matrixBuffer) {
        this.delegate.uniformMatrix3fv(location, transpose, matrixBuffer);
        this.onError("location", location, "transpose", transpose, "matrixBuffer", matrixBuffer);
    }
    
    @Override
    public void uniformMatrix4fv(final int location, final boolean transpose, final FloatBuffer matrixBuffer) {
        this.delegate.uniformMatrix4fv(location, transpose, matrixBuffer);
        this.onError("location", location, "transpose", transpose, "matrixBuffer", matrixBuffer);
    }
    
    @Override
    public int getUniformBlockIndex(final int programId, final CharSequence name) {
        final int index = this.delegate.getUniformBlockIndex(programId, name);
        this.onError("programId", programId, "name", name);
        return index;
    }
    
    @Override
    public void uniformBlockBinding(final int programId, final int index, final int binding) {
        this.delegate.uniformBlockBinding(programId, index, binding);
        this.onError("programId", programId, "index", index, "binding", binding);
    }
    
    @Override
    public int genFramebuffers() {
        final int framebuffers = this.delegate.genFramebuffers();
        this.onError("framebuffers", framebuffers);
        return framebuffers;
    }
    
    @Override
    public void bindFramebuffer(final FramebufferTarget target, final int id) {
        this.delegate.bindFramebuffer(target, id);
        this.onError("target", target, "id", id);
    }
    
    @Override
    public void framebufferTexture2D(final FramebufferTarget target, final int attachment, final TextureTarget texTarget, final int texture, final int level) {
        this.delegate.framebufferTexture2D(target, attachment, texTarget, texture, level);
        this.onError("target", target, "attachment", attachment, "texTarget", texTarget, "texture", texture, "level", level);
    }
    
    @Override
    public void deleteFramebuffers(final int framebuffer) {
        this.delegate.deleteFramebuffers(framebuffer);
        this.onError("framebuffer", framebuffer);
    }
    
    @Override
    public void onCreateFramebufferError(final Function<String, RuntimeException> errorFunction) {
        this.delegate.onCreateFramebufferError(errorFunction);
        this.onError("errorFunction", errorFunction);
    }
    
    @Override
    public int getBindingFramebuffer() {
        final int bindingFramebuffer = this.delegate.getBindingFramebuffer();
        this.onError("bindingFramebuffer", bindingFramebuffer);
        return bindingFramebuffer;
    }
    
    @Override
    public BufferObject createBuffer(final BufferTarget target, final BufferUsage usage) {
        final BufferObject buffer = this.delegate.createBuffer(target, usage);
        this.onError("buffer", buffer, "target", target, "usage", usage);
        return buffer;
    }
    
    @Override
    public VertexArrayObject createVertexArray() {
        final VertexArrayObject vertexArray = this.delegate.createVertexArray();
        this.onError("vertexArray", vertexArray);
        return vertexArray;
    }
    
    @Override
    public GFXCapabilities capabilities() {
        return this.delegate.capabilities();
    }
    
    @Override
    public void initializeCapabilities() {
        this.delegate.initializeCapabilities();
        this.onError(new Object[0]);
    }
    
    @Override
    public void setCursorPosition(final double x, final double y) {
        this.delegate.setCursorPosition(x, y);
    }
    
    @Override
    public void setGLFWIcon(final long handle, final InputStream stream) throws IOException {
        this.delegate.setGLFWIcon(handle, stream);
    }
    
    @Override
    public Blaze3DBufferSource blaze3DBufferSource() {
        return this.delegate.blaze3DBufferSource();
    }
    
    @Override
    public int getInteger(final int pname) {
        final int value = this.delegate.getInteger(pname);
        this.onError("pname", pname);
        return value;
    }
    
    @Override
    public void color4f(final float red, final float green, final float blue, final float alpha) {
        this.delegate.color4f(red, green, blue, alpha);
        this.onError("red", red, "green", green, "blue", blue, "alpha", alpha);
    }
    
    @Override
    public void linesSmooth() {
        this.delegate.linesSmooth();
        this.onError(new Object[0]);
    }
    
    @Override
    public void shadeModel(final ShadeType type) {
        this.delegate.shadeModel(type);
        this.onError("type", type);
    }
    
    @Override
    public void shadeFlat() {
        this.delegate.shadeFlat();
        this.onError(new Object[0]);
    }
    
    @Override
    public void shadeSmooth() {
        this.delegate.shadeSmooth();
        this.onError(new Object[0]);
    }
    
    @Override
    public void clearDepth() {
        this.delegate.clearDepth();
        this.onError(new Object[0]);
    }
    
    @Override
    public void disableLighting() {
        this.delegate.disableLighting();
        this.onError(new Object[0]);
    }
    
    @Override
    public void disableRescaleNormal() {
        this.delegate.disableRescaleNormal();
        this.onError(new Object[0]);
    }
    
    @Override
    public void enableRescaleNormal() {
        this.delegate.enableRescaleNormal();
        this.onError(new Object[0]);
    }
    
    @Override
    public void enableLighting() {
        this.delegate.enableLighting();
        this.onError(new Object[0]);
    }
    
    @Override
    public void polygonMode(final int face, final int mode) {
        this.delegate.polygonMode(face, mode);
        this.onError("face", face, "mode", mode);
    }
    
    @Override
    public void glPushDebugGroup(final int id, final CharSequence message) {
        this.delegate.glPushDebugGroup(id, message);
    }
    
    @Override
    public void glPushDebugGroup(final int id, final Supplier<CharSequence> message) {
        this.delegate.glPushDebugGroup(id, message);
    }
    
    @Override
    public void glPopDebugGroup() {
        this.delegate.glPopDebugGroup();
    }
    
    @Override
    public String getRenderer() {
        return this.delegate.getRenderer();
    }
    
    @Override
    public void onError(final Object... args) {
        ((AbstractGFXBridge)this.delegate).onError(args);
    }
}
