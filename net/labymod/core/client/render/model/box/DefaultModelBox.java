// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.model.box;

import net.labymod.api.util.math.Direction;
import net.labymod.api.client.render.model.box.ModelBoxVertex;
import net.labymod.api.client.render.model.box.ModelBoxQuad;
import net.labymod.api.client.render.model.box.ModelBox;

public class DefaultModelBox implements ModelBox
{
    private final ModelBoxQuad[] quads;
    private final float minX;
    private final float minY;
    private final float minZ;
    private final float maxX;
    private final float maxY;
    private final float maxZ;
    private final boolean mirror;
    
    public DefaultModelBox(final int textureOffsetX, final int textureOffsetY, final float x, final float y, final float z, final float width, final float height, final float depth, final float deltaX, final float deltaY, final float deltaZ, final boolean mirror, final float textureWidth, final float textureHeight) {
        this.quads = new ModelBoxQuad[6];
        this.minX = x - deltaX;
        this.minY = y - deltaY;
        this.minZ = z - deltaZ;
        this.maxX = x + width + deltaX;
        this.maxY = y + height + deltaY;
        this.maxZ = z + depth + deltaZ;
        this.mirror = mirror;
        float minX = this.minX;
        final float minY = this.minY;
        final float minZ = this.minZ;
        float maxX = this.maxX;
        final float maxY = this.maxY;
        final float maxZ = this.maxZ;
        if (mirror) {
            final float temp = maxX;
            maxX = minX;
            minX = temp;
        }
        final ModelBoxVertex vertex1 = new DefaultModelBoxVertex(minX, minY, minZ, 0.0f, 0.0f);
        final ModelBoxVertex vertex2 = new DefaultModelBoxVertex(maxX, minY, minZ, 0.0f, 8.0f);
        final ModelBoxVertex vertex3 = new DefaultModelBoxVertex(maxX, maxY, minZ, 8.0f, 8.0f);
        final ModelBoxVertex vertex4 = new DefaultModelBoxVertex(minX, maxY, minZ, 8.0f, 0.0f);
        final ModelBoxVertex vertex5 = new DefaultModelBoxVertex(minX, minY, maxZ, 0.0f, 0.0f);
        final ModelBoxVertex vertex6 = new DefaultModelBoxVertex(maxX, minY, maxZ, 0.0f, 8.0f);
        final ModelBoxVertex vertex7 = new DefaultModelBoxVertex(maxX, maxY, maxZ, 8.0f, 8.0f);
        final ModelBoxVertex vertex8 = new DefaultModelBoxVertex(minX, maxY, maxZ, 8.0f, 0.0f);
        final float tex1 = (float)textureOffsetX;
        final float tex2 = textureOffsetX + depth;
        final float tex3 = textureOffsetX + depth + width;
        final float tex4 = textureOffsetX + depth + width + width;
        final float tex5 = textureOffsetX + depth + width + depth;
        final float tex6 = textureOffsetX + depth + width + depth + width;
        final float tex7 = (float)textureOffsetY;
        final float tex8 = textureOffsetY + depth;
        final float tex9 = textureOffsetY + depth + height;
        this.quads[2] = new DefaultModelBoxQuad(new ModelBoxVertex[] { vertex6, vertex5, vertex1, vertex2 }, tex2, tex7, tex3, tex8, textureWidth, textureHeight, mirror, Direction.DOWN);
        this.quads[3] = new DefaultModelBoxQuad(new ModelBoxVertex[] { vertex3, vertex4, vertex8, vertex7 }, tex3, tex8, tex4, tex7, textureWidth, textureHeight, mirror, Direction.UP);
        this.quads[1] = new DefaultModelBoxQuad(new ModelBoxVertex[] { vertex1, vertex5, vertex8, vertex4 }, tex1, tex8, tex2, tex9, textureWidth, textureHeight, mirror, Direction.WEST);
        this.quads[4] = new DefaultModelBoxQuad(new ModelBoxVertex[] { vertex2, vertex1, vertex4, vertex3 }, tex2, tex8, tex3, tex9, textureWidth, textureHeight, mirror, Direction.NORTH);
        this.quads[0] = new DefaultModelBoxQuad(new ModelBoxVertex[] { vertex6, vertex2, vertex3, vertex7 }, tex3, tex8, tex5, tex9, textureWidth, textureHeight, mirror, Direction.EAST);
        this.quads[5] = new DefaultModelBoxQuad(new ModelBoxVertex[] { vertex5, vertex6, vertex7, vertex8 }, tex5, tex8, tex6, tex9, textureWidth, textureHeight, mirror, Direction.SOUTH);
    }
    
    @Override
    public float getMinX() {
        return this.minX;
    }
    
    @Override
    public float getMinY() {
        return this.minY;
    }
    
    @Override
    public float getMinZ() {
        return this.minZ;
    }
    
    @Override
    public float getMaxX() {
        return this.maxX;
    }
    
    @Override
    public float getMaxY() {
        return this.maxY;
    }
    
    @Override
    public float getMaxZ() {
        return this.maxZ;
    }
    
    @Override
    public float getWidth() {
        return Math.abs(this.maxX - this.minX);
    }
    
    @Override
    public float getHeight() {
        return Math.abs(this.maxY - this.minY);
    }
    
    @Override
    public float getDepth() {
        return Math.abs(this.maxZ - this.minZ);
    }
    
    @Override
    public boolean isMirror() {
        return this.mirror;
    }
    
    @Override
    public ModelBoxQuad[] getQuads() {
        return this.quads;
    }
}
