// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.model.box;

import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.FloatMatrix3;
import net.labymod.api.util.math.Direction;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.client.render.model.box.ModelBoxVertex;
import net.labymod.api.client.render.model.box.ModelBoxQuad;

public class DefaultModelBoxQuad implements ModelBoxQuad
{
    public final ModelBoxVertex[] vertices;
    private float minU;
    private float minV;
    private float maxU;
    private float maxV;
    private final FloatVector3 normal;
    private final Direction direction;
    private boolean visible;
    
    public DefaultModelBoxQuad(final ModelBoxVertex[] vertices, final float minU, final float minV, final float maxU, final float maxV, final float textureWidth, final float textureHeight, final boolean mirror, final Direction direction) {
        this.minU = minU;
        this.minV = minV;
        this.maxU = maxU;
        this.maxV = maxV;
        this.vertices = vertices;
        this.visible = true;
        this.direction = direction;
        final float width = 0.0f / textureWidth;
        final float height = 0.0f / textureHeight;
        this.remapVertex(0, maxU / textureWidth - width, minV / textureHeight + height);
        this.remapVertex(1, minU / textureWidth + width, minV / textureHeight + height);
        this.remapVertex(2, minU / textureWidth + width, maxV / textureHeight - height);
        this.remapVertex(3, maxU / textureWidth - width, maxV / textureHeight - height);
        if (mirror) {
            for (int length = vertices.length, index = 0; index < length / 2; ++index) {
                final ModelBoxVertex vertex = vertices[index];
                vertices[index] = vertices[length - 1 - index];
                vertices[length - 1 - index] = vertex;
            }
        }
        this.normal = direction.getNormal().copy();
        if (mirror) {
            this.normal.multiply(-1.0f, 1.0f, 1.0f);
        }
    }
    
    private void remapVertex(final int index, final float u, final float v) {
        this.vertices[index] = this.vertices[index].remap(u, v);
    }
    
    @Override
    public ModelBoxVertex[] getVertices() {
        return this.vertices;
    }
    
    @Override
    public FloatVector3 getNormal() {
        return this.normal;
    }
    
    @Override
    public Direction getDirection() {
        return this.direction;
    }
    
    @Override
    public FloatVector3 getNormal(final FloatMatrix3 matrix) {
        final FloatVector3 direction = this.normal;
        return MathHelper.transform(matrix, direction.getX(), direction.getY(), direction.getZ());
    }
    
    @Override
    public boolean isVisible() {
        return this.visible;
    }
    
    @Override
    public void setVisible(final boolean visible) {
        this.visible = visible;
    }
    
    @Override
    public float getMinU() {
        return this.minU;
    }
    
    @Override
    public float getMinV() {
        return this.minV;
    }
    
    @Override
    public float getMaxU() {
        return this.maxU;
    }
    
    @Override
    public float getMaxV() {
        return this.maxV;
    }
}
