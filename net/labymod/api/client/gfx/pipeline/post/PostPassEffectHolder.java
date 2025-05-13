// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.post;

import java.util.Iterator;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.shader.Shader;
import net.labymod.api.client.gfx.shader.ShaderConstants;
import net.labymod.api.client.gfx.shader.uniform.Uniform1F;
import net.labymod.api.client.gfx.shader.uniform.Uniform2F;
import net.labymod.api.client.gfx.shader.uniform.UniformMatrix4;
import net.labymod.api.client.gfx.shader.uniform.UniformSampler;
import java.util.Collection;
import net.labymod.api.util.debug.Preconditions;
import java.util.ArrayList;
import net.labymod.api.client.gfx.shader.UniformContext;
import java.util.function.Consumer;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.List;
import net.labymod.api.client.gfx.pipeline.program.RenderPrograms;
import net.labymod.api.client.gfx.shader.ShaderProgram;
import net.labymod.api.client.gfx.pipeline.program.RenderProgram;

record PostPassEffectHolder(RenderProgram renderProgram, ShaderProgram shaderProgram) {
    public PostPassEffectHolder(final ShaderProgram shaderProgram) {
        this(RenderPrograms.getDefaultPostProcessing(), shaderProgram);
    }
    
    public static Builder builder(final String name) {
        return new Builder(name);
    }
    
    public static class Builder
    {
        private static final ShaderProgram.Factory FACTORY;
        private final String name;
        private final List<String> keywords;
        private ResourceLocation vertexShader;
        private ResourceLocation fragmentShader;
        private Consumer<UniformContext> uniformContextConsumer;
        
        public Builder(final String name) {
            this.keywords = new ArrayList<String>();
            this.uniformContextConsumer = (uniformContext -> {});
            Preconditions.notNull(name, "name");
            this.name = name;
        }
        
        public Builder withVertexShader(final ResourceLocation vertexShaderLocation) {
            Preconditions.notNull(vertexShaderLocation, "vertexShaderLocation");
            this.vertexShader = vertexShaderLocation;
            return this;
        }
        
        public Builder withFragmentShader(final ResourceLocation fragmentShaderLocation) {
            Preconditions.notNull(fragmentShaderLocation, "fragmentShaderLocation");
            this.fragmentShader = fragmentShaderLocation;
            return this;
        }
        
        public Builder withUniformContext(final Consumer<UniformContext> uniformContextConsumer) {
            this.uniformContextConsumer = uniformContextConsumer;
            return this;
        }
        
        public Builder withKeyword(final String keyword) {
            this.keywords.add(keyword);
            return this;
        }
        
        public Builder withKeywords(final List<String> keywords) {
            this.keywords.addAll(keywords);
            return this;
        }
        
        public PostPassEffectHolder build() {
            Preconditions.notNull(this.vertexShader, "vertexShader");
            Preconditions.notNull(this.fragmentShader, "fragmentShader");
            final ShaderProgram program = Builder.FACTORY.create(context -> {
                context.addUniform(new UniformSampler("DiffuseSampler", 0));
                context.addUniform(new UniformMatrix4("ProjectionMatrix"));
                context.addUniform(new Uniform2F("SourceSize"));
                context.addUniform(new Uniform2F("DestinationSize"));
                context.addUniform(new Uniform2F("ScreenSize"));
                context.addUniform(new Uniform1F("Time"));
                if (this.uniformContextConsumer != null) {
                    this.uniformContextConsumer.accept(context);
                }
                return;
            });
            final ShaderConstants.Builder constantsBuilder = ShaderConstants.builder();
            for (final String keyword : this.keywords) {
                constantsBuilder.addConstant(keyword);
            }
            final ShaderConstants shaderConstants = constantsBuilder.build();
            program.addShader(this.vertexShader, Shader.Type.VERTEX, shaderConstants);
            program.addShader(this.fragmentShader, Shader.Type.FRAGMENT, shaderConstants);
            program.setDebugName(this.name);
            program.link(Laby.references().vertexFormatRegistry().getVertexFormat("labymod:position_texture_color"));
            return new PostPassEffectHolder(program);
        }
        
        static {
            FACTORY = Laby.references().shaderProgramFactory();
        }
    }
}
