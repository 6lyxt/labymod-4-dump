// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.model.compiler;

import net.labymod.api.util.math.vector.FloatVector2;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.client.render.model.box.ModelBox;
import net.labymod.api.client.render.matrix.StackProvider;
import java.util.Iterator;
import net.labymod.api.client.gfx.pipeline.material.MaterialColor;
import net.labymod.api.Laby;
import net.labymod.api.util.Color;
import net.labymod.api.util.time.TimeUtil;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.gfx.pipeline.buffer.BufferConsumer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.util.math.vector.FloatVector4;

public abstract class ImmediateModelCompiler
{
    protected static final FloatVector4 POSITION;
    protected static final FloatVector3 NORMAL;
    protected VertexCompiler vertexCompiler;
    
    public ImmediateModelCompiler() {
        this.vertexCompiler = VertexCompiler.DEFAULT;
    }
    
    public static ImmediateModelCompiler findBestCompiler() {
        return findCompiler(PlatformEnvironment.isAncientOpenGL());
    }
    
    public static ImmediateModelCompiler findCompiler(final boolean legacy) {
        return legacy ? new LegacyImmediateModelCompiler() : new ModernImmediateModelCompiler();
    }
    
    public VertexCompiler getVertexCompiler() {
        return this.vertexCompiler;
    }
    
    public void setVertexCompiler(final VertexCompiler vertexCompiler) {
        this.vertexCompiler = vertexCompiler;
    }
    
    public void compile(final Stack stack, final BufferConsumer consumer, final ModelPart part) {
        this.compile(stack, consumer, part, 1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public void compile(final Stack stack, final BufferConsumer consumer, final ModelPart part, @NotNull final Visitor visitor) {
        this.compile(stack, consumer, part, 1.0f, 1.0f, 1.0f, 1.0f, visitor);
    }
    
    public void compile(final Stack stack, final BufferConsumer consumer, final ModelPart part, final float red, final float green, final float blue, final float alpha) {
        this.compile(stack, consumer, part, red, green, blue, alpha, Visitor.NOP);
    }
    
    public void compile(final Stack stack, final BufferConsumer consumer, final ModelPart part, float red, float green, float blue, float alpha, @NotNull final Visitor visitor) {
        visitor.visit(stack, consumer, part);
        if (part.isInvisibleOrEmpty()) {
            return;
        }
        stack.push();
        part.getModelPartTransform().transform(stack, part.getAnimationTransformation());
        final MaterialColor materialColor = part.getColor();
        if (materialColor != null) {
            Color color = materialColor.getColor();
            if (materialColor.isRainbow()) {
                color = Color.ofHSB(TimeUtil.getMillis() % materialColor.getCycle() / (float)materialColor.getCycle(), 0.8f, 0.8f);
            }
            if (color != null) {
                red = color.getRed() / 255.0f;
                green = color.getGreen() / 255.0f;
                blue = color.getBlue() / 255.0f;
                alpha = color.getAlpha() / 255.0f;
            }
        }
        final int packedLight = (part.isGlowing() || part.isParentGlowing()) ? 15728880 : Laby.references().renderEnvironmentContext().getPackedLightWithCondition();
        this.compileBoxes(stack.getProvider(), consumer, part, packedLight, red, green, blue, alpha);
        for (final ModelPart child : part.getChildren().values()) {
            this.compile(stack, consumer, child, red, green, blue, alpha, visitor);
        }
        stack.pop();
    }
    
    protected void compileBoxes(final StackProvider provider, final BufferConsumer consumer, final ModelPart part, final int packedLight, final float red, final float green, final float blue, final float alpha) {
        for (final ModelBox box : part.getBoxes()) {
            this.compileBox(provider, consumer, box, packedLight, red, green, blue, alpha);
        }
    }
    
    protected abstract void compileBox(final StackProvider p0, final BufferConsumer p1, final ModelBox p2, final int p3, final float p4, final float p5, final float p6, final float p7);
    
    public void addVertex(final BufferConsumer consumer, final FloatMatrix4 pose, final float x, final float y, final float z, final float red, final float green, final float blue, final float alpha, final FloatVector2 uv, final int packedLight, final float normalX, final float normalY, final float normalZ) {
        ImmediateModelCompiler.POSITION.set(x, y, z, 1.0f);
        ImmediateModelCompiler.POSITION.transform(pose);
        this.addVertex(consumer, red, green, blue, alpha, uv, packedLight, normalX, normalY, normalZ);
    }
    
    protected void addVertex(final BufferConsumer consumer, final float red, final float green, final float blue, final float alpha, final FloatVector2 uv, final int packedLight, final float normalX, final float normalY, final float normalZ) {
        this.vertexCompiler.compile(consumer, ImmediateModelCompiler.POSITION.getX(), ImmediateModelCompiler.POSITION.getY(), ImmediateModelCompiler.POSITION.getZ(), red, green, blue, alpha, uv.getX(), uv.getY(), 655360, packedLight, normalX, normalY, normalZ);
    }
    
    static {
        POSITION = new FloatVector4();
        NORMAL = new FloatVector3();
    }
    
    @FunctionalInterface
    public interface Visitor
    {
        public static final Visitor NOP = (stack, consumer, part) -> {};
        
        void visit(final Stack p0, final BufferConsumer p1, final ModelPart p2);
    }
}
