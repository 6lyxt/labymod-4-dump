// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.model;

import net.labymod.api.client.gfx.pipeline.buffer.BufferConsumer;
import net.labymod.api.util.math.vector.FloatVector2;
import net.labymod.api.client.render.model.box.ModelBoxVertex;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.client.render.model.box.ModelBoxQuad;
import net.labymod.api.util.math.vector.FloatMatrix3;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.client.render.model.box.ModelBox;
import net.labymod.api.client.gfx.pipeline.material.MaterialColor;
import org.jetbrains.annotations.Nullable;
import java.util.Iterator;
import net.labymod.api.client.gfx.pipeline.buffer.BufferBuilder;
import net.labymod.api.util.Color;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gfx.pipeline.buffer.BufferState;
import net.labymod.api.client.gfx.pipeline.program.RenderProgram;
import net.labymod.api.client.gfx.pipeline.buffer.renderer.RenderedBuffer;
import net.labymod.api.client.gfx.pipeline.program.RenderPrograms;
import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.util.math.vector.FloatVector4;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.render.model.ModelUploader;

@Singleton
@Implements(ModelUploader.class)
public class DefaultModelUploader implements ModelUploader
{
    private static final FloatVector4 POSITION;
    private final GFXRenderPipeline renderPipeline;
    private ModelVertexWriter modelVertexWriter;
    private Model model;
    @Deprecated
    private boolean shaderBasedAnimation;
    
    public DefaultModelUploader() {
        this.modelVertexWriter = ModelUploader.DEFAULT_WRITER;
        this.renderPipeline = Laby.references().gfxRenderPipeline();
    }
    
    @Override
    public ModelUploader model(final Model model) {
        Objects.requireNonNull(model, "model must not be null");
        this.model = model;
        return this;
    }
    
    @Override
    public ModelUploader modelVertexWriter(final ModelVertexWriter writer) {
        this.modelVertexWriter = writer;
        return this;
    }
    
    @Override
    public ModelUploader shaderBasedAnimation() {
        this.shaderBasedAnimation = true;
        return this;
    }
    
    @Override
    public void directUpload() {
        final Model model = this.model;
        if (model == null) {
            return;
        }
        final RenderedBuffer renderedBuffer = model.getRenderedBuffer();
        if (renderedBuffer != null) {
            renderedBuffer.dispose();
        }
        model.setDrawCommand(this.upload(RenderPrograms.getItemProgram(model.getTextureLocation())));
    }
    
    @Nullable
    @Override
    public BufferState upload(final RenderProgram renderProgram) {
        final Model model = this.model;
        if (model == null) {
            return null;
        }
        final BufferBuilder bufferBuilder = this.renderPipeline.getDefaultBufferBuilder();
        bufferBuilder.begin(renderProgram, () -> "Model");
        final Stack stack = Stack.getDefaultEmptyStack();
        for (final ModelPart modelPart : model.getChildren().values()) {
            this.uploadModelPart(modelPart, stack, bufferBuilder, ColorFormat.ARGB32.packTo(ColorFormat.ABGR32, Color.WHITE.get()), 0L);
        }
        this.invalidate();
        return bufferBuilder.end();
    }
    
    private void invalidate() {
        this.model = null;
        this.shaderBasedAnimation = false;
        this.modelVertexWriter = DefaultModelUploader.DEFAULT_WRITER;
    }
    
    private void prepareMatrix(final Stack stack, final ModelPart modelPart) {
        if (this.shaderBasedAnimation) {
            return;
        }
        modelPart.getModelPartTransform().transform(stack, modelPart.getAnimationTransformation());
    }
    
    private void uploadModelPart(final ModelPart modelPart, final Stack stack, final BufferBuilder bufferBuilder, int packedColor, long rainbowCycle) {
        if (!modelPart.isVisible() || (modelPart.getBoxes().isEmpty() && modelPart.getChildren().isEmpty())) {
            return;
        }
        stack.push();
        this.prepareMatrix(stack, modelPart);
        final MaterialColor materialColor = modelPart.getColor();
        if (materialColor != null) {
            final Color color = materialColor.getColor();
            if (color != null) {
                packedColor = ColorFormat.ARGB32.packTo(ColorFormat.ABGR32, materialColor.getColor().get());
            }
            rainbowCycle = materialColor.getCycle();
        }
        this.compileBoxes(modelPart, stack, bufferBuilder, packedColor, rainbowCycle);
        final ModelPart[] modelParts = modelPart.getChildren().values().toArray(new ModelPart[0]);
        for (int i = modelParts.length - 1; i >= 0; --i) {
            this.uploadModelPart(modelParts[i], stack, bufferBuilder, packedColor, rainbowCycle);
        }
        stack.pop();
    }
    
    private void compileBoxes(final ModelPart modelPart, final Stack stack, final BufferBuilder bufferBuilder, final int packedColor, final long rainbowCycle) {
        final StackProvider provider = stack.getProvider();
        final FloatMatrix4 positionMatrix = provider.getPosition();
        final FloatMatrix3 normalMatrix = provider.getNormal();
        for (final ModelBox box : modelPart.getBoxes()) {
            for (final ModelBoxQuad quad : box.getQuads()) {
                if (quad.isVisible()) {
                    final FloatVector3 normal = quad.getNormal(normalMatrix);
                    final float normalX = normal.getX();
                    final float normalY = normal.getY();
                    final float normalZ = normal.getZ();
                    final ModelBoxVertex[] vertices2;
                    final ModelBoxVertex[] vertices = vertices2 = quad.getVertices();
                    for (final ModelBoxVertex vertex : vertices2) {
                        final FloatVector3 vertexPosition = vertex.getPosition();
                        DefaultModelUploader.POSITION.transform(vertexPosition, positionMatrix, 0.0625f);
                        final FloatVector2 uv = vertex.getUV();
                        this.writeVertex(bufferBuilder, DefaultModelUploader.POSITION.getX(), DefaultModelUploader.POSITION.getY(), DefaultModelUploader.POSITION.getZ(), uv.getX(), uv.getY(), packedColor, normalX, normalY, normalZ, (float)modelPart.getId(), modelPart.isGlowing() || modelPart.isParentGlowing(), (rainbowCycle > 0L) ? rainbowCycle : modelPart.getParentCycleDuration());
                    }
                }
            }
        }
    }
    
    private void writeVertex(final BufferBuilder bufferBuilder, final float x, final float y, final float z, final float u, final float v, final int packedColor, final float normalX, final float normalY, final float normalZ, final float modelId, final boolean glowing, final long rainbowCycle) {
        this.modelVertexWriter.write(bufferBuilder, x, y, z, u, v, packedColor, normalX, normalY, normalZ, modelId, glowing, rainbowCycle);
        bufferBuilder.endVertex();
    }
    
    static {
        POSITION = new FloatVector4();
    }
}
