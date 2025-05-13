// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.shader.transformer.model;

import org.jetbrains.annotations.NotNull;

public class Attribute
{
    @NotNull
    private final String glslName;
    @NotNull
    private final String type;
    @NotNull
    private final String mojangName;
    @NotNull
    private final String replacement;
    
    public Attribute(@NotNull final String glslName, @NotNull final String type, @NotNull final String mojangName) {
        this(glslName, type, mojangName, mojangName);
    }
    
    public Attribute(@NotNull final String glslName, @NotNull final String type, @NotNull final String mojangName, @NotNull final String replacement) {
        this.glslName = glslName;
        this.type = type;
        this.mojangName = mojangName;
        this.replacement = replacement;
    }
    
    @NotNull
    public String getGlslName() {
        return this.glslName;
    }
    
    @NotNull
    public String getType() {
        return this.type;
    }
    
    @NotNull
    public String getMojangName() {
        return this.mojangName;
    }
    
    @NotNull
    public String getReplacement() {
        return this.replacement;
    }
}
