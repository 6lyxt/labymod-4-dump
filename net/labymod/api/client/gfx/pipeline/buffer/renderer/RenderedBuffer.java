// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.buffer.renderer;

import net.labymod.api.Laby;
import net.labymod.api.Constants;
import net.labymod.api.client.gfx.DrawingMode;
import net.labymod.api.client.gfx.target.RenderTarget;
import net.labymod.api.util.RenderUtil;
import net.labymod.api.client.gfx.pipeline.backend.memory.MemoryHandler;
import java.nio.ByteBuffer;
import java.nio.Buffer;
import net.labymod.api.client.gfx.DataType;
import net.labymod.api.client.gfx.pipeline.buffer.index.IndexType;
import net.labymod.api.client.gfx.vertex.VertexFormat;
import net.labymod.api.client.gfx.pipeline.buffer.index.IndexBuffer;
import net.labymod.api.client.gfx.pipeline.buffer.BufferState;
import java.util.Locale;
import net.labymod.api.client.gfx.buffer.BufferTarget;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.client.gfx.pipeline.program.RenderProgram;
import net.labymod.api.client.gfx.pipeline.buffer.index.ElementBuffer;
import net.labymod.api.client.gfx.buffer.BufferUsage;
import net.labymod.api.client.gfx.buffer.BufferObject;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gfx.buffer.VertexArrayObject;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.Disposable;

public class RenderedBuffer implements Disposable
{
    private static final boolean DEBUG;
    private static final Logging LOGGER;
    private static final int[] QUAD_INDICES;
    private static final boolean DEBUG_INTEL = false;
    private static boolean printIntelWorkaround;
    private static final GFXBridge GFX;
    @Nullable
    private final VertexArrayObject vao;
    private final BufferObject vbo;
    private final BufferUsage usage;
    @Nullable
    private ElementBuffer elementBuffer;
    private final boolean vaoSupported;
    private final boolean useIntelWorkaround;
    private boolean applyIntelWorkaround;
    private RenderProgram renderProgram;
    private int vertices;
    private boolean disposed;
    private boolean skipFramebufferCheck;
    
    public RenderedBuffer(final BufferUsage usage) {
        ThreadSafe.ensureRenderThread();
        this.vao = RenderedBuffer.GFX.createVertexArray();
        this.vbo = RenderedBuffer.GFX.createBuffer(BufferTarget.ARRAY, usage);
        this.useIntelWorkaround = RenderedBuffer.GFX.getRenderer().toLowerCase(Locale.ENGLISH).contains("intel");
        this.usage = usage;
        this.vaoSupported = (this.vao != null);
    }
    
    public void skipFramebufferCheck() {
        this.skipFramebufferCheck = true;
    }
    
    public void upload(final BufferState bufferState) {
        this.bind();
        final IndexBuffer indexBuffer = bufferState.getIndexBuffer();
        this.detectIntelWorkaround(bufferState, indexBuffer);
        if (this.applyIntelWorkaround && !RenderedBuffer.printIntelWorkaround) {
            RenderedBuffer.LOGGER.info("Apply Intel workaround for unsigned int indices.", new Object[0]);
            RenderedBuffer.printIntelWorkaround = true;
        }
        this.renderProgram = bufferState.renderProgram();
        this.vertices = (this.applyIntelWorkaround ? (bufferState.getVertices() / 4 * 6) : bufferState.getVertices());
        this.uploadVertexBufferObject(bufferState);
        if (!this.applyIntelWorkaround) {
            this.elementBuffer = this.uploadElementBufferObject(bufferState);
        }
        final VertexFormat format = this.renderProgram.vertexFormat();
        format.apply();
        this.unbind();
        if (this.elementBuffer != null) {
            this.elementBuffer.unbind();
        }
        this.vbo.unbind();
        RenderedBuffer.GFX.invalidateBuffers();
    }
    
    private void detectIntelWorkaround(final BufferState bufferState, final IndexBuffer indexBuffer) {
        if (!this.useIntelWorkaround) {
            return;
        }
        if (indexBuffer == null) {
            final IndexType selectedIndexType = IndexType.select(bufferState.getIndices());
            this.applyIntelWorkaround = (selectedIndexType == IndexType.INT);
        }
        else {
            this.applyIntelWorkaround = (indexBuffer.type() == DataType.UNSIGNED_INT);
        }
    }
    
    @Nullable
    private ElementBuffer uploadElementBufferObject(final BufferState bufferState) {
        return ElementBuffer.selectBuffer(bufferState, this.usage);
    }
    
    private void uploadVertexBufferObject(final BufferState bufferState) {
        final ByteBuffer buffer = bufferState.getBuffer();
        buffer.position(0);
        if (this.applyIntelWorkaround) {
            final MemoryHandler handler = RenderedBuffer.GFX.backend().memoryHandler();
            final ByteBuffer newVertexBuffer = this.buildVertexBuffer(handler, buffer);
            this.vbo.bind();
            this.vbo.upload(newVertexBuffer);
            handler.free(newVertexBuffer);
        }
        else {
            this.vbo.bind();
            this.vbo.upload(buffer);
        }
    }
    
    public RenderProgram renderProgram() {
        return this.renderProgram;
    }
    
    public int getVertices() {
        return this.vertices;
    }
    
    public void bind() {
        if (this.isDisposed()) {
            throw new IllegalStateException("RenderedBuffer cannot be bound because it has already been disposed of.");
        }
        if (this.vao != null) {
            this.vao.bind();
        }
        else {
            this.vbo.bind();
            if (this.elementBuffer != null) {
                this.elementBuffer.bind();
            }
        }
    }
    
    public void unbind() {
        if (this.vao != null) {
            this.vao.unbind();
        }
        else {
            if (this.elementBuffer != null) {
                this.elementBuffer.unbind();
            }
            this.vbo.unbind();
        }
    }
    
    public void drawWithProgram() {
        this.drawWithProgram(this::draw);
    }
    
    public void drawWithProgram(final Runnable renderer) {
        RenderedBuffer.GFX.bindRenderProgram(this.renderProgram);
        RenderTarget renderTarget = null;
        if (!this.skipFramebufferCheck) {
            renderTarget = RenderUtil.bindMainTarget();
        }
        this.useVertexArray(renderer);
        RenderedBuffer.GFX.unbindRenderProgram();
        if (renderTarget != null) {
            renderTarget.unbind();
        }
    }
    
    public void draw() {
        final DrawingMode mode = this.renderProgram.mode();
        if (this.applyIntelWorkaround) {
            RenderedBuffer.GFX.drawArrays(mode, 0, this.vertices);
        }
        else {
            this.ensureElementBuffer();
            this.elementBuffer.draw(mode);
        }
    }
    
    public void drawInstancedWithProgram(final int primcount) {
        this.drawWithProgram(() -> this.drawInstanced(primcount));
    }
    
    public void drawInstanced(final int primcount) {
        this.ensureElementBuffer();
        final DrawingMode mode = this.renderProgram.mode();
        this.elementBuffer.drawInstanced(mode, primcount);
    }
    
    public void useVertexArray(final Runnable renderer) {
        if (renderer == null) {
            return;
        }
        this.bind();
        final boolean vaoNotSupported = !this.vaoSupported;
        if (vaoNotSupported) {
            final VertexFormat format = this.renderProgram().vertexFormat();
            format.apply();
        }
        renderer.run();
        if (vaoNotSupported) {
            final VertexFormat format = this.renderProgram().vertexFormat();
            format.clear();
        }
        this.unbind();
    }
    
    @Override
    public void dispose() {
        if (this.disposed) {
            return;
        }
        this.disposed = true;
        this.vbo.dispose();
        if (this.elementBuffer != null) {
            this.elementBuffer.dispose();
        }
        if (this.vao != null) {
            this.vao.dispose();
        }
    }
    
    @Override
    public boolean isDisposed() {
        return this.disposed;
    }
    
    private ByteBuffer buildVertexBuffer(final MemoryHandler handler, final ByteBuffer buffer) {
        final VertexFormat format = this.renderProgram.vertexFormat();
        final int stride = format.getStride();
        final int bufferVertexCount = buffer.capacity() / stride;
        final ByteBuffer newVertexBuffer = handler.create(this.vertices * stride);
        for (int vertexIndex = 0; vertexIndex < bufferVertexCount; vertexIndex += 4) {
            for (final int index : RenderedBuffer.QUAD_INDICES) {
                newVertexBuffer.put(buffer.slice(stride * (vertexIndex + index), stride));
            }
        }
        newVertexBuffer.position(0);
        return newVertexBuffer;
    }
    
    private void ensureElementBuffer() {
        if (!RenderedBuffer.DEBUG || this.elementBuffer != null) {
            return;
        }
        throw new IllegalStateException("Cannot draw buffer without indices.");
    }
    
    static {
        DEBUG = Constants.SystemProperties.getBoolean(Constants.SystemProperties.OPENGL);
        LOGGER = Logging.getLogger();
        QUAD_INDICES = new int[] { 0, 1, 2, 2, 3, 0 };
        GFX = Laby.gfx();
    }
}
