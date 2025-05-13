// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx;

import java.util.stream.Stream;
import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.core.client.resources.texture.DefaultAbstractTextureRepository;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.gfx.util.ShadeType;
import net.labymod.api.client.gfx.shader.Shader;
import net.labymod.api.client.gfx.texture.TextureTarget;
import net.labymod.api.client.gfx.target.FramebufferTarget;
import net.labymod.api.client.gfx.buffer.VertexArrayObject;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.buffer.BufferObject;
import net.labymod.api.client.gfx.buffer.BufferUsage;
import net.labymod.api.client.gfx.target.stencil.StencilOperation;
import net.labymod.api.client.gfx.AttributeMask;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.labymod.api.models.version.Version;
import net.labymod.api.client.gfx.opengl.NamedOpenGLVersion;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.labymod.api.client.gfx.pipeline.backend.GFXBackend;
import net.labymod.api.client.gfx.GFXCapabilities;
import net.labymod.api.client.gfx.pipeline.program.RenderProgram;
import net.labymod.api.client.gfx.pipeline.blaze3d.buffer.Blaze3DBufferSource;
import net.labymod.api.client.gfx.pipeline.Blaze3DGlStatePipeline;
import net.labymod.api.util.Lazy;
import net.labymod.api.client.gfx.buffer.BufferTarget;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.client.gfx.GFXBridge;

public abstract class AbstractGFXBridge implements GFXBridge
{
    private static final Logging LOGGER;
    private static final StackWalker WALKER;
    protected final Object2IntMap<BufferTarget> buffers;
    private final Lazy<Boolean> openGl43;
    protected int vaoId;
    protected final Blaze3DGlStatePipeline blaze3DGlStatePipeline;
    protected Blaze3DBufferSource blaze3DBufferSource;
    private RenderProgram currentRenderProgram;
    private GFXCapabilities capabilities;
    private GFXBackend backend;
    
    public AbstractGFXBridge(final Blaze3DGlStatePipeline blaze3DGlStatePipeline) {
        this.buffers = (Object2IntMap<BufferTarget>)new Object2IntOpenHashMap();
        this.openGl43 = Lazy.of(() -> this.capabilities().isSupported(NamedOpenGLVersion.GL43));
        this.vaoId = 0;
        this.blaze3DBufferSource = null;
        this.blaze3DGlStatePipeline = blaze3DGlStatePipeline;
    }
    
    public GFXCapabilities createCapabilities() {
        return this.backend.capabilities();
    }
    
    @Override
    public void printBoundBuffers() {
        for (Object2IntMap.Entry<BufferTarget> entry : this.buffers.object2IntEntrySet()) {
            final BufferTarget target = (BufferTarget)entry.getKey();
            final int id = entry.getIntValue();
            if (id == 0) {
                continue;
            }
            AbstractGFXBridge.LOGGER.info(String.valueOf(target) + ": " + id, new Object[0]);
        }
    }
    
    @Override
    public GFXBackend backend() {
        if (this.backend == null) {
            throw new IllegalStateException("No GFXBackend has been set. Please call GFXBridge#setBacked first.");
        }
        return this.backend;
    }
    
    @Override
    public void setBackend(final GFXBackend backend) {
        if (this.backend != null) {
            return;
        }
        this.backend = backend;
    }
    
    @Override
    public void enableRescaleNormal() {
    }
    
    @Override
    public void disableRescaleNormal() {
    }
    
    @Override
    public void enableLighting() {
    }
    
    @Override
    public void disableLighting() {
    }
    
    @Override
    public void storeBlaze3DStates() {
        this.blaze3DGlStatePipeline.storeStates();
    }
    
    @Override
    public void restoreBlaze3DStates() {
        this.blaze3DGlStatePipeline.restoreStates();
    }
    
    @Override
    public Blaze3DGlStatePipeline blaze3DGlStatePipeline() {
        return this.blaze3DGlStatePipeline;
    }
    
    @Override
    public void viewport(final int x, final int y, final int width, final int height) {
        this.blaze3DGlStatePipeline.viewport(x, y, width, height);
    }
    
    @Nullable
    @Override
    public RenderProgram getCurrentRenderProgram() {
        return this.currentRenderProgram;
    }
    
    @Override
    public void bindRenderProgram(@NotNull final RenderProgram renderProgram) {
        if (this.currentRenderProgram == null) {
            this.storeBlaze3DStates();
        }
        if (!Objects.equals(renderProgram, this.currentRenderProgram)) {
            this.unbindRenderProgram();
            (this.currentRenderProgram = renderProgram).apply();
        }
    }
    
    @Override
    public void unbindRenderProgram(final boolean invalidateBuffers) {
        if (this.currentRenderProgram == null) {
            return;
        }
        this.currentRenderProgram.clear();
        this.currentRenderProgram = null;
        this.restoreBlaze3DStates();
        if (invalidateBuffers) {
            this.invalidateBlaze3DBuffers();
        }
    }
    
    @Override
    public void invalidateBlaze3DBuffers() {
        this.blaze3DGlStatePipeline.invalidateBuffers();
    }
    
    @Override
    public void clear(final AttributeMask... bits) {
        this.blaze3DGlStatePipeline.clear(bits);
    }
    
    @Override
    public void clearColor(final float red, final float green, final float blue, final float alpha) {
        this.blaze3DGlStatePipeline.clearColor(red, green, blue, alpha);
    }
    
    @Override
    public void colorMask(final boolean red, final boolean green, final boolean blue, final boolean alpha) {
        this.blaze3DGlStatePipeline.colorMask(red, green, blue, alpha);
    }
    
    @Override
    public void depthMask(final boolean writeDepth) {
        this.blaze3DGlStatePipeline.depthMask(writeDepth);
    }
    
    @Override
    public void stencilFunc(final int func, final int ref, final int mask) {
        this.blaze3DGlStatePipeline.stencilFunc(func, ref, mask);
    }
    
    @Override
    public void stencilMask(final int mask) {
        this.blaze3DGlStatePipeline.stencilMask(mask);
    }
    
    @Override
    public void stencilOp(final StencilOperation sfail, final StencilOperation dpfail, final StencilOperation dppass) {
        this.blaze3DGlStatePipeline.stencilOp(sfail, dpfail, dppass);
    }
    
    @Override
    public void enableScissor() {
        this.blaze3DGlStatePipeline.enableScissor();
    }
    
    @Override
    public void scissor(final int x, final int y, final int width, final int height) {
        this.blaze3DGlStatePipeline.scissor(x, y, width, height);
    }
    
    @Override
    public void disableScissor() {
        this.blaze3DGlStatePipeline.disableScissor();
    }
    
    @Override
    public boolean isScissorActive() {
        return this.blaze3DGlStatePipeline.isScissorActive();
    }
    
    @Override
    public void enableTexture() {
        this.blaze3DGlStatePipeline.enableTexture();
    }
    
    @Override
    public void disableTexture() {
        this.blaze3DGlStatePipeline.disableTexture();
    }
    
    @Override
    public void setDirectActiveTexture(final int value) {
        this.blaze3DGlStatePipeline.setActiveTexture(value);
    }
    
    @Override
    public int getActiveTexture() {
        return this.blaze3DGlStatePipeline.getActiveTexture();
    }
    
    @Override
    public void deleteTextures(final int texture) {
        this.blaze3DGlStatePipeline.deleteTexture(texture);
    }
    
    @Override
    public void enableBlend() {
        this.blaze3DGlStatePipeline.enableBlend();
    }
    
    @Override
    public void disableBlend() {
        this.blaze3DGlStatePipeline.disableBlend();
    }
    
    @Override
    public void blendEquation(final int mode) {
        this.blaze3DGlStatePipeline.blendEquation(mode);
    }
    
    @Override
    public void blend(final int sourceFactor, final int destinationFactor) {
        this.blaze3DGlStatePipeline.blendFunc(sourceFactor, destinationFactor);
    }
    
    @Override
    public void blendSeparate(final int srcFactorRGB, final int dstFactorRGB, final int srcFactorAlpha, final int dstFactorAlpha) {
        this.blaze3DGlStatePipeline.blendFuncSeparate(srcFactorRGB, dstFactorRGB, srcFactorAlpha, dstFactorAlpha);
    }
    
    @Override
    public void enableDepth() {
        this.blaze3DGlStatePipeline.enableDepthTest();
    }
    
    @Override
    public void depthFunc(final int func) {
        this.blaze3DGlStatePipeline.depthFunc(func);
    }
    
    @Override
    public void disableDepth() {
        this.blaze3DGlStatePipeline.disableDepthTest();
    }
    
    @Override
    public void enableCull() {
        this.blaze3DGlStatePipeline.enableCull();
    }
    
    @Override
    public void disableCull() {
        this.blaze3DGlStatePipeline.disableCull();
    }
    
    @Override
    public void enablePolygonOffset() {
        this.blaze3DGlStatePipeline.enablePolygonOffset();
    }
    
    @Override
    public void disablePolygonOffset() {
        this.blaze3DGlStatePipeline.disablePolygonOffset();
    }
    
    @Override
    public void polygonOffset(final float factor, final float units) {
        this.blaze3DGlStatePipeline.polygonOffset(factor, units);
    }
    
    @Override
    public void invalidateBuffers() {
        for (final Object2IntMap.Entry<BufferTarget> entry : this.buffers.object2IntEntrySet()) {
            final int id = entry.getIntValue();
            if (id == 0) {
                continue;
            }
            this.bindBuffer((BufferTarget)entry.getKey(), 0);
        }
        if (this.capabilities().isArbVertexArrayObjectSupported() || NamedOpenGLVersion.GL30.isSupported()) {
            this.unbindVertexArray();
        }
        this.invalidateBlaze3DBuffers();
    }
    
    @Override
    public BufferObject createBuffer(final BufferTarget target, final BufferUsage usage) {
        return Laby.references().bufferObjectFactory().create(target, usage);
    }
    
    @Override
    public VertexArrayObject createVertexArray() {
        return Laby.references().vertexArrayObjectFactory().create();
    }
    
    @Override
    public int getBindingFramebuffer() {
        return this.blaze3DGlStatePipeline.getBindingFramebuffer();
    }
    
    @Override
    public int genFramebuffers() {
        return this.blaze3DGlStatePipeline.genFramebuffers();
    }
    
    @Override
    public void bindFramebuffer(final FramebufferTarget target, final int id) {
        this.blaze3DGlStatePipeline.bindFramebuffer(target, id);
    }
    
    @Override
    public void framebufferTexture2D(final FramebufferTarget target, final int attachment, final TextureTarget texTarget, final int texture, final int level) {
        this.blaze3DGlStatePipeline.framebufferTexture2D(target, attachment, texTarget, texture, level);
    }
    
    @Override
    public void deleteFramebuffers(final int framebuffer) {
        this.blaze3DGlStatePipeline.deleteFramebuffers(framebuffer);
    }
    
    @Override
    public GFXCapabilities capabilities() {
        this.initializeCapabilities();
        return this.capabilities;
    }
    
    @Override
    public void initializeCapabilities() {
        if (this.capabilities == null) {
            this.capabilities = this.createCapabilities();
        }
        this.capabilities.initialize();
    }
    
    @Override
    public int genBuffers() {
        return this.blaze3DGlStatePipeline.genBuffers();
    }
    
    @Override
    public void deleteBuffers(final int buffer) {
        this.blaze3DGlStatePipeline.deleteBuffers(buffer);
    }
    
    @Override
    public int genVertexArrays() {
        return this.blaze3DGlStatePipeline.genVertexArrays();
    }
    
    @Override
    public void bindVertexArray(final int array) {
        this.blaze3DGlStatePipeline.bindVertexArray(array);
        this.vaoId = array;
    }
    
    @Override
    public void deleteVertexArrays(final int vao) {
        this.blaze3DGlStatePipeline.deleteVertexArrays(vao);
    }
    
    @Override
    public int createProgram() {
        return this.blaze3DGlStatePipeline.createProgram();
    }
    
    @Override
    public void attachShader(final int program, final int shader) {
        this.blaze3DGlStatePipeline.attachShader(program, shader);
    }
    
    @Override
    public void deleteShader(final int shader) {
        this.blaze3DGlStatePipeline.deleteShader(shader);
    }
    
    @Override
    public int createShader(final Shader.Type type) {
        return this.blaze3DGlStatePipeline.createShader(type);
    }
    
    @Override
    public void useProgram(final int programId) {
        this.blaze3DGlStatePipeline.useProgram(programId);
    }
    
    @Override
    public void linkProgram(final int programId) {
        this.blaze3DGlStatePipeline.linkProgram(programId);
    }
    
    @Override
    public void deleteProgram(final int programId) {
        this.blaze3DGlStatePipeline.deleteProgram(programId);
    }
    
    @Override
    public Blaze3DBufferSource blaze3DBufferSource() {
        return this.blaze3DBufferSource;
    }
    
    @Override
    public void color4f(final float red, final float green, final float blue, final float alpha) {
        this.blaze3DGlStatePipeline.color4f(red, green, blue, alpha);
    }
    
    @Override
    public void shadeModel(final ShadeType type) {
        this.blaze3DGlStatePipeline.shadeModel(type);
    }
    
    protected void onTextureBind(final ResourceLocation location) {
        final TextureRepository textureRepository = Laby.labyAPI().renderPipeline().resources().textureRepository();
        if (textureRepository instanceof final DefaultAbstractTextureRepository defaultAbstractTextureRepository) {
            defaultAbstractTextureRepository.onTextureBind(location);
        }
    }
    
    public boolean isOpenGl43() {
        return this.openGl43.get();
    }
    
    public abstract void onError(final Object... p0);
    
    protected void printError(final int errorCode, final Object... args) {
        final StringBuilder content = new StringBuilder();
        for (int length = args.length, i = 0; i < length; i += 2) {
            content.append("{}={}");
            if (i + 1 != length - 1) {
                content.append(", ");
            }
        }
        final StackWalker.StackFrame frame = AbstractGFXBridge.WALKER.walk(stream -> stream.skip(4L).findFirst().orElse(null));
        if (frame == null) {
            AbstractGFXBridge.LOGGER.error("That's strange, no StackFrame could be found. OpenGL Error code: 0x{}", Integer.toHexString(errorCode));
            return;
        }
        final String callerMethod = frame.getMethodName();
        final String errorCodeMessage = switch (errorCode) {
            case 1280 -> "Invalid Enum";
            case 1281 -> "Invalid Value";
            case 1282 -> "Invalid Operation";
            case 1283 -> "Stack Overflow";
            case 1284 -> "Stack Underflow";
            case 1285 -> "Out of Memory";
            default -> "0x" + Integer.toHexString(errorCode);
        };
        AbstractGFXBridge.LOGGER.error("[Error Code: " + errorCodeMessage + "] " + callerMethod + "(" + String.valueOf(content), args);
    }
    
    static {
        LOGGER = Logging.getLogger();
        WALKER = StackWalker.getInstance();
    }
}
