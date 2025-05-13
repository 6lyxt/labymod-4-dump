// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.schematic;

import net.labymod.core.client.render.schematic.block.Block;
import net.labymod.core.client.render.schematic.lightning.legacy.LegacyLightEngine;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import java.util.Iterator;
import net.labymod.core.util.camera.spline.position.Location;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.Textures;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.core.client.render.schematic.lightning.LightSourceRegistry;
import net.labymod.core.client.render.schematic.block.material.RenderLayer;
import net.labymod.api.Laby;
import java.util.ArrayList;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import java.util.List;
import net.labymod.core.client.render.schematic.renderer.RenderedSchematicLayer;
import net.labymod.core.util.ArrayIndex;
import net.labymod.core.util.camera.CinematicCamera;
import net.labymod.api.client.gfx.pipeline.texture.atlas.AtlasRegistry;
import net.labymod.core.client.render.schematic.particle.ParticleRenderer;
import net.labymod.core.client.render.schematic.block.BlockRenderer;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.LabyAPI;
import net.labymod.api.util.Disposable;

public class SchematicRenderer implements Disposable
{
    private final LabyAPI labyAPI;
    private final Schematic schematic;
    private final GFXRenderPipeline pipeline;
    private final BlockRenderer blockRenderer;
    private final ParticleRenderer particleRenderer;
    private final AtlasRegistry atlasRegistry;
    private final CinematicCamera camera;
    private final ArrayIndex<RenderedSchematicLayer> layers;
    private final List<RenderHook> renderHooks;
    private final ConfigProperty<Boolean> animatedTextures;
    private int liquidUpdateTicks;
    
    public SchematicRenderer(final Schematic schematic) {
        this.renderHooks = new ArrayList<RenderHook>();
        this.liquidUpdateTicks = 0;
        this.schematic = schematic;
        this.labyAPI = Laby.labyAPI();
        this.pipeline = this.labyAPI.gfxRenderPipeline();
        this.animatedTextures = this.labyAPI.config().appearance().dynamicBackground().animatedTextures();
        this.camera = new ShaderCamera(50.0f);
        this.atlasRegistry = Laby.references().atlasRegistry();
        this.blockRenderer = new BlockRenderer(schematic);
        this.particleRenderer = new ParticleRenderer(schematic, this.camera);
        (this.layers = new ArrayIndex<RenderedSchematicLayer>(RenderLayer.values().length, RenderedSchematicLayer[]::new)).fill(index -> new RenderedSchematicLayer(RenderLayer.values()[index]));
    }
    
    public void onTick() {
        this.particleRenderer.onTick();
        LightSourceRegistry.getInstance().tick();
        if (this.liquidUpdateTicks > 6 && this.animatedTextures.get()) {
            this.liquidUpdateTicks = 0;
            final RenderedSchematicLayer layer = this.layers.get(RenderLayer.LIQUID.ordinal());
            if (layer != null) {
                layer.setDirty();
            }
        }
        ++this.liquidUpdateTicks;
    }
    
    public void onRenderTick(final Stack stack, final float left, final float top, final float right, final float bottom, final float partialTicks) {
        this.camera.setup(left, top, right, bottom, partialTicks);
    }
    
    public void render(final Stack stack, final float left, final float top, final float right, final float bottom, final float partialTicks) {
        stack.push();
        stack.identity();
        this.camera.setup(left, top, right, bottom, partialTicks);
        final float tickDelta = Laby.labyAPI().minecraft().getTickDelta();
        this.camera.update(left, top, right, bottom, tickDelta);
        final GFXBridge gfx = this.pipeline.gfx();
        gfx.enableDepth();
        gfx.disableCull();
        this.renderLayer(this.atlasRegistry.getAtlas(Textures.Splash.BLOCKS), RenderLayer.CUBE);
        this.renderLayer(this.atlasRegistry.getAtlas(Textures.Splash.LAVA_FLOW), RenderLayer.LIQUID);
        this.renderLayer(this.atlasRegistry.getAtlas(Textures.Splash.BLOCKS), RenderLayer.CUT_OUT);
        final Location position = this.camera.location();
        final LightSourceRegistry registry = LightSourceRegistry.getInstance();
        registry.sort(new FloatVector3((float)position.getX(), (float)position.getY(), (float)position.getZ()));
        this.layers.forEach(RenderedSchematicLayer::upload);
        this.particleRenderer.render(stack, this.atlasRegistry.getAtlas(Textures.Splash.PARTICLES), partialTicks);
        for (final RenderHook hook : this.renderHooks) {
            hook.render(stack, left, top, right, bottom, partialTicks);
        }
        LightSourceRegistry.getInstance().render(stack, partialTicks);
        stack.pop();
        this.schematic.legacyLightEngine().handleLightUpdates();
    }
    
    private void renderLayer(final TextureAtlas atlas, final RenderLayer type) {
        final RenderedSchematicLayer layer = this.layers.get(type.ordinal());
        if (layer == null) {
            return;
        }
        final LegacyLightEngine lightEngine = this.schematic.legacyLightEngine();
        if (lightEngine == layer.getLightEngine() && !lightEngine.isDirty() && !layer.isDirty() && layer.renderBuffer()) {
            return;
        }
        final int chunksInWidth = this.schematic.getChunksInWidth();
        final int chunksInHeight = this.schematic.getChunksInHeight();
        final int chunksInLength = this.schematic.getChunksInLength();
        for (int chunkX = 0; chunkX < chunksInWidth; ++chunkX) {
            for (int chunkY = 0; chunkY < chunksInHeight; ++chunkY) {
                for (int chunkZ = 0; chunkZ < chunksInLength; ++chunkZ) {
                    this.renderChunk(layer, atlas, type, chunkX, chunkY, chunkZ);
                }
            }
        }
        layer.setLightEngine(lightEngine);
    }
    
    private void renderChunk(final RenderedSchematicLayer layer, final TextureAtlas atlas, final RenderLayer type, final int chunkX, final int chunkY, final int chunkZ) {
        final int minX = chunkX * 16;
        final int minY = chunkY * 16;
        final int minZ = chunkZ * 16;
        final int maxX = Math.min(minX + 16, this.schematic.getWidth());
        final int maxY = Math.min(minY + 16, this.schematic.getHeight());
        final int maxZ = Math.min(minZ + 16, this.schematic.getLength());
        for (int x = minX; x < maxX; ++x) {
            for (int y = minY; y < maxY; ++y) {
                for (int z = minZ; z < maxZ; ++z) {
                    final Block block = this.schematic.getBlockAt(x, y, z);
                    final RenderLayer renderLayer = block.material().getRenderLayer();
                    if (renderLayer == type) {
                        layer.render(this.blockRenderer, atlas, block, x, y, z);
                    }
                }
            }
        }
        layer.uploadChunk();
    }
    
    public void registerRenderHook(final RenderHook hook) {
        this.renderHooks.add(hook);
    }
    
    public CinematicCamera camera() {
        return this.camera;
    }
    
    public ParticleRenderer particleRenderer() {
        return this.particleRenderer;
    }
    
    public Schematic schematic() {
        return this.schematic;
    }
    
    public void setDirty() {
        this.layers.forEach(RenderedSchematicLayer::setDirty);
    }
    
    @Override
    public void dispose() {
        this.layers.forEach(RenderedSchematicLayer::dispose);
        LightSourceRegistry.getInstance().reset();
    }
    
    public void close() {
        if (this.particleRenderer != null) {
            this.particleRenderer.close();
        }
        if (this.layers != null) {
            this.layers.forEach(RenderedSchematicLayer::close);
        }
    }
    
    public interface RenderHook
    {
        void render(final Stack p0, final float p1, final float p2, final float p3, final float p4, final float p5);
    }
}
