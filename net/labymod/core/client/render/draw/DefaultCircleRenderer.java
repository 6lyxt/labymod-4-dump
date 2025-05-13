// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.draw;

import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.client.gfx.pipeline.Blaze3DGlStatePipeline;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.client.render.vertex.phase.RenderPhases;
import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.Stack;
import javax.inject.Inject;
import javax.inject.Named;
import net.labymod.api.client.render.shader.program.ShaderInstance;
import net.labymod.core.client.render.draw.shader.CircleShaderInstance;
import net.labymod.api.client.render.batch.RectangleRenderContext;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.render.draw.CircleRenderer;
import net.labymod.core.client.render.draw.builder.DefaultCircleBuilder;

@Singleton
@Implements(CircleRenderer.class)
public class DefaultCircleRenderer extends DefaultCircleBuilder<CircleRenderer> implements CircleRenderer
{
    private static final boolean CUSTOM_MODEL_VIEW_MATRIX;
    private static final float CIRCLE_SEGMENT_STEP = 0.0027777778f;
    private static final float DEFAULT_STARTING_ANGLE = 0.5f;
    private final RectangleRenderContext context;
    private final CircleShaderInstance shaderInstance;
    
    @Inject
    public DefaultCircleRenderer(@Named("circle_shader_instance") final ShaderInstance instance, final RectangleRenderContext rectangleRenderContext) {
        this.context = rectangleRenderContext;
        this.shaderInstance = (CircleShaderInstance)instance;
    }
    
    @Override
    public void render(final Stack stack) {
        this.validateBuilder();
        this.renderCircle(stack);
        this.resetBuilder();
    }
    
    private void renderCircle(final Stack stack) {
        if (!this.shaderInstance.complied()) {
            this.shaderInstance.prepare(Laby.labyAPI().renderPipeline().vertexFormatRegistry().getPositionColor());
            return;
        }
        final FloatMatrix4 pose = stack.getProvider().getPosition();
        final Blaze3DGlStatePipeline glStatePipeline = Laby.gfx().blaze3DGlStatePipeline();
        this.applyMatrix(stack, glStatePipeline, pose);
        this.context.begin(stack, RenderPhases.createNoTexturedRectanglePhase(this.shaderInstance.shaderProgram()));
        final FloatVector3 transform = stack.transformVector(this.x, this.y, 0.0f);
        final float translatedX = transform.getX();
        final float translatedY = transform.getY();
        final float radius = this.outerRadius;
        this.shaderInstance.circleCenterUniform().set(this.x + translatedX, this.y + translatedY);
        this.shaderInstance.circleRadiusUniform().set(radius);
        this.shaderInstance.circleInnerRadiusUniform().set(radius - this.innerRadius);
        this.shaderInstance.circleStartAngleUniform().set(0.5f + 0.0027777778f * this.startingAngle);
        this.shaderInstance.circleEndAngleUniform().set(0.0027777778f * this.endingAngle);
        this.context.render(this.x - radius, this.y - radius, this.x + radius, this.y + radius, this.color);
        this.context.uploadToBuffer();
        this.resetMatrix(stack, glStatePipeline);
    }
    
    private void applyMatrix(final Stack stack, final Blaze3DGlStatePipeline glStatePipeline, final FloatMatrix4 pose) {
        if (!DefaultCircleRenderer.CUSTOM_MODEL_VIEW_MATRIX) {
            return;
        }
        stack.push();
        stack.identity();
        final Stack modelViewMatrix = glStatePipeline.getModelViewStack();
        modelViewMatrix.push();
        modelViewMatrix.multiply(pose);
        glStatePipeline.applyModelViewMatrix();
    }
    
    private void resetMatrix(final Stack stack, final Blaze3DGlStatePipeline glStatePipeline) {
        if (!DefaultCircleRenderer.CUSTOM_MODEL_VIEW_MATRIX) {
            return;
        }
        final Stack modelViewMatrix = glStatePipeline.getModelViewStack();
        modelViewMatrix.pop();
        glStatePipeline.applyModelViewMatrix();
        stack.pop();
    }
    
    static {
        CUSTOM_MODEL_VIEW_MATRIX = MinecraftVersions.V1_13.orNewer();
    }
}
