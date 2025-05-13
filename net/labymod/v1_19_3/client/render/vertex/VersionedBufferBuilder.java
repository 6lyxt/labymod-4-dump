// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.client.render.vertex;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import net.labymod.v1_19_3.client.renderer.LabyModRenderType;
import java.util.Locale;
import net.labymod.api.Laby;
import net.labymod.api.client.render.vertex.OldVertexFormat;
import net.labymod.api.client.render.vertex.OldVertexFormatRegistry;
import net.labymod.api.client.render.vertex.phase.RenderPhase;
import java.util.function.Function;
import net.labymod.api.util.function.FunctionMemoizeStorage;
import net.labymod.api.client.render.vertex.BufferBuilder;

public class VersionedBufferBuilder implements BufferBuilder
{
    private static final FunctionMemoizeStorage MEMOIZE_STORAGE;
    private static final Function<RenderPhase, fed> RENDER_TYPE_FUNCTION;
    private final eef tesselator;
    private final OldVertexFormatRegistry vertexFormatRegistry;
    private eeh consumer;
    private edy bufferBuilder;
    @Deprecated
    private fdv.a bufferSource;
    private RenderPhase renderPhase;
    private fed renderType;
    private OldVertexFormat currentFormat;
    private edy.b renderedBuffer;
    
    public VersionedBufferBuilder() {
        this.tesselator = eef.a();
        this.vertexFormatRegistry = Laby.labyAPI().renderPipeline().vertexFormatRegistry();
    }
    
    public VersionedBufferBuilder(final edy builder) {
        this();
        this.bufferBuilder = builder;
    }
    
    @Override
    public BufferBuilder begin(final int mode, final String format) {
        this.reset();
        this.bufferBuilder = this.tesselator.c();
        final OldVertexFormat vertexFormat = this.vertexFormatRegistry.getVertexFormat(format);
        if (vertexFormat == null) {
            throw new IllegalStateException(String.format(Locale.ROOT, "No vertex format with the name %s was found", format));
        }
        ((VersionedVertexFormat)vertexFormat).applyShader();
        this.bufferBuilder.a(LabyModRenderType.getMode(mode), (eei)vertexFormat.getMojangVertexFormat());
        this.currentFormat = vertexFormat;
        return this;
    }
    
    @Override
    public BufferBuilder begin(final RenderPhase phase, final VertexSorter vertexSorter, final boolean shouldCache) {
        this.reset();
        final boolean changed = this.hasCacheChanged(phase, shouldCache);
        if (vertexSorter != null) {
            final Object source = vertexSorter.bufferSource();
            if (source instanceof final fdv multiBufferSource) {
                if (multiBufferSource instanceof final fdv.a bufferSource) {
                    this.bufferSource = bufferSource;
                }
                final VersionedVertexFormat format = (VersionedVertexFormat)phase.getVertexFormat();
                format.applyShader();
                this.currentFormat = format;
                this.bufferBuilder = null;
                if (shouldCache) {
                    if (changed) {
                        this.consumer = multiBufferSource.getBuffer(this.renderType);
                    }
                }
                else {
                    this.consumer = multiBufferSource.getBuffer(this.renderType);
                }
                return this;
            }
        }
        return this.beginBufferBuilder(phase);
    }
    
    @Override
    public BufferBuilder vertex(final float x, final float y, final float z) {
        if (this.consumer != null) {
            this.consumer.a((double)x, (double)y, (double)z);
        }
        if (this.bufferBuilder != null) {
            this.bufferBuilder.a((double)x, (double)y, (double)z);
        }
        return this;
    }
    
    @Override
    public BufferBuilder texture(final float u, final float v) {
        if (this.consumer != null) {
            this.consumer.a(u, v);
        }
        if (this.bufferBuilder != null) {
            this.bufferBuilder.a(u, v);
        }
        return this;
    }
    
    @Override
    public BufferBuilder color(final float red, final float green, final float blue, float alpha) {
        alpha *= Laby.labyAPI().renderPipeline().getAlpha();
        if (this.consumer != null) {
            this.consumer.a(red, green, blue, alpha);
        }
        if (this.bufferBuilder != null) {
            this.bufferBuilder.a(red, green, blue, alpha);
        }
        return this;
    }
    
    @Override
    public BufferBuilder overlay(final int u, final int v) {
        if (this.consumer != null) {
            this.consumer.a(u, v);
        }
        if (this.bufferBuilder != null) {
            this.bufferBuilder.a(u, v);
        }
        return this;
    }
    
    @Override
    public BufferBuilder lightMap(final int u, final int v) {
        if (this.consumer != null) {
            this.consumer.b(u, v);
        }
        if (this.bufferBuilder != null) {
            this.bufferBuilder.b(u, v);
        }
        return this;
    }
    
    @Override
    public BufferBuilder normal(final float x, final float y, final float z) {
        if (this.consumer != null) {
            this.consumer.b(x, y, z);
        }
        if (this.bufferBuilder != null) {
            this.bufferBuilder.b(x, y, z);
        }
        return this;
    }
    
    @Override
    public BufferBuilder next() {
        if (this.consumer != null) {
            this.consumer.e();
        }
        if (this.bufferBuilder != null) {
            this.bufferBuilder.e();
        }
        return this;
    }
    
    @Override
    public void end() {
        if (this.bufferBuilder == null) {
            return;
        }
        this.renderedBuffer = this.bufferBuilder.d();
    }
    
    @Override
    public void uploadToBuffer() {
        if (this.bufferBuilder == null) {
            this.endLastBatch();
            return;
        }
        this.renderType.a(this.bufferBuilder, 0, 0, 0);
    }
    
    @Override
    public OldVertexFormat currentFormat() {
        return this.currentFormat;
    }
    
    @Override
    public void addVertex(final float x, final float y, final float z, final float red, final float green, final float blue, final float alpha, final float u, final float v, final int overlayUV, final int lightMapUV, final float normalX, final float normalY, final float normalZ) {
        this.bufferBuilder.a(x, y, z, red, green, blue, alpha, u, v, overlayUV, lightMapUV, normalX, normalY, normalZ);
    }
    
    @Override
    public boolean building() {
        if (this.bufferBuilder != null) {
            return this.bufferBuilder.j();
        }
        final eeh consumer = this.consumer;
        if (consumer instanceof final edy bufferBuilder) {
            return bufferBuilder.j();
        }
        return false;
    }
    
    @Deprecated
    private void endLastBatch() {
        if (this.bufferSource == null) {
            return;
        }
        this.bufferSource.a();
    }
    
    private boolean hasCacheChanged(final RenderPhase phase, final boolean shouldCache) {
        boolean changed = false;
        if (shouldCache) {
            if (this.renderPhase == null) {
                this.renderPhase = phase;
                this.renderType = VersionedBufferBuilder.RENDER_TYPE_FUNCTION.apply(phase);
                changed = true;
            }
            if (!this.renderPhase.getName().equals(phase.getName())) {
                this.renderPhase = phase;
                this.renderType = VersionedBufferBuilder.RENDER_TYPE_FUNCTION.apply(phase);
                changed = true;
            }
        }
        else {
            this.renderType = VersionedBufferBuilder.RENDER_TYPE_FUNCTION.apply(phase);
        }
        return changed;
    }
    
    private BufferBuilder beginBufferBuilder(@NotNull final RenderPhase phase) {
        this.consumer = null;
        if (this.bufferBuilder != null && this.bufferBuilder.j()) {
            return this;
        }
        this.bufferBuilder = this.tesselator.c();
        final VersionedVertexFormat format = (VersionedVertexFormat)phase.getVertexFormat();
        format.applyShader();
        this.currentFormat = format;
        this.bufferBuilder.a(LabyModRenderType.getMode(phase.getMode()), (eei)format.getMojangVertexFormat());
        return this;
    }
    
    @Nullable
    public edy getBufferBuilder() {
        return this.bufferBuilder;
    }
    
    public edy.b renderedBuffer() {
        return this.renderedBuffer;
    }
    
    private void reset() {
        this.renderedBuffer = null;
    }
    
    static {
        MEMOIZE_STORAGE = Laby.references().functionMemoizeStorage();
        RENDER_TYPE_FUNCTION = VersionedBufferBuilder.MEMOIZE_STORAGE.memoize(LabyModRenderType::new);
    }
}
