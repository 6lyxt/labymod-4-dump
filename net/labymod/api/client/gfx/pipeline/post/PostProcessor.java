// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.post;

import net.labymod.api.event.client.gui.screen.ScreenResizeEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.event.client.gui.window.WindowResizeEvent;
import java.util.Iterator;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.Laby;
import java.util.ArrayList;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.math.vector.FloatMatrix4;
import org.jetbrains.annotations.Nullable;
import java.util.List;
import net.labymod.api.client.gfx.target.RenderTarget;

public final class PostProcessor
{
    private final String name;
    private final PostProcessorRenderTargetRegistry renderTargetRegistry;
    private final RenderTarget destinationTarget;
    private final List<PostPass> passes;
    @Nullable
    private CustomPostPassProcessor customPostPassProcessor;
    private FloatMatrix4 projectionMatrix;
    
    PostProcessor(final RenderTarget destinationTarget, final ResourceLocation location) {
        this.renderTargetRegistry = new PostProcessorRenderTargetRegistry();
        this.passes = new ArrayList<PostPass>();
        this.name = location.toString();
        this.destinationTarget = destinationTarget;
        this.updateProjectionMatrix();
        Laby.references().eventBus().registerListener(this);
    }
    
    public void process(final float tickDelta) {
        final float time = tickDelta;
        final GFXBridge gfx = Laby.gfx();
        gfx.storeBlaze3DStates();
        for (final PostPass pass : this.passes) {
            pass.process(time);
        }
        gfx.clearDepth();
        gfx.restoreBlaze3DStates();
        this.destinationTarget.bindWrite(false);
    }
    
    public void resize(final int width, final int height) {
        this.updateProjectionMatrix();
        for (final PostPass pass : this.passes) {
            pass.setProjectionMatrix(this.projectionMatrix);
        }
        this.renderTargetRegistry.resizeFullSizedTargets(width, height);
    }
    
    @Nullable
    public CustomPostPassProcessor getCustomPostPassProcessor() {
        return this.customPostPassProcessor;
    }
    
    public void setCustomPostPassProcessor(final CustomPostPassProcessor customPostPassProcessor) {
        this.customPostPassProcessor = customPostPassProcessor;
        for (final PostPass pass : this.passes) {
            pass.setCustomPostPassProcessor(customPostPassProcessor);
        }
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    @Subscribe
    public void onWindowResize(final WindowResizeEvent event) {
        final Window window = Laby.labyAPI().minecraft().minecraftWindow();
        this.resize(window.getRawWidth(), window.getRawHeight());
    }
    
    @Subscribe
    public void onScreenResize(final ScreenResizeEvent event) {
        final Window window = Laby.labyAPI().minecraft().minecraftWindow();
        this.resize(window.getRawWidth(), window.getRawHeight());
    }
    
    private void updateProjectionMatrix() {
        this.projectionMatrix = FloatMatrix4.orthographic(0.0f, (float)this.destinationTarget.getWidth(), (float)this.destinationTarget.getHeight(), 0.0f, 0.1f, 1000.0f);
    }
    
    PostPass addPass(final String passName, final PostPassRenderTarget sourceTarget, final PostPassRenderTarget destinationTarget, final PostPassEffectHolder effectHolder) {
        final PostPass pass = new PostPass(this, passName, sourceTarget, destinationTarget, effectHolder);
        this.passes.add(pass);
        return pass;
    }
    
    @Nullable
    public RenderTarget getRenderTarget(@Nullable final String name) {
        return this.renderTargetRegistry.getRenderTarget(name, this.destinationTarget);
    }
    
    PostProcessorRenderTargetRegistry getRenderTargetRegistry() {
        return this.renderTargetRegistry;
    }
    
    void requestProjectionMatrix() {
        this.updateProjectionMatrix();
        for (final PostPass pass : this.passes) {
            pass.setProjectionMatrix(this.projectionMatrix);
        }
    }
}
