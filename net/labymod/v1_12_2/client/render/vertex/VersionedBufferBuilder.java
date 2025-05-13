// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.render.vertex;

import java.nio.ByteBuffer;
import net.labymod.v1_12_2.client.renderer.WorldRendererAccessor;
import net.labymod.v1_12_2.client.render.matrix.VersionedStackProvider;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.render.matrix.Stack;
import java.util.Locale;
import net.labymod.api.Laby;
import net.labymod.api.client.render.vertex.OldVertexFormat;
import net.labymod.api.client.render.vertex.phase.RenderPhase;
import net.labymod.api.client.render.vertex.OldVertexFormatRegistry;
import net.labymod.core.client.render.shader.DefaultShaderProgramApplier;
import net.labymod.api.client.render.vertex.BufferBuilder;

public class VersionedBufferBuilder implements BufferBuilder
{
    private final DefaultShaderProgramApplier programApplier;
    private final OldVertexFormatRegistry vertexFormatRegistry;
    private bve tessellator;
    private buk bufferBuilder;
    private RenderPhase renderPhase;
    private OldVertexFormat currentFormat;
    
    public VersionedBufferBuilder() {
        this.programApplier = new DefaultShaderProgramApplier();
        this.vertexFormatRegistry = Laby.labyAPI().renderPipeline().vertexFormatRegistry();
    }
    
    public VersionedBufferBuilder(final buk bufferBuilder) {
        this();
        this.bufferBuilder = bufferBuilder;
    }
    
    @Override
    public BufferBuilder begin(final int mode, final String format) {
        this.tessellator = bve.a();
        final OldVertexFormat vertexFormat = this.vertexFormatRegistry.getVertexFormat(format);
        if (vertexFormat == null) {
            throw new IllegalStateException(String.format(Locale.ROOT, "No vertex format with the name %s was found", format));
        }
        (this.bufferBuilder = this.tessellator.c()).a(mode, (cea)vertexFormat.getMojangVertexFormat());
        this.currentFormat = vertexFormat;
        return this;
    }
    
    @Override
    public BufferBuilder begin(final RenderPhase phase, final VertexSorter vertexSorter, final boolean shouldCache) {
        this.tessellator = bve.a();
        this.bufferBuilder = this.tessellator.c();
        final OldVertexFormat vertexFormat = phase.getVertexFormat();
        this.bufferBuilder.a(phase.getMode(), (cea)vertexFormat.getMojangVertexFormat());
        this.currentFormat = vertexFormat;
        this.renderPhase = phase;
        return this;
    }
    
    @Override
    public BufferBuilder vertex(final float x, final float y, final float z) {
        this.bufferBuilder.b((double)x, (double)y, (double)z);
        return this;
    }
    
    @Override
    public BufferBuilder vertex(@NotNull final Stack stack, final float x, final float y, final float z) {
        if (!(stack.getProvider() instanceof VersionedStackProvider)) {
            return this.vertex(stack.getProvider().getPosition(), x, y, z);
        }
        this.bufferBuilder.b((double)x, (double)y, (double)z);
        return this;
    }
    
    @Override
    public BufferBuilder texture(final float u, final float v) {
        this.bufferBuilder.a((double)u, (double)v);
        return this;
    }
    
    @Override
    public BufferBuilder color(final float red, final float green, final float blue, float alpha) {
        alpha *= Laby.labyAPI().renderPipeline().getAlpha();
        this.bufferBuilder.a(red, green, blue, alpha);
        return this;
    }
    
    @Override
    public BufferBuilder overlay(final int u, final int v) {
        this.bufferBuilder.a((double)u, (double)v);
        return this;
    }
    
    @Override
    public BufferBuilder lightMap(final int lightMapUV) {
        return this.lightMap(lightMapUV >> 16 & 0xFFFF, lightMapUV & 0xFFFF);
    }
    
    @Override
    public BufferBuilder lightMap(final int u, final int v) {
        this.bufferBuilder.a(u, v);
        return this;
    }
    
    @Override
    public BufferBuilder normal(final float x, final float y, final float z) {
        this.bufferBuilder.c(x, y, z);
        return this;
    }
    
    @Override
    public BufferBuilder next() {
        this.bufferBuilder.d();
        return this;
    }
    
    @Override
    public void end() {
        if (this.bufferBuilder == null) {
            return;
        }
        this.bufferBuilder.e();
    }
    
    @Override
    public void uploadToBuffer() {
        this.renderPhase.finish(() -> {
            this.programApplier.apply(null);
            this.tessellator.b();
            this.programApplier.stop(null);
        }, 0, 0, 0);
    }
    
    @Override
    public OldVertexFormat currentFormat() {
        return this.currentFormat;
    }
    
    @Override
    public boolean building() {
        if (this.bufferBuilder == null) {
            return super.building();
        }
        return ((WorldRendererAccessor)this.bufferBuilder).building();
    }
    
    public buk getBufferBuilder() {
        return this.bufferBuilder;
    }
    
    private int getNextElementPosition() {
        return ((WorldRendererAccessor)this.bufferBuilder).getNextElementPosition();
    }
    
    private ByteBuffer getBuffer() {
        return ((WorldRendererAccessor)this.bufferBuilder).getBuffer();
    }
    
    private void nextVertexFormatElement() {
        ((WorldRendererAccessor)this.bufferBuilder).updateVertexFormatIndex();
    }
}
