// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.items.pet.ai.controller;

import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.core.main.user.shop.item.items.pet.ai.Transform;
import net.labymod.api.util.math.MathHelper;
import it.unimi.dsi.fastutil.floats.FloatConsumer;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.core.main.user.shop.item.items.pet.ai.PetBehavior;

public class LookController
{
    private static final float TOLERANCE = 1.0E-5f;
    private static final float DEFAULT_Y_ROTATION_SPEED = 10.0f;
    private final PetBehavior behavior;
    private final DoubleVector3 lookAt;
    private float yMaxRotationSpeed;
    private int lookAtCooldown;
    
    public LookController(final PetBehavior behavior) {
        this.lookAt = new DoubleVector3();
        this.behavior = behavior;
    }
    
    public void tick() {
        if (this.lookAtCooldown > 0) {
            --this.lookAtCooldown;
            this.calculateYRotationDelta(delta -> {
                final Transform transform = this.behavior.transform();
                final FloatVector3 rotation = transform.rotation();
                final float currentY = rotation.getY();
                rotation.setY(MathHelper.rotateTowards(currentY, delta, this.yMaxRotationSpeed));
            });
        }
    }
    
    public void setLookAt(final DoubleVector3 target) {
        this.setLookAt(target, 10.0f);
    }
    
    public void setLookAt(final DoubleVector3 target, final float yMaxRotationSpeed) {
        this.setLookAt(target.getX(), target.getY(), target.getZ(), yMaxRotationSpeed);
    }
    
    public void setLookAt(final double x, final double y, final double z) {
        this.setLookAt(x, y, z, 10.0f);
    }
    
    public void setLookAt(final double x, final double y, final double z, final float yMaxRotationSpeed) {
        this.lookAt.set(x, y, z);
        this.yMaxRotationSpeed = yMaxRotationSpeed;
        this.lookAtCooldown = 30;
    }
    
    public DoubleVector3 lookAt() {
        return this.lookAt;
    }
    
    private void calculateYRotationDelta(final FloatConsumer deltaConsumer) {
        final double diffX = this.lookAt.getX() - this.behavior.position().getX();
        final double diffZ = this.lookAt.getZ() - this.behavior.position().getZ();
        if (Math.abs(diffX) <= 9.999999747378752E-6 && Math.abs(diffZ) <= 9.999999747378752E-6) {
            return;
        }
        final float delta = (float)(-Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793 + 90.0);
        deltaConsumer.accept(delta);
    }
}
