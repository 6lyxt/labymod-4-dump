// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.program;

import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.vertex.VertexFormatRegistry;
import java.util.Objects;
import net.labymod.api.client.gfx.pipeline.program.parameters.ShaderMaterialRenderParameter;
import net.labymod.api.client.gfx.pipeline.program.parameters.ShaderProgramRenderParameter;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gfx.shader.ShaderProgram;
import org.jetbrains.annotations.ApiStatus;
import java.util.Iterator;
import java.util.ArrayList;
import net.labymod.api.client.gfx.shader.material.ShaderMaterial;
import java.util.List;
import net.labymod.api.client.gfx.vertex.VertexFormat;
import net.labymod.api.client.gfx.DrawingMode;

public class RenderProgram
{
    private final String name;
    private final DrawingMode mode;
    private final VertexFormat vertexFormat;
    protected final List<RenderParameter> parameters;
    private ShaderMaterial shaderMaterial;
    
    public RenderProgram(final String name, final DrawingMode mode, final VertexFormat vertexFormat) {
        this(name, mode, vertexFormat, new ArrayList<RenderParameter>());
    }
    
    public RenderProgram(final String name, final DrawingMode mode, final VertexFormat vertexFormat, final List<RenderParameter> parameters) {
        this.name = name;
        this.mode = mode;
        this.vertexFormat = vertexFormat;
        this.parameters = parameters;
        this.checkShaderParameter();
        this.onPostInit();
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    @ApiStatus.Internal
    public void apply() {
        for (final RenderParameter parameter : this.parameters) {
            parameter.apply();
        }
    }
    
    @ApiStatus.Internal
    public void clear() {
        for (final RenderParameter parameter : this.parameters) {
            parameter.clear();
        }
    }
    
    public String getName() {
        return this.name;
    }
    
    public DrawingMode mode() {
        return this.mode;
    }
    
    public VertexFormat vertexFormat() {
        return this.vertexFormat;
    }
    
    @Deprecated(since = "4.1.5", forRemoval = true)
    public ShaderProgram shaderProgram() {
        return this.shaderMaterial.program();
    }
    
    @NotNull
    public ShaderMaterial shaderMaterial() {
        return this.shaderMaterial;
    }
    
    public List<RenderParameter> getParameters() {
        return this.parameters;
    }
    
    public void onPostInit() {
    }
    
    private void checkShaderParameter() {
        RenderParameter parameter = null;
        this.parameters.replaceAll(parameter -> {
            if (parameter instanceof final ShaderProgramRenderParameter shaderProgramRenderParameter) {
                return ShaderMaterialRenderParameter.unknown(shaderProgramRenderParameter.shaderProgram());
            }
            else {
                return parameter;
            }
        });
        final Iterator<RenderParameter> iterator = this.parameters.iterator();
        while (iterator.hasNext()) {
            parameter = iterator.next();
            if (parameter instanceof final ShaderMaterialRenderParameter shaderMaterialRenderParameter) {
                shaderMaterialRenderParameter.link(this.vertexFormat);
                this.shaderMaterial = shaderMaterialRenderParameter.shaderMaterial();
                return;
            }
        }
        final ShaderProgram shaderProgram = (this.vertexFormat == null) ? null : this.vertexFormat.getShader();
        if (shaderProgram != null) {
            final ShaderMaterialRenderParameter materialRenderParameter = ShaderMaterialRenderParameter.unknown(shaderProgram);
            this.parameters.add(materialRenderParameter);
            this.shaderMaterial = materialRenderParameter.shaderMaterial();
        }
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final RenderProgram that = (RenderProgram)o;
        return Objects.equals(this.name, that.name) && Objects.equals(this.mode, that.mode) && Objects.equals(this.vertexFormat, that.vertexFormat) && Objects.equals(this.parameters, that.parameters) && Objects.equals(this.shaderMaterial, that.shaderMaterial);
    }
    
    @Override
    public int hashCode() {
        int result = (this.name != null) ? this.name.hashCode() : 0;
        result = 31 * result + ((this.mode != null) ? this.mode.hashCode() : 0);
        result = 31 * result + ((this.vertexFormat != null) ? this.vertexFormat.hashCode() : 0);
        result = 31 * result + ((this.parameters != null) ? this.parameters.hashCode() : 0);
        result = 31 * result + ((this.shaderMaterial != null) ? this.shaderMaterial.hashCode() : 0);
        return result;
    }
    
    public static class Builder
    {
        private final VertexFormatRegistry vertexFormatRegistry;
        private String name;
        private DrawingMode drawingMode;
        private VertexFormat vertexFormat;
        private final List<RenderParameter> parameters;
        
        public Builder() {
            this.drawingMode = DrawingMode.TRIANGLES;
            this.vertexFormatRegistry = Laby.references().vertexFormatRegistry();
            this.parameters = new ArrayList<RenderParameter>();
        }
        
        public Builder name(final String name) {
            this.name = name;
            return this;
        }
        
        public Builder drawingMode(final DrawingMode mode) {
            this.drawingMode = mode;
            return this;
        }
        
        public Builder vertexFormat(final String name) {
            this.vertexFormat = this.vertexFormatRegistry.getVertexFormat(name);
            return this;
        }
        
        public Builder vertexFormat(final ResourceLocation location) {
            this.vertexFormat = this.vertexFormatRegistry.getVertexFormat(location);
            return this;
        }
        
        public Builder addParameter(final RenderParameter parameter) {
            this.parameters.add(parameter);
            return this;
        }
        
        public Builder addModernParameter(final RenderParameter parameter) {
            return this.addParameter(!PlatformEnvironment.isAncientOpenGL(), parameter);
        }
        
        public Builder addParameter(final boolean condition, final RenderParameter parameter) {
            if (condition) {
                this.parameters.add(parameter);
                return this;
            }
            return this;
        }
        
        public RenderProgram build() {
            return new RenderProgram(this.name, this.drawingMode, this.vertexFormat, this.parameters);
        }
    }
}
