// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.schematic.renderer;

import net.labymod.api.client.gfx.pipeline.program.RenderProgram;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.function.Function;
import net.labymod.api.client.gfx.pipeline.program.RenderPrograms;
import net.labymod.core.client.render.schematic.block.Block;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.core.client.render.schematic.block.BlockRenderer;
import java.util.Iterator;
import net.labymod.api.Laby;
import java.util.ArrayList;
import net.labymod.core.client.render.schematic.lightning.legacy.LegacyLightEngine;
import net.labymod.api.client.gfx.pipeline.buffer.renderer.RenderedBuffer;
import net.labymod.api.client.gfx.pipeline.buffer.BufferState;
import java.util.List;
import net.labymod.core.client.render.schematic.block.material.RenderLayer;
import net.labymod.api.client.gfx.pipeline.buffer.BufferBuilder;
import net.labymod.api.util.Disposable;

public class RenderedSchematicLayer implements Disposable
{
    private final BufferBuilder bufferBuilder;
    private final RenderLayer renderLayer;
    private final List<BufferState> bufferStates;
    private final List<RenderedBuffer> buffers;
    private boolean dirty;
    private LegacyLightEngine lightEngine;
    
    public RenderedSchematicLayer(final RenderLayer renderLayer) {
        this.bufferStates = new ArrayList<BufferState>();
        this.buffers = new ArrayList<RenderedBuffer>();
        this.dirty = true;
        this.lightEngine = null;
        this.renderLayer = renderLayer;
        this.bufferBuilder = Laby.references().gfxRenderPipeline().createBufferBuilder(256);
    }
    
    public boolean renderBuffer() {
        if (this.buffers.isEmpty()) {
            return false;
        }
        for (final RenderedBuffer buffer : this.buffers) {
            buffer.drawWithProgram();
        }
        return true;
    }
    
    public void render(final BlockRenderer renderer, final TextureAtlas atlas, final Block block, final int x, final int y, final int z) {
        this.render(RenderPrograms::getSchematic, renderer, atlas, block, x, y, z);
    }
    
    public void render(final Function<ResourceLocation, RenderProgram> programFunction, final BlockRenderer renderer, final TextureAtlas atlas, final Block block, final int x, final int y, final int z) {
        this.beginBuffer(programFunction, atlas);
        renderer.renderCube(atlas, this.bufferBuilder, block, x, y, z);
    }
    
    public void upload() {
        this.uploadChunk();
        if (this.isDirty()) {
            this.dispose();
            for (final BufferState state : this.bufferStates) {
                this.buffers.add(state.uploadStaticDraw());
            }
            this.bufferStates.clear();
            this.dirty = false;
            this.renderBuffer();
        }
    }
    
    private void beginBuffer(final Function<ResourceLocation, RenderProgram> programFunction, final TextureAtlas atlas) {
        if (!this.bufferBuilder.building()) {
            this.bufferBuilder.begin(programFunction.apply(atlas.resource()), () -> "Schematic layer " + String.valueOf(this.renderLayer));
        }
    }
    
    @Override
    public void dispose() {
        for (final RenderedBuffer buffer : this.buffers) {
            buffer.dispose();
        }
        this.buffers.clear();
        this.dirty = true;
    }
    
    public boolean isDirty() {
        return this.dirty;
    }
    
    public void setDirty() {
        this.dirty = true;
    }
    
    public void setLightEngine(final LegacyLightEngine lightEngine) {
        this.lightEngine = lightEngine;
    }
    
    public LegacyLightEngine getLightEngine() {
        return this.lightEngine;
    }
    
    public void close() {
        if (this.bufferBuilder != null) {
            this.bufferBuilder.close();
        }
    }
    
    public void uploadChunk() {
        if (!this.bufferBuilder.building()) {
            return;
        }
        final BufferState bufferState = this.bufferBuilder.end();
        if (bufferState == null) {
            return;
        }
        this.bufferStates.add(bufferState);
    }
}
