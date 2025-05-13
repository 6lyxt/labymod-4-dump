// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.shader.transformer.model;

public enum UniformType
{
    FLOAT("float", "float"), 
    INT("int", "int"), 
    MATRIX_2("matrix2x2", "mat2"), 
    MATRIX_3("matrix3x3", "mat3"), 
    MATRIX_4("matrix4x4", "mat4"), 
    VECTOR_2("float", "vec2"), 
    VECTOR_3("float", "vec3"), 
    VECTOR_4("float", "vec4");
    
    private final String name;
    private final String glslName;
    
    private UniformType(final String name, final String glslName) {
        this.name = name;
        this.glslName = glslName;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getGlslName() {
        return this.glslName;
    }
}
