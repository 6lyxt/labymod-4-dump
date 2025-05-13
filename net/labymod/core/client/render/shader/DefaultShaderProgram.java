// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.shader;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gfx.shader.UniformContext;
import java.util.Collections;
import java.util.Collection;
import net.labymod.api.client.gfx.shader.uniform.UniformBuffer;
import java.util.function.Supplier;
import java.util.StringJoiner;
import net.labymod.api.util.math.vector.FloatVector2;
import net.labymod.api.client.gfx.pipeline.Fog;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.client.gfx.pipeline.MojangLight;
import net.labymod.api.client.gfx.pipeline.Matrices;
import java.util.Objects;
import java.util.Iterator;
import java.util.Locale;
import net.labymod.api.client.gfx.shader.Shader;
import java.io.IOException;
import net.labymod.api.client.render.shader.ShaderException;
import net.labymod.api.util.io.IOUtil;
import java.nio.charset.StandardCharsets;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.HashMap;
import net.labymod.api.Laby;
import net.labymod.api.client.render.vertex.OldVertexFormat;
import net.labymod.api.client.gfx.shader.uniform.Uniform1F;
import net.labymod.api.client.gfx.shader.uniform.Uniform3F;
import net.labymod.api.client.gfx.shader.uniform.Uniform4F;
import net.labymod.api.client.gfx.shader.uniform.Uniform2F;
import net.labymod.api.client.gfx.shader.uniform.UniformMatrix4;
import net.labymod.api.client.gfx.shader.uniform.Uniform;
import java.util.Map;
import net.labymod.core.client.render.shader.transformer.GlslPreprocessor;
import net.labymod.core.client.render.shader.transformer.DefaultShaderTransformer;
import net.labymod.api.client.gfx.pipeline.Blaze3DShaderUniformPipeline;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.render.shader.ShaderProgram;

public class DefaultShaderProgram implements ShaderProgram
{
    private final LabyAPI labyAPI;
    private final GFXBridge gfx;
    private final Blaze3DShaderUniformPipeline shaderUniformPipeline;
    private final DefaultShaderTransformer shaderTransformer;
    private final GlslPreprocessor glslPreprocessor;
    private final Map<Integer, CharSequence> attributes;
    private final Map<String, Uniform> uniforms;
    private int programId;
    private int vertexShader;
    private int fragmentShader;
    private boolean linked;
    private UniformMatrix4 modelViewMatrixUniform;
    private UniformMatrix4 projectionMatrixUniform;
    private UniformMatrix4 textureMatrixUniform;
    private Uniform2F screenSizeUniform;
    private Uniform4F colorModulatorUniform;
    private Uniform3F light0DirectionUniform;
    private Uniform3F light1DirectionUniform;
    private Uniform1F fogStartUniform;
    private Uniform1F fogEndUniform;
    private Uniform4F fogColorUniform;
    private Uniform1F lineWidthUniform;
    private Uniform1F gameTimeUniform;
    private Uniform1F partialTicksUniform;
    private final OldVertexFormat format;
    
    protected DefaultShaderProgram(final OldVertexFormat format) {
        this.format = format;
        this.labyAPI = Laby.labyAPI();
        this.gfx = Laby.gfx();
        this.shaderUniformPipeline = Laby.references().blaze3DShaderUniformPipeline();
        this.attributes = new HashMap<Integer, CharSequence>();
        this.uniforms = new HashMap<String, Uniform>();
        this.programId = 0;
        this.vertexShader = 0;
        this.fragmentShader = 0;
        this.linked = true;
        this.shaderTransformer = new DefaultShaderTransformer();
        this.glslPreprocessor = new GlslPreprocessor();
    }
    
    @Override
    public ShaderProgram addVertexShader(final ResourceLocation shaderLocation) throws ShaderException {
        try {
            this.addVertexShader(IOUtil.toString(shaderLocation.openStream(), StandardCharsets.UTF_8));
        }
        catch (final IOException exception) {
            throw new ShaderException("Couldn't load shader source.", exception);
        }
        return this;
    }
    
    @Override
    public ShaderProgram addFragmentShader(final ResourceLocation shaderLocation) throws ShaderException {
        try {
            this.addFragmentShader(IOUtil.toString(shaderLocation.openStream(), StandardCharsets.UTF_8));
        }
        catch (final IOException exception) {
            throw new ShaderException("Couldn't load shader source.", exception);
        }
        return this;
    }
    
    @Override
    public ShaderProgram addVertexShader(final String shader) throws ShaderException {
        if (this.vertexShader != 0) {
            throw new ShaderException("Only one vertex shader can be added to a shader program.");
        }
        this.vertexShader = this.addShader(shader, Shader.Type.VERTEX);
        return this;
    }
    
    @Override
    public ShaderProgram addFragmentShader(final String shader) throws ShaderException {
        if (this.fragmentShader != 0) {
            throw new ShaderException("Only one fragment shader can be added to a shader program.");
        }
        this.fragmentShader = this.addShader(shader, Shader.Type.FRAGMENT);
        return this;
    }
    
    private int addShader(String shaderSource, final Shader.Type type) throws ShaderException {
        shaderSource = this.glslPreprocessor.process(shaderSource);
        shaderSource = this.shaderTransformer.transform(shaderSource, this.format);
        final int shaderId = this.gfx.createShader(type);
        this.gfx.shaderSource(shaderId, shaderSource);
        this.gfx.compileShader(shaderId);
        this.gfx.onShaderCompileError(shaderId, message -> {
            new net.labymod.api.client.gfx.shader.exception.ShaderException(String.format(Locale.ROOT, "The %s shader could not be compiled. [%s]%n%s", type, this, message));
            return;
        });
        return shaderId;
    }
    
    @Override
    public void link() throws ShaderException {
        this.programId = this.gfx.createProgram();
        if (this.format != null) {
            this.setupVertexAttributeLocations();
        }
        if (this.vertexShader != 0) {
            this.gfx.attachShader(this.programId, this.vertexShader);
        }
        if (this.fragmentShader != 0) {
            this.gfx.attachShader(this.programId, this.fragmentShader);
        }
        this.gfx.linkProgram(this.programId);
        this.gfx.onProgramLinkError(this.programId, message -> {
            new net.labymod.api.client.gfx.shader.exception.ShaderException(String.format(Locale.ROOT, "(%s) Shader program could not be linked.%n%s", this, message));
            return;
        });
        this.linked = true;
        if (this.vertexShader != 0) {
            this.gfx.deleteShader(this.vertexShader);
        }
        if (this.fragmentShader != 0) {
            this.gfx.deleteShader(this.fragmentShader);
        }
        this.createDefaultUniforms();
    }
    
    @Override
    public void bind() {
        if (this.programId <= 0) {
            throw new IllegalStateException("Shader program has not been successfully linked yet.");
        }
        this.gfx.useProgram(this.programId);
        this.applyDefaultUniforms();
        this.updateProvidedUniforms();
    }
    
    @Override
    public void unbind() {
        this.gfx.useProgram(0);
    }
    
    @Override
    public void updateProvidedUniforms() {
        final int activeTexture = this.gfx.getActiveTexture();
        for (final Uniform uniform : this.uniforms.values()) {
            uniform.upload();
        }
        this.gfx.setActiveTexture(activeTexture - 33984);
    }
    
    @Override
    public void addProvidedUniform(final Uniform uniform) {
        this.addUniform(uniform);
    }
    
    @Override
    public int getProgramID() {
        return this.programId;
    }
    
    @Override
    public int getVertexShaderID() {
        return this.vertexShader;
    }
    
    @Override
    public int getFragmentShaderID() {
        return this.fragmentShader;
    }
    
    @Override
    public boolean isLinked() {
        return this.linked;
    }
    
    @Override
    public void setupVertexAttributeLocations() {
        if (this.format == null) {
            return;
        }
        this.format.setupAttributeLocation(this);
    }
    
    @Override
    public ShaderProgram bindAttributeLocation(final int index, final CharSequence name) {
        final CharSequence attributeName = this.attributes.get(index);
        if (attributeName != null) {
            return this;
        }
        this.gfx.bindAttributeLocation(this.programId, index, name);
        this.attributes.put(index, name);
        return this;
    }
    
    @Override
    public int getAttributeLocation(final CharSequence name) {
        for (final Map.Entry<Integer, CharSequence> entry : this.attributes.entrySet()) {
            if (!Objects.equals(entry.getValue(), name)) {
                continue;
            }
            return entry.getKey();
        }
        return -1;
    }
    
    private void applyDefaultUniforms() {
        final Matrices matrices = this.shaderUniformPipeline.matrices();
        this.modelViewMatrixUniform.set(matrices.modelViewMatrix());
        this.projectionMatrixUniform.set(matrices.projectionMatrix());
        this.textureMatrixUniform.set(matrices.textureMatrix());
        this.colorModulatorUniform.set(this.shaderUniformPipeline.colorModulator());
        final MojangLight light = this.shaderUniformPipeline.light();
        final FloatVector3 light0Direction = light.light0Direction();
        this.light0DirectionUniform.set(light0Direction.getX(), light0Direction.getY(), light0Direction.getZ());
        final FloatVector3 light1Direction = light.light1Direction();
        this.light1DirectionUniform.set(light1Direction.getX(), light1Direction.getY(), light1Direction.getZ());
        final Fog fog = this.shaderUniformPipeline.fog();
        this.fogStartUniform.set(fog.getSuppliedStart());
        this.fogEndUniform.set(fog.getSuppliedEnd());
        this.fogColorUniform.set(fog.color());
        this.partialTicksUniform.set(this.labyAPI.minecraft().getPartialTicks());
        final FloatVector2 screenSize = this.shaderUniformPipeline.screenSize();
        this.screenSizeUniform.set(screenSize.getX(), screenSize.getY());
        this.lineWidthUniform.set(this.shaderUniformPipeline.getLineWidth());
        this.gameTimeUniform.set(this.shaderUniformPipeline.getGameTime());
    }
    
    private void createDefaultUniforms() {
        this.modelViewMatrixUniform = this.addUniform(new UniformMatrix4("u_modelViewMat"));
        this.projectionMatrixUniform = this.addUniform(new UniformMatrix4("u_projMat"));
        this.textureMatrixUniform = this.addUniform(new UniformMatrix4("u_textureMat"));
        this.screenSizeUniform = this.addUniform(new Uniform2F("u_screenSize"));
        this.partialTicksUniform = this.addUniform(new Uniform1F("u_partialTicks"));
        this.colorModulatorUniform = this.addUniform(new Uniform4F("u_colorModulator"));
        this.light0DirectionUniform = this.addUniform(new Uniform3F("u_light0Direction"));
        this.light1DirectionUniform = this.addUniform(new Uniform3F("u_light1Direction"));
        this.fogStartUniform = this.addUniform(new Uniform1F("u_fogStart"));
        this.fogEndUniform = this.addUniform(new Uniform1F("u_fogEnd"));
        this.fogColorUniform = this.addUniform(new Uniform4F("u_fogColor"));
        this.lineWidthUniform = this.addUniform(new Uniform1F("u_lineWidth"));
        this.gameTimeUniform = this.addUniform(new Uniform1F("u_gameTime"));
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final DefaultShaderProgram that = (DefaultShaderProgram)o;
        return this.programId == that.programId;
    }
    
    @Override
    public int hashCode() {
        return this.programId;
    }
    
    @Override
    public String toString() {
        return new StringJoiner((CharSequence)", ", DefaultShaderProgram.class.getSimpleName(), (CharSequence)"]").add("programId=" + this.programId).add("vertexShader=" + this.vertexShader).add("fragmentShader=" + this.fragmentShader).add("linked=" + this.linked).toString();
    }
    
    @Override
    public <T extends Uniform> T addUniform(final T uniform) {
        this.uniforms.put(uniform.getName(), uniform);
        uniform.setProgram(this.toString(), this.programId);
        return uniform;
    }
    
    @Override
    public <T extends UniformBuffer> T addUniformBuffer(final Supplier<T> uniformBuffer) {
        throw new UnsupportedOperationException("Not implemented yet.");
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
        return (Collection<UniformBuffer>)Collections.emptyList();
    }
    
    @Override
    public void setUniformApplier(@Nullable final UniformContext.UniformApplier uniformApplier) {
    }
}
