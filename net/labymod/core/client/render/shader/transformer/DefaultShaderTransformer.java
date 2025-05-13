// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.shader.transformer;

import java.util.Locale;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import java.util.Iterator;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.core.client.render.shader.transformer.model.UniformType;
import java.util.ArrayList;
import java.util.HashMap;
import net.labymod.api.client.render.vertex.OldVertexFormat;
import net.labymod.core.client.render.shader.transformer.model.Attribute;
import net.labymod.core.client.render.shader.transformer.model.Uniform;
import java.util.List;
import java.util.Map;

public class DefaultShaderTransformer
{
    private final Map<String, String> replacements;
    private final List<String> transformedSource;
    private final List<Uniform> uniforms;
    private final List<Attribute> attributes;
    private final String fragColorName;
    private final String frontColorName;
    private OldVertexFormat format;
    
    public DefaultShaderTransformer() {
        this.replacements = new HashMap<String, String>();
        this.transformedSource = new ArrayList<String>();
        this.uniforms = new ArrayList<Uniform>();
        this.attributes = new ArrayList<Attribute>();
        this.addUniform("gl_ModelViewMatrix", UniformType.MATRIX_4, "u_modelViewMat");
        this.addUniform("gl_ProjectionMatrix", UniformType.MATRIX_4, "u_projMat");
        this.addAttribute("gl_Vertex", "vec3", "Position", "vec4(Position, 1.0)");
        this.addAttribute("gl_Color", "vec4", "Color");
        this.addAttribute("gl_Normal", "vec3", "Normal");
        this.addAttribute("gl_MultiTexCoord0.st", "vec2", "UV0");
        this.addAttribute("gl_MultiTexCoord1.st", "ivec2", "UV1");
        this.addAttribute("gl_MultiTexCoord2.st", "ivec2", "UV2");
        this.fragColorName = this.generateUniqueName("transformed", "fragColor");
        this.frontColorName = this.generateUniqueName("transformed", "frontColor");
    }
    
    public String transform(final String originalSource, final OldVertexFormat format) {
        this.format = format;
        if (PlatformEnvironment.isNoShader()) {
            return originalSource;
        }
        String modifiedSource = originalSource;
        modifiedSource = modifiedSource.replace("gl_ModelViewProjectionMatrix", "gl_ProjectionMatrix * gl_ModelViewMatrix");
        modifiedSource = modifiedSource.replace("texture2D", "texture");
        this.transformedSource.add("#version 150");
        final boolean isFragmentShader = modifiedSource.contains("gl_FragColor");
        final boolean isVertexShader = !isFragmentShader;
        if (isFragmentShader) {
            this.transformedSource.add("out vec4 " + this.fragColorName);
            this.replacements.put("gl_FragColor", this.fragColorName);
        }
        if (isFragmentShader && modifiedSource.contains("gl_Color")) {
            this.transformedSource.add("in vec4 " + this.frontColorName);
            this.replacements.put("gl_Color", this.frontColorName);
        }
        if (isVertexShader && modifiedSource.contains("gl_FrontColor")) {
            this.transformedSource.add("out vec4 " + this.frontColorName);
            this.replacements.put("gl_FrontColor", this.frontColorName);
        }
        this.replaceAttributes(modifiedSource);
        this.replaceUniforms(modifiedSource);
        final String[] split = modifiedSource.split("\n");
        for (int length = split.length, i = 0; i < length; ++i) {
            String line = split[i];
            if (!line.startsWith("#version")) {
                if (line.startsWith("attribute") && isVertexShader) {
                    this.transformedSource.add("in" + line.substring("attribute".length()));
                }
                else if (line.startsWith("varying")) {
                    final String prefix = isFragmentShader ? "in" : "out";
                    this.transformedSource.add(prefix + line.substring("varying".length()));
                }
                else {
                    for (final Map.Entry<String, String> entry : this.replacements.entrySet()) {
                        line = line.replace(entry.getKey(), entry.getValue());
                    }
                    this.transformedSource.add(line);
                }
            }
        }
        final StringBuilder builder = new StringBuilder();
        for (final String line2 : this.transformedSource) {
            builder.append(line2).append("\n");
        }
        this.replacements.clear();
        this.transformedSource.clear();
        return builder.toString();
    }
    
    private void addUniform(@NotNull final String glslName, @NotNull final UniformType type, @NotNull final String labyModUniformName) {
        this.uniforms.add(new Uniform(glslName, type, labyModUniformName));
    }
    
    private void addAttribute(@NotNull final String glslName, @NotNull final String type) {
        this.addAttribute(glslName, type, null);
    }
    
    private void addAttribute(@NotNull final String glslName, @NotNull final String type, @Nullable String mojangAttributeName) {
        if (mojangAttributeName == null) {
            mojangAttributeName = glslName;
        }
        this.attributes.add(new Attribute(glslName, type, mojangAttributeName));
    }
    
    private void addAttribute(@NotNull final String glslName, @NotNull final String type, @NotNull final String mojangAttributeName, @NotNull final String replacement) {
        this.attributes.add(new Attribute(glslName, type, mojangAttributeName, replacement));
    }
    
    private void replaceUniforms(final String modifiedSource) {
        for (final Uniform uniform : this.uniforms) {
            if (!modifiedSource.contains(uniform.getGlslName())) {
                continue;
            }
            this.replacements.put(uniform.getGlslName(), uniform.getMojangName());
            this.transformedSource.add(String.format(Locale.ROOT, "uniform %s %s;", uniform.getType().getGlslName(), uniform.getMojangName()));
        }
    }
    
    private void replaceAttributes(final String modifiedSource) {
        final Map<String, Attribute> attributeMap = new HashMap<String, Attribute>();
        for (final Attribute attribute : this.attributes) {
            if (!modifiedSource.contains(attribute.getGlslName())) {
                continue;
            }
            if (this.replacements.containsKey(attribute.getGlslName())) {
                continue;
            }
            this.replacements.put(attribute.getGlslName(), attribute.getReplacement());
            attributeMap.computeIfAbsent(attribute.getMojangName(), a -> attribute);
        }
        if (this.format != null) {
            for (String attributeName : this.format.getAttributeNames()) {
                final Attribute attribute2 = attributeMap.get(attributeName);
                if (attribute2 == null) {
                    System.out.println("No attribute was found: " + attributeName);
                }
                else {
                    this.transformedSource.add(String.format(Locale.ROOT, "in %s %s;", attribute2.getType(), attribute2.getMojangName()));
                }
            }
        }
    }
    
    @NotNull
    private String generateUniqueName(final String prefix, @NotNull final String name) {
        return prefix + "_" + name;
    }
}
