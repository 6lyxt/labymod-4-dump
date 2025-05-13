// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.math;

import java.util.StringJoiner;
import net.labymod.api.util.math.vector.IntVector3;
import net.labymod.api.util.math.vector.DoubleVector3;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.util.math.vector.FloatVector3;

public class AxisAlignedBoundingBox
{
    private double minX;
    private double minY;
    private double minZ;
    private double maxX;
    private double maxY;
    private double maxZ;
    
    public AxisAlignedBoundingBox() {
        this(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public AxisAlignedBoundingBox(final double minX, final double minY, final double minZ, final double maxX, final double maxY, final double maxZ) {
        this.minX = Math.min(minX, maxX);
        this.minY = Math.min(minY, maxY);
        this.minZ = Math.min(minZ, maxZ);
        this.maxX = Math.max(minX, maxX);
        this.maxY = Math.max(minY, maxY);
        this.maxZ = Math.max(minZ, maxZ);
    }
    
    public AxisAlignedBoundingBox(final float minX, final float minY, final float minZ, final float maxX, final float maxY, final float maxZ) {
        this(minX, minY, minZ, maxX, maxY, (double)maxZ);
    }
    
    public AxisAlignedBoundingBox(@NotNull final FloatVector3 vector) {
        this(vector, new FloatVector3(vector).add(FloatVector3.ONE));
    }
    
    public AxisAlignedBoundingBox(@NotNull final DoubleVector3 min, @NotNull final DoubleVector3 max) {
        this(min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ());
    }
    
    public AxisAlignedBoundingBox(@NotNull final FloatVector3 minVector, @NotNull final FloatVector3 maxVector) {
        this(minVector.getX(), minVector.getY(), minVector.getZ(), maxVector.getX(), maxVector.getY(), maxVector.getZ());
    }
    
    @NotNull
    public AxisAlignedBoundingBox move(final double x, final double y, final double z) {
        return new AxisAlignedBoundingBox(this.minX + x, this.minY + y, this.minZ + z, this.maxX + x, this.maxY + y, this.maxZ + z);
    }
    
    @NotNull
    public AxisAlignedBoundingBox move(@NotNull final DoubleVector3 vector) {
        return this.move((float)vector.getX(), (float)vector.getY(), (float)vector.getZ());
    }
    
    @NotNull
    public AxisAlignedBoundingBox move(@NotNull final FloatVector3 vector) {
        return this.move(vector.getX(), vector.getY(), vector.getZ());
    }
    
    @NotNull
    public AxisAlignedBoundingBox move(@NotNull final IntVector3 vector) {
        return this.move(vector.getX(), vector.getY(), vector.getZ());
    }
    
    public void moveSelf(final float x, final float y, final float z) {
        this.set(this.getMinX() + x, this.getMinY() + y, this.getMinZ() + z, this.getMaxX() + x, this.getMaxY() + y, this.getMaxZ() + z);
    }
    
    public void moveSelf(@NotNull final FloatVector3 other) {
        this.moveSelf(other.getX(), other.getY(), other.getZ());
    }
    
    public void moveSelf(@NotNull final IntVector3 other) {
        this.moveSelf((float)other.getX(), (float)other.getY(), (float)other.getZ());
    }
    
    @NotNull
    public AxisAlignedBoundingBox minX(final double minX) {
        return new AxisAlignedBoundingBox(minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ);
    }
    
    @NotNull
    public AxisAlignedBoundingBox minY(final double minY) {
        return new AxisAlignedBoundingBox(this.minX, minY, this.minZ, this.maxX, this.maxY, this.maxZ);
    }
    
    @NotNull
    public AxisAlignedBoundingBox minZ(final double minZ) {
        return new AxisAlignedBoundingBox(this.minX, this.minY, minZ, this.maxX, this.maxY, this.maxZ);
    }
    
    @NotNull
    public AxisAlignedBoundingBox maxX(final double maxX) {
        return new AxisAlignedBoundingBox(this.minX, this.minY, this.minZ, maxX, this.maxY, this.maxZ);
    }
    
    @NotNull
    public AxisAlignedBoundingBox maxY(final double maxY) {
        return new AxisAlignedBoundingBox(this.minX, this.minY, this.minZ, this.maxX, maxY, this.maxZ);
    }
    
    @NotNull
    public AxisAlignedBoundingBox maxZ(final double maxZ) {
        return new AxisAlignedBoundingBox(this.minX, this.minY, this.minZ, this.maxX, this.maxY, maxZ);
    }
    
    public void set(final double minX, final double minY, final double minZ, final double maxX, final double maxY, final double maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }
    
    public void set(final AxisAlignedBoundingBox other) {
        this.set(other.getMinX(), other.getMinY(), other.getMinZ(), other.getMaxX(), other.getMaxY(), other.getMaxZ());
    }
    
    public AxisAlignedBoundingBox expandTowards(final FloatVector3 vector) {
        return this.expandTowards(vector.getX(), vector.getY(), vector.getZ());
    }
    
    public AxisAlignedBoundingBox expandTowards(final double x, final double y, final double z) {
        double minX = this.minX;
        double minY = this.minY;
        double minZ = this.minZ;
        double maxX = this.maxX;
        double maxY = this.maxY;
        double maxZ = this.maxZ;
        if (x < 0.0) {
            minX += x;
        }
        else {
            maxX += x;
        }
        if (y < 0.0) {
            minY += y;
        }
        else {
            maxY += y;
        }
        if (z < 0.0) {
            minZ += z;
        }
        else {
            maxZ += z;
        }
        return new AxisAlignedBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
    }
    
    public double getXSize() {
        return this.maxX - this.minX;
    }
    
    public double getYSize() {
        return this.maxY - this.minY;
    }
    
    public double getZSize() {
        return this.maxZ - this.minZ;
    }
    
    public DoubleVector3 getCenter() {
        return new DoubleVector3(MathHelper.lerp(this.minX, this.maxX, 0.5f), MathHelper.lerp(this.minY, this.maxY, 0.5f), MathHelper.lerp(this.minZ, this.maxZ, 0.5f));
    }
    
    public double getMinX() {
        return this.minX;
    }
    
    public double getMinY() {
        return this.minY;
    }
    
    public double getMinZ() {
        return this.minZ;
    }
    
    public double getMaxX() {
        return this.maxX;
    }
    
    public double getMaxY() {
        return this.maxY;
    }
    
    public double getMaxZ() {
        return this.maxZ;
    }
    
    public double getXWidth() {
        return this.maxX - this.minX;
    }
    
    public double getZWidth() {
        return this.maxZ - this.minZ;
    }
    
    public double getHeight() {
        return this.maxY - this.minY;
    }
    
    public boolean intersects(final AxisAlignedBoundingBox other) {
        return this.intersects(other.getMinX(), other.getMinY(), other.getMinZ(), other.getMaxX(), other.getMaxY(), other.getMaxZ());
    }
    
    public boolean intersects(final FloatVector3 min, final FloatVector3 max) {
        return this.intersects(Math.min(min.getX(), max.getX()), Math.min(min.getY(), max.getY()), Math.min(min.getZ(), max.getZ()), Math.max(min.getX(), max.getX()), Math.max(min.getY(), max.getY()), Math.max(min.getZ(), max.getZ()));
    }
    
    public boolean intersects(final double minX, final double minY, final double minZ, final double maxX, final double maxY, final double maxZ) {
        return this.minX < maxX && this.maxX > minX && this.minY < maxY && this.maxY > minY && this.minZ < maxZ && this.maxZ > minZ;
    }
    
    public AxisAlignedBoundingBox inflate(final double delta) {
        return this.inflate(delta, delta, delta);
    }
    
    public AxisAlignedBoundingBox inflate(final double deltaX, final double deltaY, final double deltaZ) {
        final double minX = this.minX - deltaX;
        final double minY = this.minY - deltaY;
        final double minZ = this.minZ - deltaZ;
        final double maxX = this.maxX + deltaX;
        final double maxY = this.maxY + deltaY;
        final double maxZ = this.maxZ + deltaZ;
        return new AxisAlignedBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
    }
    
    public double collide(final Axis axis, final AxisAlignedBoundingBox other, final double value) {
        return switch (axis) {
            default -> throw new MatchException(null, null);
            case X -> this.collideX(other, value);
            case Y -> this.collideY(other, value);
            case Z -> this.collideZ(other, value);
        };
    }
    
    private double collideX(final AxisAlignedBoundingBox other, double x) {
        if (this.isCollidingY(other)) {
            return x;
        }
        if (this.isCollidingZ(other)) {
            return x;
        }
        if (x > 0.0 && other.getMaxX() <= this.getMinX()) {
            final double maxX = this.getMinX() - other.getMaxX();
            if (maxX < x) {
                x = maxX;
            }
        }
        if (x < 0.0 && other.getMinX() >= this.getMaxX()) {
            final double maxX = this.getMaxX() - other.getMinX();
            if (maxX > x) {
                x = maxX;
            }
        }
        return x;
    }
    
    private double collideY(final AxisAlignedBoundingBox other, double y) {
        if (this.isCollidingX(other)) {
            return y;
        }
        if (this.isCollidingZ(other)) {
            return y;
        }
        if (y > 0.0 && other.getMaxY() <= this.getMinY()) {
            final double maxY = this.getMinY() - other.getMaxY();
            if (maxY < y) {
                y = maxY;
            }
        }
        if (y < 0.0 && other.getMinY() >= this.getMaxY()) {
            final double maxY = this.getMaxY() - other.getMinY();
            if (maxY > y) {
                y = maxY;
            }
        }
        return y;
    }
    
    private double collideZ(final AxisAlignedBoundingBox other, double z) {
        if (this.isCollidingY(other)) {
            return z;
        }
        if (this.isCollidingX(other)) {
            return z;
        }
        if (z > 0.0 && other.getMaxZ() <= this.getMinZ()) {
            final double maxZ = this.getMinZ() - other.getMaxZ();
            if (maxZ < z) {
                z = maxZ;
            }
        }
        if (z < 0.0 && other.getMinZ() >= this.getMaxZ()) {
            final double maxZ = this.getMaxZ() - other.getMinZ();
            if (maxZ > z) {
                z = maxZ;
            }
        }
        return z;
    }
    
    private boolean isCollidingX(final AxisAlignedBoundingBox other) {
        return other.getMaxX() <= this.getMinX() || other.getMinX() >= this.getMaxX();
    }
    
    private boolean isCollidingY(final AxisAlignedBoundingBox other) {
        return other.getMaxY() <= this.getMinY() || other.getMinY() >= this.getMaxY();
    }
    
    private boolean isCollidingZ(final AxisAlignedBoundingBox other) {
        return other.getMaxZ() <= this.getMinZ() || other.getMinZ() >= this.getMaxZ();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final AxisAlignedBoundingBox box = (AxisAlignedBoundingBox)o;
        return Double.compare(this.minX, box.minX) == 0 && Double.compare(this.minY, box.minY) == 0 && Double.compare(this.minZ, box.minZ) == 0 && Double.compare(this.maxX, box.maxX) == 0 && Double.compare(this.maxY, box.maxY) == 0 && Double.compare(this.maxZ, box.maxZ) == 0;
    }
    
    @Override
    public int hashCode() {
        int result = Double.hashCode(this.minX);
        result = 31 * result + Double.hashCode(this.minY);
        result = 31 * result + Double.hashCode(this.minZ);
        result = 31 * result + Double.hashCode(this.maxX);
        result = 31 * result + Double.hashCode(this.maxY);
        result = 31 * result + Double.hashCode(this.maxZ);
        return result;
    }
    
    @Override
    public String toString() {
        return new StringJoiner((CharSequence)", ", AxisAlignedBoundingBox.class.getSimpleName(), (CharSequence)"]").add("minX=" + this.minX).add("minY=" + this.minY).add("minZ=" + this.minZ).add("maxX=" + this.maxX).add("maxY=" + this.maxY).add("maxZ=" + this.maxZ).toString();
    }
}
