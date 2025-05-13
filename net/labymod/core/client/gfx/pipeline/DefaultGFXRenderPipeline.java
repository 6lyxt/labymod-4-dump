// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.pipeline;

import net.labymod.api.client.gui.screen.widget.OverlappingTranslator;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.gfx.target.FramebufferTarget;
import net.labymod.api.client.gfx.pipeline.renderer.immediate.ImmediateDrawPhase;
import java.util.function.Consumer;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.core.client.gfx.pipeline.buffer.DefaultBufferBuilder;
import net.labymod.api.event.EventBus;
import net.labymod.core.client.gfx.pipeline.listener.GFXBackendServiceLoadListener;
import net.labymod.core.client.gfx.pipeline.listener.RenderTargetListener;
import net.labymod.api.client.gfx.pipeline.context.FrameContext;
import net.labymod.api.client.gfx.target.RenderTargetAttachment;
import net.labymod.api.client.gfx.target.attachment.Depth24Stencil8RenderTargetAttachment;
import net.labymod.api.thirdparty.optifine.OptiFine;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.GFXBridgeAccessor;
import java.util.HashMap;
import net.labymod.api.client.gfx.pipeline.buffer.BufferBuilder;
import net.labymod.api.client.gfx.pipeline.renderer.batch.RenderBuffers;
import net.labymod.api.client.gfx.pipeline.MatrixStorage;
import net.labymod.api.client.gfx.pipeline.util.Scissor;
import net.labymod.api.client.gfx.pipeline.context.FrameContextRegistry;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.gfx.target.RenderTarget;
import net.labymod.api.client.gfx.texture.LightmapTexture;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.client.gfx.pipeline.backend.GFXBackend;
import java.util.Map;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;

@Singleton
@Implements(GFXRenderPipeline.class)
public class DefaultGFXRenderPipeline implements GFXRenderPipeline
{
    private final Map<String, GFXBackend> backends;
    private final GFXBridge gfxBridge;
    private final LightmapTexture lightmapTexture;
    private final RenderTarget activityRenderTarget;
    private final RenderEnvironmentContext renderEnvironmentContext;
    private final FrameContextRegistry frameContextRegistry;
    private final Scissor scissor;
    private final MatrixStorage matrixStorage;
    private RenderBuffers renderBuffers;
    private BufferBuilder defaultBufferBuilder;
    private GFXBackend gfxBackend;
    
    public DefaultGFXRenderPipeline() {
        this.backends = new HashMap<String, GFXBackend>();
        this.gfxBridge = GFXBridgeAccessor.get();
        this.lightmapTexture = Laby.references().lightmapTexture();
        this.renderEnvironmentContext = Laby.references().renderEnvironmentContext();
        this.frameContextRegistry = Laby.references().frameContextRegistry();
        this.activityRenderTarget = new RenderTarget();
        final float alpha = OptiFine.isPresent() ? 0.003921569f : 0.0f;
        this.activityRenderTarget.setClearColor(0.0f, 0.0f, 0.0f, alpha);
        this.activityRenderTarget.addColorAttachment(0);
        this.activityRenderTarget.addAttachment(new Depth24Stencil8RenderTargetAttachment());
        this.frameContextRegistry.register(this.gfxBridge.blaze3DGlStatePipeline().shaderUniformPipeline());
        this.scissor = new Scissor(this);
        this.matrixStorage = new MatrixStorage(this);
        final EventBus eventBus = Laby.references().eventBus();
        eventBus.registerListener(new RenderTargetListener(this));
        eventBus.registerListener(new GFXBackendServiceLoadListener(this));
    }
    
    @Override
    public FrameContextRegistry frameContextRegistry() {
        return this.frameContextRegistry;
    }
    
    @Override
    public GFXBridge gfx() {
        return this.gfxBridge;
    }
    
    @Override
    public Scissor scissor() {
        return this.scissor;
    }
    
    @Override
    public MatrixStorage matrixStorage() {
        return this.matrixStorage;
    }
    
    @Override
    public BufferBuilder getDefaultBufferBuilder() {
        if (this.defaultBufferBuilder == null) {
            this.defaultBufferBuilder = this.createBufferBuilder(256);
        }
        return this.defaultBufferBuilder;
    }
    
    @Override
    public BufferBuilder createBufferBuilder(final int capacity) {
        return new DefaultBufferBuilder(capacity);
    }
    
    @Override
    public void setProjectionMatrix(final FloatMatrix4 projectionMatrix) {
        this.matrixStorage.setProjectionMatrix(projectionMatrix);
    }
    
    @Override
    public FloatMatrix4 getProjectionMatrix() {
        return this.matrixStorage.getProjectionMatrix();
    }
    
    @Override
    public void setModelViewMatrix(final FloatMatrix4 modelViewMatrix, final boolean multiplyWithGameModelViewMatrix) {
        int target = 4;
        if (multiplyWithGameModelViewMatrix) {
            target |= 0x1;
        }
        this.matrixStorage.setModelViewMatrix(modelViewMatrix, target);
    }
    
    @Override
    public FloatMatrix4 getModelViewMatrix() {
        return this.matrixStorage.getModelViewMatrix();
    }
    
    @Override
    public FloatMatrix4 getViewMatrix() {
        return this.matrixStorage.getViewMatrix();
    }
    
    @Override
    public void setViewMatrix(final FloatMatrix4 viewMatrix) {
        this.matrixStorage.setViewMatrix(viewMatrix);
    }
    
    @Override
    public LightmapTexture getLightmapTexture() {
        return this.lightmapTexture;
    }
    
    @Override
    public RenderEnvironmentContext renderEnvironmentContext() {
        return this.renderEnvironmentContext;
    }
    
    @Override
    public void renderToTarget(final RenderTarget target, final Consumer<RenderTarget> renderer) {
        this.applyToTarget(target, renderer);
        this.renderTarget(target);
    }
    
    @Override
    public void applyToTarget(final RenderTarget target, final Consumer<RenderTarget> renderer) {
        int bindingFramebuffer = this.gfxBridge.getBindingFramebuffer();
        if (ImmediateDrawPhase.RENDER_TARGET_RENDER_PASS && bindingFramebuffer == 0) {
            bindingFramebuffer = Laby.labyAPI().minecraft().mainTarget().getId();
        }
        this.gfxBridge.bindFramebuffer(FramebufferTarget.BOTH, 0);
        target.bind(true);
        renderer.accept(target);
        target.unbind();
        this.gfxBridge.bindFramebuffer(FramebufferTarget.BOTH, bindingFramebuffer);
    }
    
    public void renderTarget(final RenderTarget target) {
        final Window window = Laby.labyAPI().minecraft().minecraftWindow();
        target.render(window.getRawWidth(), window.getRawHeight(), false);
    }
    
    @Override
    public RenderTarget getActivityRenderTarget() {
        return this.activityRenderTarget;
    }
    
    @Override
    public void clear(final RenderTarget renderTarget) {
        final int bindingFramebuffer = this.gfxBridge.getBindingFramebuffer();
        this.gfxBridge.bindFramebuffer(FramebufferTarget.BOTH, 0);
        renderTarget.clear();
        this.gfxBridge.bindFramebuffer(FramebufferTarget.BOTH, bindingFramebuffer);
    }
    
    @Override
    public OverlappingTranslator overlappingTranslator() {
        return Laby.references().overlappingTranslator();
    }
    
    @Override
    public RenderBuffers renderBuffers() {
        if (this.renderBuffers == null) {
            this.renderBuffers = new RenderBuffers();
        }
        return this.renderBuffers;
    }
    
    public void registerBackend(final GFXBackend backend) {
        this.backends.putIfAbsent(backend.getName(), backend);
    }
    
    public void findBestBackend() {
        this.gfxBackend = this.backends.get("lwjgl3");
        this.gfxBridge.setBackend(this.gfxBackend);
    }
}
