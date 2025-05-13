// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.schematic.block.material;

import net.labymod.api.util.math.vector.FloatVector3;

public class BoundingBox
{
    private final FloatVector3[] corners;
    
    public BoundingBox(final float minX, final float minY, final float minZ, final float maxX, final float maxY, final float maxZ) {
        this.corners = new FloatVector3[8];
        this.set(minX, minY, minZ, maxX, maxY, maxZ);
    }
    
    public FloatVector3 getCorner(final int index) {
        return this.corners[index];
    }
    
    public FloatVector3 getCorner(int index, final int rotation) {
        for (int i = 0; i < rotation; ++i) {
            switch (index) {
                case 0: {
                    index = 2;
                    break;
                }
                case 3: {
                    index = 1;
                    break;
                }
                case 4: {
                    index = 6;
                    break;
                }
                case 7: {
                    index = 5;
                    break;
                }
                case 1:
                case 5: {
                    --index;
                    break;
                }
                case 2:
                case 6: {
                    ++index;
                    break;
                }
            }
        }
        return this.corners[index];
    }
    
    public void setCorner(final int index, final float x, final float y, final float z) {
        FloatVector3 corner = this.corners[index];
        if (corner == null) {
            corner = new FloatVector3();
        }
        corner.set(x, y, z);
        this.corners[index] = corner;
    }
    
    public void set(final float minX, final float minY, final float minZ, final float maxX, final float maxY, final float maxZ) {
        this.setCorner(0, minX, minY, minZ);
        this.setCorner(1, minX, minY, maxZ);
        this.setCorner(2, maxX, minY, minZ);
        this.setCorner(3, maxX, minY, maxZ);
        this.setCorner(4, minX, maxY, minZ);
        this.setCorner(5, minX, maxY, maxZ);
        this.setCorner(6, maxX, maxY, minZ);
        this.setCorner(7, maxX, maxY, maxZ);
    }
    
    public void rotateX(final float pivotX, final float pivotY, final float pivotZ, final float degree) {
        this.translate(-pivotX, -pivotY, -pivotZ);
        this.rotateX(degree);
        this.translate(pivotX, pivotY, pivotZ);
    }
    
    public void rotateY(final float pivotX, final float pivotY, final float pivotZ, final float degree) {
        this.translate(-pivotX, -pivotY, -pivotZ);
        this.rotateY(degree);
        this.translate(pivotX, pivotY, pivotZ);
    }
    
    public void rotateZ(final float pivotX, final float pivotY, final float pivotZ, final float degree) {
        this.translate(-pivotX, -pivotY, -pivotZ);
        this.rotateZ(degree);
        this.translate(pivotX, pivotY, pivotZ);
    }
    
    private void rotateX(final double degree) {
        final float angleRadians = (float)Math.toRadians(degree);
        for (int i = 0; i < 8; ++i) {
            final FloatVector3 corner = this.getCorner(i);
            final float y = corner.getY();
            final float z = corner.getZ();
            corner.setY((float)(Math.cos(angleRadians) * y - Math.sin(angleRadians) * z));
            corner.setZ((float)(Math.sin(angleRadians) * y + Math.cos(angleRadians) * z));
        }
    }
    
    private void rotateY(final double degree) {
        final float angleRadians = (float)Math.toRadians(degree);
        for (int i = 0; i < 8; ++i) {
            final FloatVector3 corner = this.getCorner(i);
            final float x = corner.getX();
            final float z = corner.getZ();
            corner.setX((float)(Math.cos(angleRadians) * x + Math.sin(angleRadians) * z));
            corner.setZ((float)(-Math.sin(angleRadians) * x + Math.cos(angleRadians) * z));
        }
    }
    
    private void rotateZ(final double degree) {
        final float angleRadians = (float)Math.toRadians(degree);
        for (int i = 0; i < 8; ++i) {
            final FloatVector3 corner = this.getCorner(i);
            final float x = corner.getX();
            final float y = corner.getY();
            corner.setX((float)(Math.cos(angleRadians) * x - Math.sin(angleRadians) * y));
            corner.setY((float)(Math.sin(angleRadians) * x + Math.cos(angleRadians) * y));
        }
    }
    
    private void translate(final float deltaX, final float deltaY, final float deltaZ) {
        for (int i = 0; i < 8; ++i) {
            final FloatVector3 corner = this.getCorner(i);
            corner.setX(corner.getX() + deltaX);
            corner.setY(corner.getY() + deltaY);
            corner.setZ(corner.getZ() + deltaZ);
        }
    }
}
