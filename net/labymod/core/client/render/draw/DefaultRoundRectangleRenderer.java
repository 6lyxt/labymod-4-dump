// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.draw;

import net.labymod.core.client.render.draw.shader.rounded.RoundedGeometryShaderInstance;
import net.labymod.api.client.render.vertex.phase.RenderPhases;
import net.labymod.api.Laby;
import java.util.function.Consumer;
import net.labymod.api.client.render.draw.builder.RoundedGeometryBuilder;
import net.labymod.api.client.render.matrix.Stack;
import javax.inject.Inject;
import net.labymod.api.client.render.batch.RenderContexts;
import net.labymod.core.client.render.draw.shader.rounded.RoundedGeometryShaderInstanceApplier;
import net.labymod.core.client.render.draw.shader.rounded.RoundedGeometryShaderInstanceHolder;
import net.labymod.api.client.render.batch.RectangleRenderContext;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;

@Singleton
@Referenceable
public class DefaultRoundRectangleRenderer
{
    private final RectangleRenderContext context;
    private final RoundedGeometryShaderInstanceHolder shaderInstanceHolder;
    private final RoundedGeometryShaderInstanceApplier shaderInstanceApplier;
    
    @Inject
    public DefaultRoundRectangleRenderer(final RenderContexts renderContexts, final RoundedGeometryShaderInstanceHolder shaderInstanceHolder, final RoundedGeometryShaderInstanceApplier shaderInstanceApplier) {
        this.context = renderContexts.rectangleRenderContext();
        this.shaderInstanceHolder = shaderInstanceHolder;
        this.shaderInstanceApplier = shaderInstanceApplier;
    }
    
    public void renderSimpleRoundedRectangle(final Stack stack, final RoundedGeometryBuilder.RoundedData data, final Consumer<RectangleRenderContext> renderer) {
        final RoundedGeometryShaderInstance shaderInstance = this.shaderInstanceHolder.get("rectangle");
        if (!shaderInstance.complied()) {
            shaderInstance.prepare(Laby.labyAPI().renderPipeline().vertexFormatRegistry().getPositionColor());
            return;
        }
        this.shaderInstanceApplier.apply(shaderInstance, data);
        this.context.begin(stack, RenderPhases.createNoTexturedRectanglePhase(shaderInstance.shaderProgram()));
        renderer.accept(this.context);
        this.context.uploadToBuffer();
    }
}
