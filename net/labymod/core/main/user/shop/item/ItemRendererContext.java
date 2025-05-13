// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item;

import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;

public final class ItemRendererContext
{
    private final RenderEnvironmentContext renderEnvironmentContext;
    private final AbstractItem item;
    private Stack stack;
    private boolean firstPerson;
    private int packedOverlayCoords;
    private int packedLightCoords;
    private boolean renderInWorld;
    
    ItemRendererContext(final AbstractItem item) {
        this.item = item;
        final GFXRenderPipeline renderPipeline = Laby.labyAPI().gfxRenderPipeline();
        this.renderEnvironmentContext = renderPipeline.renderEnvironmentContext();
    }
    
    public void update(final Stack stack, final boolean firstPerson, final int packedLightCoords, final int packedOverlayCoords, final boolean renderInWorld) {
        this.stack = stack;
        this.firstPerson = firstPerson;
        this.packedLightCoords = packedLightCoords;
        this.packedOverlayCoords = packedOverlayCoords;
        this.renderInWorld = renderInWorld;
    }
    
    public boolean isScreenContext() {
        return this.renderEnvironmentContext.isScreenContext();
    }
    
    public boolean isShadowRenderPass() {
        return this.renderEnvironmentContext.shadowRenderPassContext().isShadowRenderPass();
    }
    
    public boolean isDirect() {
        return this.firstPerson || this.isScreenContext() || this.isShadowRenderPass();
    }
    
    public RenderEnvironmentContext renderEnvironmentContext() {
        return this.renderEnvironmentContext;
    }
    
    public Stack stack() {
        return this.stack;
    }
    
    public FloatMatrix4 projectionMatrix() {
        return this.item.projectionMatrix();
    }
    
    public FloatMatrix4 modelViewMatrix() {
        return this.item.modelViewMatrix();
    }
    
    public boolean isFirstPerson() {
        return this.firstPerson;
    }
    
    public int getPackedOverlayCoords() {
        return this.packedOverlayCoords;
    }
    
    public int getPackedLightCoords() {
        return this.packedLightCoords;
    }
    
    public boolean isRenderInWorld() {
        return this.renderInWorld;
    }
}
