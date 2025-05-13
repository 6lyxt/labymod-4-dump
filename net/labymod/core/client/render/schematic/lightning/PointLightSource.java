// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.schematic.lightning;

import net.labymod.api.util.math.vector.FloatVector3;

public class PointLightSource implements LightSource
{
    public static final FloatVector3 WHITE;
    private final FloatVector3 position;
    private final FloatVector3 color;
    private float constant;
    private float linear;
    private float quadratic;
    
    public PointLightSource() {
        this.position = new FloatVector3(0.0f, 0.0f, 0.0f);
        this.color = new FloatVector3(1.0f, 1.0f, 1.0f);
        this.constant = 1.0f;
        this.linear = 0.09f;
        this.quadratic = 0.0512f;
    }
    
    @Override
    public FloatVector3 getPosition() {
        return this.position;
    }
    
    public FloatVector3 getColor() {
        return this.color;
    }
    
    public float getConstant() {
        return this.constant;
    }
    
    public void setConstant(final float constant) {
        this.constant = constant;
    }
    
    public float getLinear() {
        return this.linear;
    }
    
    public void setLinear(final float linear) {
        this.linear = linear;
    }
    
    public float getQuadratic() {
        return this.quadratic;
    }
    
    public void setQuadratic(final float quadratic) {
        this.quadratic = quadratic;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final PointLightSource that = (PointLightSource)o;
        return this.position.equals(that.position);
    }
    
    @Override
    public int hashCode() {
        return this.position.hashCode();
    }
    
    static {
        WHITE = new FloatVector3(0.5f, 0.5f, 0.5f);
    }
}
