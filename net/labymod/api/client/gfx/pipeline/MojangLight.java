// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline;

import net.labymod.api.client.gfx.shader.uniform.Uniform3F;
import net.labymod.api.client.gfx.shader.ShaderProgram;
import net.labymod.api.util.math.vector.FloatVector3;

public class MojangLight
{
    private final FloatVector3 light0Direction;
    private final FloatVector3 light1Direction;
    
    public MojangLight() {
        this(new FloatVector3(), new FloatVector3());
    }
    
    public MojangLight(final FloatVector3 light0Direction, final FloatVector3 light1Direction) {
        this.light0Direction = light0Direction;
        this.light1Direction = light1Direction;
    }
    
    public void updateLight0Direction(final float x, final float y, final float z) {
        this.light0Direction.set(x, y, z);
    }
    
    public void updateLight0Direction(final FloatVector3 direction) {
        this.light0Direction.set(direction);
    }
    
    public void updateLight1Direction(final float x, final float y, final float z) {
        this.light1Direction.set(x, y, z);
    }
    
    public void updateLight1Direction(final FloatVector3 direction) {
        this.light1Direction.set(direction);
    }
    
    public FloatVector3 light0Direction() {
        return this.light0Direction;
    }
    
    public FloatVector3 light1Direction() {
        return this.light1Direction;
    }
    
    public void setupShaderLights(final ShaderProgram shaderProgram) {
        final Uniform3F lightDirection0 = shaderProgram.getUniform("LightDirection0");
        if (lightDirection0 != null) {
            lightDirection0.set(this.light0Direction);
            lightDirection0.upload();
        }
        final Uniform3F lightDirection2 = shaderProgram.getUniform("LightDirection1");
        if (lightDirection2 != null) {
            lightDirection2.set(this.light1Direction);
            lightDirection2.upload();
        }
    }
}
