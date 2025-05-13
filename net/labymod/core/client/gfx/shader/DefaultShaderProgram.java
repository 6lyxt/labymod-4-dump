// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.shader;

import javax.inject.Inject;
import net.labymod.api.client.gfx.shader.ShaderProgramManager;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import java.util.function.Supplier;
import java.io.IOException;
import net.labymod.api.util.function.ThrowableBiFunction;
import java.util.Objects;
import net.labymod.api.client.gfx.shader.ShaderConstants;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.Collection;
import net.labymod.api.client.gfx.shader.exception.ShaderException;
import java.util.Locale;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;
import net.labymod.core.main.LabyMod;
import net.labymod.api.Laby;
import java.util.function.Consumer;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gfx.shader.UniformContext;
import net.labymod.api.client.gfx.vertex.VertexFormat;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.core.client.gfx.shader.preprocessor.GlslPreprocessor;
import net.labymod.api.client.gfx.shader.uniform.UniformBuffer;
import java.util.List;
import net.labymod.api.client.gfx.shader.uniform.Uniform;
import net.labymod.api.client.gfx.shader.Shader;
import java.util.Map;
import net.labymod.api.client.gfx.shader.ShaderProgram;

public class DefaultShaderProgram implements ShaderProgram
{
    private final Map<Shader.Type, Shader> shaders;
    private final Map<String, Uniform> uniforms;
    private final List<UniformBuffer> uniformBuffers;
    private final GlslPreprocessor glslPreprocessor;
    private final GFXRenderPipeline renderPipeline;
    private final GFXBridge gfx;
    private int id;
    private boolean linked;
    private VertexFormat vertexFormat;
    private String debugName;
    @Nullable
    private UniformContext.UniformApplier uniformApplier;
    
    public DefaultShaderProgram(final Consumer<UniformContext> uniformConsumer) {
        this.gfx = Laby.gfx();
        this.glslPreprocessor = LabyMod.references().glslPreprocessor();
        this.renderPipeline = Laby.references().gfxRenderPipeline();
        this.shaders = new HashMap<Shader.Type, Shader>();
        this.uniforms = new HashMap<String, Uniform>();
        this.uniformBuffers = new ArrayList<UniformBuffer>();
        if (uniformConsumer != null) {
            uniformConsumer.accept(this);
        }
    }
    
    @Override
    public int getId() {
        return this.id;
    }
    
    @Override
    public void bind() {
        this.gfx.useProgram(this.id);
    }
    
    @Override
    public void unbind() {
        this.gfx.useProgram(0);
    }
    
    @Override
    public void apply() {
        this.bind();
        if (this.uniformApplier != null) {
            this.uniformApplier.apply(this.renderPipeline);
        }
        final int activeTexture = this.gfx.getActiveTexture();
        for (final Uniform uniform : this.getUniforms()) {
            uniform.upload(true);
        }
        this.gfx.setDirectActiveTexture(activeTexture);
    }
    
    @Override
    public void clear() {
        this.unbind();
    }
    
    @Override
    public void link(final VertexFormat vertexFormat) {
        if (this.linked) {
            throw new ShaderException(String.format(Locale.ROOT, "(%s) Shader is already linked!", this));
        }
        this.vertexFormat = vertexFormat;
        this.id = this.gfx.createProgram();
        int index = 0;
        for (final String attributeName : vertexFormat.getAttributeNames()) {
            this.gfx.bindAttributeLocation(this.id, index, attributeName);
            ++index;
        }
        final Collection<Shader> shaders = this.shaders.values();
        for (final Shader shader : shaders) {
            shader.attach(this.id);
        }
        this.gfx.linkProgram(this.id);
        this.gfx.onProgramLinkError(this.id, message -> {
            new ShaderException(String.format(Locale.ROOT, "(%s) Shader program could not be linked.%n%s", this, message));
            return;
        });
        this.linked = true;
        for (final Shader shader : shaders) {
            shader.delete();
        }
        for (final Uniform uniform : this.getUniforms()) {
            uniform.setProgram(this.debugName, this.id);
        }
    }
    
    @Override
    public boolean isLinked() {
        return this.linked;
    }
    
    @Override
    public Shader addShader(final ResourceLocation location, final Shader.Type type, final ShaderConstants shaderConstants) {
        if (!location.exists()) {
            throw new ShaderException(String.format(Locale.ROOT, "The specified location(%s) where the shader source should be does not exist.", location));
        }
        Shader shader = this.shaders.get(type);
        if (shader == null) {
            shader = new Shader(location, type, shaderConstants);
        }
        else {
            shader.checkAlreadyAdded();
        }
        final Shader shader2 = shader;
        final GlslPreprocessor glslPreprocessor = this.glslPreprocessor;
        Objects.requireNonNull(glslPreprocessor);
        shader2.load((ThrowableBiFunction<String, Shader, String, IOException>)glslPreprocessor::process);
        this.shaders.put(type, shader);
        return shader;
    }
    
    @Override
    public <T extends Uniform> T addUniform(final T uniform) {
        this.uniforms.put(uniform.getName(), uniform);
        return uniform;
    }
    
    @Override
    public <T extends UniformBuffer> T addUniformBuffer(final Supplier<T> uniformBufferSupplier) {
        final T uniformBuffer = uniformBufferSupplier.get();
        final int index = this.gfx.getUniformBlockIndex(this.id, uniformBuffer.getName());
        if (index < 0) {
            throw new ShaderException(String.format(Locale.ROOT, "No uniform block with name \"%s\" was found", uniformBuffer.getName()));
        }
        this.gfx.uniformBlockBinding(this.id, index, uniformBuffer.getIndex());
        this.uniformBuffers.add(uniformBuffer);
        return uniformBuffer;
    }
    
    @Override
    public <T extends Uniform> T getUniform(final String name) {
        return (T)this.uniforms.get(name);
    }
    
    @Override
    public Collection<Uniform> getUniforms() {
        return this.uniforms.values();
    }
    
    @Override
    public Collection<UniformBuffer> getUniformBuffers() {
        return this.uniformBuffers;
    }
    
    @Override
    public void setUniformApplier(@Nullable final UniformContext.UniformApplier uniformApplier) {
        this.uniformApplier = uniformApplier;
    }
    
    @Override
    public VertexFormat vertexFormat() {
        return this.vertexFormat;
    }
    
    @Override
    public void setDebugName(final String name) {
        this.debugName = name;
    }
    
    @Override
    public void recompile() {
        if (!this.isLinked()) {
            this.link(this.vertexFormat());
            return;
        }
        final Uniform[] uniforms = this.getUniforms().toArray(new Uniform[0]);
        this.uniforms.clear();
        final Shader[] shaders = this.shaders.values().toArray(new Shader[0]);
        this.dispose();
        for (final Uniform uniform : uniforms) {
            this.addUniform(uniform);
        }
        for (final Shader shader : shaders) {
            final ShaderConstants shaderConstants = shader.shaderConstants();
            shaderConstants.rebuild();
            this.addShader(shader.location(), shader.type(), shaderConstants);
        }
        this.link(this.vertexFormat());
    }
    
    @Override
    public void disposeAndDelete() {
        this.dispose();
        Laby.references().shaderProgramManager().removeShaderProgram(this);
    }
    
    @Override
    public void dispose() {
        this.gfx.deleteProgram(this.id);
        this.shaders.clear();
        this.linked = false;
    }
    
    @Override
    public String toString() {
        return (this.debugName == null) ? super.toString() : this.debugName;
    }
    
    @Singleton
    @Implements(Factory.class)
    public static class DefaultShaderProgramFactory implements Factory
    {
        private final ShaderProgramManager shaderProgramManager;
        
        @Inject
        public DefaultShaderProgramFactory(final ShaderProgramManager shaderProgramManager) {
            this.shaderProgramManager = shaderProgramManager;
        }
        
        @Override
        public ShaderProgram create(final Consumer<UniformContext> uniformConsumer) {
            return this.shaderProgramManager.addShaderProgram(new DefaultShaderProgram(uniformConsumer));
        }
    }
}
