// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.items.pet.ai;

import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.util.math.vector.DoubleVector3;

public class Transform
{
    private final DoubleVector3 position;
    private final DoubleVector3 previousPosition;
    private final FloatVector3 rotation;
    private final FloatVector3 previousRotation;
    
    public Transform() {
        this.position = new DoubleVector3();
        this.previousPosition = new DoubleVector3();
        this.rotation = new FloatVector3();
        this.previousRotation = new FloatVector3();
    }
    
    public void update() {
        this.previousPosition.set(this.position);
        this.previousRotation.set(this.rotation);
    }
    
    public void set(final double x, final double y, final double z) {
        this.position.set(x, y, z);
    }
    
    public void set(final DoubleVector3 position) {
        this.position.set(position);
    }
    
    public void rotate(final Stack stack, final float partialTicks) {
        final float rotX = MathHelper.interpolateRotation(this.previousRotation.getX(), this.rotation.getX(), partialTicks);
        final float rotY = MathHelper.interpolateRotation(this.previousRotation.getY(), this.rotation.getY(), partialTicks);
        final float rotZ = MathHelper.interpolateRotation(this.previousRotation.getZ(), this.rotation.getZ(), partialTicks);
        stack.rotate(rotZ, 0.0f, 0.0f, 1.0f);
        stack.rotate(rotY, 0.0f, 1.0f, 0.0f);
        stack.rotate(rotX, 1.0f, 0.0f, 0.0f);
    }
    
    public DoubleVector3 position() {
        return this.position;
    }
    
    public DoubleVector3 previousPosition() {
        return this.previousPosition;
    }
    
    public FloatVector3 rotation() {
        return this.rotation;
    }
    
    public FloatVector3 previousRotation() {
        return this.previousRotation;
    }
}
