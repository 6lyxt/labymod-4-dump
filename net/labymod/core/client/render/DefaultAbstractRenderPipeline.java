// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render;

import net.labymod.api.client.gfx.pipeline.program.RenderParameter;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.client.gfx.pipeline.program.RenderParameters;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.render.font.text.TextRenderer;
import net.labymod.api.event.EventBus;
import net.labymod.api.generated.ReferenceStorage;
import net.labymod.api.Laby;
import net.labymod.api.client.render.font.text.TextRendererProvider;
import net.labymod.api.client.GameTickProvider;
import net.labymod.api.client.resources.Resources;
import net.labymod.api.client.render.RenderConstants;
import net.labymod.api.client.render.batch.RenderContexts;
import net.labymod.api.client.render.shader.ShaderProvider;
import net.labymod.api.client.render.model.ModelService;
import net.labymod.api.client.resources.pack.ResourcePack;
import net.labymod.api.client.resources.pack.ResourcePackRepository;
import net.labymod.api.client.render.vertex.OldVertexFormatRegistry;
import net.labymod.api.client.render.draw.TriangleRenderer;
import net.labymod.api.client.render.draw.ResourceRenderer;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.client.render.font.ComponentRenderer;
import net.labymod.api.client.render.draw.CircleRenderer;
import net.labymod.api.client.render.PlayerHeartRenderer;
import net.labymod.api.client.render.gl.GlStateBridge;
import net.labymod.api.client.render.RenderPipeline;

public abstract class DefaultAbstractRenderPipeline implements RenderPipeline
{
    private final GlStateBridge glStateBridge;
    private final PlayerHeartRenderer heartRenderer;
    private final CircleRenderer circleRenderer;
    private final ComponentRenderer componentRenderer;
    private final RectangleRenderer rectangleRenderer;
    private final ResourceRenderer resourceRenderer;
    private final TriangleRenderer triangleRenderer;
    private final OldVertexFormatRegistry vertexFormatRegistry;
    private final ResourcePackRepository resourcePackRepository;
    private final ResourcePack.Factory resourcePackFactory;
    private final ModelService modelService;
    private final ShaderProvider shaderProvider;
    private final RenderContexts renderContexts;
    private final RenderConstants renderConstants;
    private final Resources resources;
    private final GameTickProvider gameTickProvider;
    private boolean modifiedAlpha;
    private float alpha;
    private final TextRendererProvider textRendererProvider;
    
    public DefaultAbstractRenderPipeline() {
        this.modifiedAlpha = true;
        this.alpha = 1.0f;
        final ReferenceStorage references = Laby.references();
        this.textRendererProvider = references.textRendererProvider();
        this.renderConstants = references.renderConstants();
        this.renderContexts = references.renderContexts();
        this.resources = references.resources();
        this.circleRenderer = references.circleRenderer();
        this.componentRenderer = references.componentRenderer();
        this.rectangleRenderer = references.rectangleRenderer();
        this.resourceRenderer = references.resourceRenderer();
        this.triangleRenderer = references.triangleRenderer();
        this.vertexFormatRegistry = Laby.references().oldVertexFormatRegistry();
        this.resourcePackRepository = references.resourcePackRepository();
        this.resourcePackFactory = references.resourcePackFactory();
        this.modelService = references.modelService();
        this.shaderProvider = references.shaderProvider();
        this.gameTickProvider = references.gameTickProvider();
        final EventBus eventBus = references.eventBus();
        eventBus.registerListener(this.gameTickProvider);
        this.glStateBridge = this.createGlStateBridge();
        this.heartRenderer = this.createHeartRenderer();
    }
    
    @Override
    public void setModifiedAlpha(final boolean value) {
        this.modifiedAlpha = value;
    }
    
    @Override
    public float getAlpha() {
        return this.modifiedAlpha ? this.alpha : 1.0f;
    }
    
    @Override
    public void multiplyAlpha(final float alpha) {
        this.setAlpha(this.alpha * alpha);
    }
    
    @Override
    public void setAlpha(final float alpha) {
        this.alpha = alpha;
        Laby.gfx().blaze3DGlStatePipeline().color4f(1.0f, 1.0f, 1.0f, this.alpha);
    }
    
    @Override
    public void multiplyAlpha(final float alpha, final Runnable context) {
        final float prev = this.getAlpha();
        this.multiplyAlpha(alpha);
        context.run();
        this.setAlpha(prev);
    }
    
    @Override
    public void setAlpha(final float alpha, final Runnable context) {
        final float prev = this.getAlpha();
        this.setAlpha(alpha);
        context.run();
        this.setAlpha(prev);
    }
    
    protected abstract GlStateBridge createGlStateBridge();
    
    protected abstract PlayerHeartRenderer createHeartRenderer();
    
    @Override
    public GlStateBridge glStateBridge() {
        return this.glStateBridge;
    }
    
    @Override
    public TextRenderer textRenderer() {
        return this.textRendererProvider.getRenderer();
    }
    
    @Override
    public PlayerHeartRenderer heartRenderer() {
        return this.heartRenderer;
    }
    
    @Override
    public ComponentRenderer componentRenderer() {
        return this.componentRenderer;
    }
    
    @Override
    public CircleRenderer circleRenderer() {
        return this.circleRenderer;
    }
    
    @Override
    public RectangleRenderer rectangleRenderer() {
        return this.rectangleRenderer;
    }
    
    @Override
    public TriangleRenderer triangleRenderer() {
        return this.triangleRenderer;
    }
    
    @Override
    public ResourceRenderer resourceRenderer() {
        return this.resourceRenderer;
    }
    
    @Override
    public OldVertexFormatRegistry vertexFormatRegistry() {
        return this.vertexFormatRegistry;
    }
    
    @Override
    public ResourcePackRepository resourcePackRepository() {
        return this.resourcePackRepository;
    }
    
    @Override
    public ResourcePack.Factory resourcePackFactory() {
        return this.resourcePackFactory;
    }
    
    @Override
    public ModelService modelService() {
        return this.modelService;
    }
    
    @Override
    public ShaderProvider shaderProvider() {
        return this.shaderProvider;
    }
    
    @Override
    public RenderContexts renderContexts() {
        return this.renderContexts;
    }
    
    @Override
    public RenderConstants renderConstants() {
        return this.renderConstants;
    }
    
    @Override
    public Resources resources() {
        return this.resources;
    }
    
    @Override
    public GameTickProvider gameTickProvider() {
        return this.gameTickProvider;
    }
    
    @Override
    public void renderSeeThrough(final Entity entity, final float seeThroughOpacity, final Runnable renderer) {
        final GFXBridge gfx = Laby.gfx();
        gfx.storeBlaze3DStates();
        final boolean crouching = entity.isCrouching();
        final RenderParameter depthTest = RenderParameters.LEQUAL_DEPTH_TEST;
        if (!crouching) {
            depthTest.apply();
            renderer.run();
            depthTest.clear();
        }
        if (crouching) {
            depthTest.apply();
            this.multiplyAlpha(seeThroughOpacity, renderer);
            depthTest.clear();
        }
        else {
            this.multiplyAlpha(seeThroughOpacity, renderer);
        }
        gfx.restoreBlaze3DStates();
    }
    
    @Override
    public void renderNoneStandardNameTag(final Entity entity, final Runnable renderer) {
        if (entity.isCrouching()) {
            return;
        }
        final GFXBridge gfx = Laby.gfx();
        gfx.storeBlaze3DStates();
        gfx.enableDepth();
        renderer.run();
        gfx.restoreBlaze3DStates();
    }
}
