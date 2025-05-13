// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.pipeline;

import javax.inject.Inject;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gfx.pipeline.RenderAttributesStack;
import net.labymod.api.client.gfx.pipeline.renderer.shader.ShaderPipeline;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;

@Singleton
@Implements(RenderEnvironmentContext.class)
public class DefaultRenderEnvironmentContext implements RenderEnvironmentContext
{
    private final ShaderPipeline shaderPipeline;
    private final RenderAttributesStack renderAttributesStack;
    private int currentPackedLight;
    private final ScreenContext environmentScreenContext;
    private boolean screenContext;
    
    @Inject
    public DefaultRenderEnvironmentContext() {
        this.environmentScreenContext = ScreenContext.create();
        this.shaderPipeline = Laby.references().shaderPipeline();
        this.renderAttributesStack = new RenderAttributesStack();
    }
    
    @Override
    public RenderAttributesStack renderAttributesStack() {
        return this.renderAttributesStack;
    }
    
    @Override
    public boolean isScreenContext() {
        return this.screenContext;
    }
    
    @Override
    public void setScreenContext(final boolean screenContext) {
        this.screenContext = screenContext;
    }
    
    @Override
    public ShaderPipeline shaderPipeline() {
        return this.shaderPipeline;
    }
    
    @Override
    public int getPackedLight() {
        return this.currentPackedLight;
    }
    
    @Override
    public void setPackedLight(final int packedLight) {
        this.currentPackedLight = packedLight;
    }
    
    @Override
    public ScreenContext screenContext() {
        return this.environmentScreenContext;
    }
}
