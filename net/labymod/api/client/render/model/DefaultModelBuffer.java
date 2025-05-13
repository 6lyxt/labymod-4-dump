// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.model;

import net.labymod.api.client.gfx.pipeline.MojangLight;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.client.gfx.pipeline.Blaze3DGlStatePipeline;
import java.util.Iterator;
import net.labymod.api.client.gfx.pipeline.buffer.BufferConsumer;
import net.labymod.api.client.gfx.pipeline.blaze3d.buffer.Blaze3DBufferSource;
import java.util.Objects;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.gfx.pipeline.buffer.renderer.RenderedBuffer;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.client.render.matrix.ArrayStackProvider;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.shader.uniform.Uniform3F;
import net.labymod.api.client.gfx.shader.uniform.Uniform2I;
import net.labymod.api.client.gfx.shader.uniform.Uniform1I;
import net.labymod.api.client.gfx.shader.uniform.UniformBone;
import net.labymod.api.client.gfx.shader.ShaderProgram;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.render.model.compiler.ImmediateModelCompiler;
import net.labymod.api.client.render.model.compiler.VertexCompiler;

public class DefaultModelBuffer implements ModelBuffer
{
    private static final VertexCompiler LEGACY_COMPILER;
    private final Model model;
    private final ModelRenderer modelRenderer;
    private final ImmediateModelCompiler modelCompiler;
    private final RenderEnvironmentContext renderEnvironmentContext;
    @Nullable
    private Stack legacyStack;
    private boolean forceProjectionMatrix;
    private ShaderProgram lastProgram;
    private UniformBone boneMatricesUniform;
    private Uniform1I environmentContextUniform;
    private Uniform2I lightCoordsUniform;
    private Uniform3F lightDirection0Uniform;
    private Uniform3F lightDirection1Uniform;
    private ImmediateModelCompiler.Visitor visitor;
    private boolean immediate;
    private int argb;
    
    public DefaultModelBuffer(final Model model) {
        this.visitor = ImmediateModelCompiler.Visitor.NOP;
        this.argb = -1;
        this.model = model;
        this.modelRenderer = Laby.references().modelRenderer();
        final boolean legacy = PlatformEnvironment.isAncientOpenGL();
        this.modelCompiler = ImmediateModelCompiler.findCompiler(legacy);
        this.renderEnvironmentContext = Laby.references().renderEnvironmentContext();
        if (legacy) {
            this.modelCompiler.setVertexCompiler(DefaultModelBuffer.LEGACY_COMPILER);
            this.legacyStack = Stack.create(new ArrayStackProvider(64));
        }
    }
    
    @Override
    public void render(final Stack stack) {
        final RenderedBuffer renderedBuffer = this.getOrCreateBuffer();
        if (renderedBuffer != null) {
            if (this.immediate || this.isWorldEnvironmentAndHasActiveShader()) {
                this.renderImmediate(stack);
            }
            else {
                this.renderRetained(stack, renderedBuffer);
            }
        }
        this.setARGB(-1);
    }
    
    private void renderImmediate(final Stack stack) {
        final Blaze3DBufferSource bufferSource = Laby.gfx().blaze3DBufferSource();
        final BufferConsumer buffer = bufferSource.getBuffer(Laby.references().standardBlaze3DRenderTypes().entityTranslucent(this.model.getTextureLocation(), false));
        final Stack currentStack = (this.legacyStack == null) ? stack : this.legacyStack;
        final ColorFormat colorFormat = ColorFormat.ARGB32;
        final float red = colorFormat.normalizedRed(this.argb);
        final float green = colorFormat.normalizedGreen(this.argb);
        final float blue = colorFormat.normalizedBlue(this.argb);
        final float alpha = colorFormat.normalizedAlpha(this.argb);
        for (final ModelPart child : this.model.getChildren().values()) {
            this.modelCompiler.compile(currentStack, buffer, child, red, green, blue, alpha, this.visitor);
        }
        final Blaze3DGlStatePipeline glStatePipeline = Laby.gfx().blaze3DGlStatePipeline();
        if (this.isWorldEnvironmentAndHasActiveShader()) {
            bufferSource.endBuffer();
        }
        else {
            final Blaze3DGlStatePipeline blaze3DGlStatePipeline = glStatePipeline;
            final Blaze3DBufferSource obj = bufferSource;
            Objects.requireNonNull(obj);
            blaze3DGlStatePipeline.setupFlatLighting(obj::endBuffer);
        }
    }
    
    private void renderRetained(final Stack stack, final RenderedBuffer renderedBuffer) {
        final ShaderProgram shaderProgram = renderedBuffer.renderProgram().shaderProgram();
        if (this.lastProgram != shaderProgram) {
            this.lastProgram = shaderProgram;
            this.boneMatricesUniform = shaderProgram.getUniform("BoneMatrices");
            this.environmentContextUniform = shaderProgram.getUniform("EnvironmentContext");
            this.lightCoordsUniform = shaderProgram.getUniform("LightCoords");
            this.lightDirection0Uniform = shaderProgram.getUniform("LightDirection0");
            this.lightDirection1Uniform = shaderProgram.getUniform("LightDirection1");
        }
        this.applyEnvironmentUniforms(stack);
        if (this.boneMatricesUniform != null) {
            this.model.applyAnimation(this.boneMatricesUniform);
        }
        this.modelRenderer.render(stack, this.model, this.isForceProjectionMatrix());
        if (this.boneMatricesUniform != null) {
            this.boneMatricesUniform.resetBoneMatrix();
        }
    }
    
    @Override
    public void rebuildModel() {
        ThreadSafe.ensureRenderThread();
        if (this.model == null) {
            return;
        }
        this.model.deleteBuffer();
        this.getOrCreateBuffer();
    }
    
    @Override
    public void dispose() {
        if (this.model == null) {
            return;
        }
        this.model.deleteBuffer();
    }
    
    @Override
    public boolean isForceProjectionMatrix() {
        return this.forceProjectionMatrix;
    }
    
    @Override
    public void setForceProjectionMatrix(final boolean forceProjectionMatrix) {
        this.forceProjectionMatrix = forceProjectionMatrix;
    }
    
    @NotNull
    @Override
    public ImmediateModelCompiler.Visitor visitor() {
        return this.visitor;
    }
    
    @Override
    public void setVisitor(@Nullable ImmediateModelCompiler.Visitor visitor) {
        if (visitor == null) {
            visitor = ImmediateModelCompiler.Visitor.NOP;
        }
        this.visitor = visitor;
    }
    
    @Override
    public boolean isImmediate() {
        return this.immediate;
    }
    
    @Override
    public void setImmediate(final boolean immediate) {
        this.immediate = immediate;
    }
    
    @Override
    public int getARGB() {
        return this.argb;
    }
    
    @Override
    public void setARGB(final int argb) {
        this.argb = argb;
    }
    
    private boolean isWorldEnvironmentAndHasActiveShader() {
        return !this.renderEnvironmentContext.isScreenContext() && this.renderEnvironmentContext.shaderPipeline().hasActiveShaderPack();
    }
    
    private RenderedBuffer getOrCreateBuffer() {
        RenderedBuffer renderedBuffer = this.model.getRenderedBuffer();
        if (renderedBuffer == null) {
            final ModelUploader uploader = this.modelRenderer.modelUploader();
            uploader.model(this.model);
            if (!this.immediate) {
                uploader.shaderBasedAnimation();
            }
            uploader.directUpload();
            renderedBuffer = this.model.getRenderedBuffer();
        }
        return renderedBuffer;
    }
    
    private void applyEnvironmentUniforms(final Stack stack) {
        if (this.environmentContextUniform != null) {
            this.environmentContextUniform.set(this.renderEnvironmentContext.isScreenContext());
        }
        if (this.lightCoordsUniform != null) {
            if (!this.renderEnvironmentContext.isScreenContext()) {
                final int light = this.renderEnvironmentContext.getPackedLight();
                this.lightCoordsUniform.set(light & 0xFFFF, light >> 16 & 0xFFFF);
            }
            else {
                this.lightCoordsUniform.set(240, 240);
            }
        }
        final MojangLight light2 = Laby.gfx().blaze3DGlStatePipeline().shaderUniformPipeline().light();
        if (this.lightDirection0Uniform != null) {
            this.lightDirection0Uniform.set(light2.light0Direction());
        }
        if (this.lightDirection1Uniform != null) {
            this.lightDirection1Uniform.set(light2.light1Direction());
        }
    }
    
    static {
        LEGACY_COMPILER = ((consumer, x, y, z, red, green, blue, alpha, u, v, packedOverlay, packedLight, normalX, normalY, normalZ) -> {
            consumer.pos(x, y, z);
            consumer.uv(u, v);
            consumer.color(red, green, blue, alpha);
            consumer.normal(normalX, normalY, normalZ);
            consumer.endVertex();
        });
    }
}
