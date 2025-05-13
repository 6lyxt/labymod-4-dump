// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.shader.transformer.model;

import org.jetbrains.annotations.NotNull;

public class Uniform
{
    @NotNull
    private final String glslName;
    @NotNull
    private final UniformType type;
    @NotNull
    private final String mojangName;
    
    public Uniform(@NotNull final String glslName, @NotNull final UniformType type, @NotNull final String mojangName) {
        this.glslName = glslName;
        this.type = type;
        this.mojangName = mojangName;
    }
    
    @NotNull
    public String getGlslName() {
        return this.glslName;
    }
    
    @NotNull
    public UniformType getType() {
        return this.type;
    }
    
    @NotNull
    public String getMojangName() {
        return this.mojangName;
    }
}
