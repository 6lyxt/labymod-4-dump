// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.shader;

@Deprecated(forRemoval = true, since = "4.2.41")
public enum GFXShaderType implements ShaderType
{
    VERTEX(Shader.Type.VERTEX), 
    FRAGMENT(Shader.Type.FRAGMENT), 
    GEOMETRY(Shader.Type.GEOMETRY), 
    TESS_CONTROL(Shader.Type.TESS_CONTROL), 
    TESS_EVALUATION(Shader.Type.TESS_EVALUATION), 
    COMPUTE(Shader.Type.COMPUTE);
    
    private final Shader.Type type;
    
    private GFXShaderType(final Shader.Type type) {
        this.type = type;
    }
    
    @Override
    public int getHandle() {
        return this.type.getId();
    }
    
    public Shader.Type getType() {
        return this.type;
    }
    
    @Override
    public String toString() {
        return this.type.getFileExtension();
    }
}
