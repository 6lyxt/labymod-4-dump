// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.shader;

import java.util.Iterator;
import net.labymod.api.thirdparty.optifine.OptiFine;
import java.util.Collection;
import java.util.HashMap;
import net.labymod.api.client.gfx.shader.uniform.Uniform;
import java.util.Map;

public final class MojangShader
{
    private final String name;
    private final int programId;
    private final Map<String, Uniform> uniforms;
    
    public MojangShader(final String name, final int programId) {
        this.name = name;
        this.programId = programId;
        this.uniforms = new HashMap<String, Uniform>();
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getProgramId() {
        return this.programId;
    }
    
    public <T> void addUniform(final String name, final Uniform uniform) {
        this.uniforms.put(name, uniform);
    }
    
    public <T extends Uniform> T getUniform(final String name) {
        return (T)this.uniforms.get(name);
    }
    
    public boolean hasUniforms() {
        return this.uniforms.isEmpty();
    }
    
    public Collection<Uniform> getUniforms() {
        return this.uniforms.values();
    }
    
    public void uploadUniforms() {
        if (OptiFine.isPresent() || !this.hasUniforms()) {
            return;
        }
        for (final Uniform uniform : this.getUniforms()) {
            uniform.upload();
        }
    }
}
