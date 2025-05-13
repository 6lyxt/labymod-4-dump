// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.entity;

import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.render.matrix.Stack;

public interface EntityRenderDispatcher
{
    boolean isRenderDebugHitBoxes();
    
    void setRenderDebugHitBoxes(final boolean p0);
    
    void renderInScreen(final Stack p0, final Entity p1, final float p2);
    
    default <E extends LivingEntity> void renderInScreen(final Stack stack, final E entity, final float x, final float y, final float rotationX, final float rotationY, final float rotationZ, final float scale, final boolean renderName, final float headRotationStrength, final float partialTicks) {
        final float yRotBody = entity.getBodyRotationY();
        final float yRotHead = entity.getHeadRotationY();
        final float prevYRotBody = entity.getPreviousBodyRotationY();
        final float prevYRotHead = entity.getPreviousHeadRotationY();
        final float headOffsetY = MathHelper.angleDifference(yRotBody, yRotHead);
        final float prevHeadOffsetY = MathHelper.angleDifference(prevYRotBody, prevYRotHead);
        final float cameraRotation = (MinecraftVersions.V1_21.orNewer() != renderName) ? 0.0f : 180.0f;
        final float dollRotation = rotationY + 180.0f - cameraRotation;
        entity.setBodyRotationY(dollRotation);
        entity.setHeadRotationY(dollRotation - headOffsetY * headRotationStrength);
        entity.setPreviousBodyRotationY(dollRotation);
        entity.setPreviousHeadRotationY(dollRotation - prevHeadOffsetY * headRotationStrength);
        stack.push();
        stack.translate(x, y, 50.0f);
        stack.multiply(FloatMatrix4.scaleMatrix(scale, scale, -scale));
        stack.rotate(rotationX, 1.0f, 0.0f, 0.0f);
        stack.rotate(cameraRotation, 0.0f, 1.0f, 0.0f);
        stack.rotate(rotationZ + 180.0f, 0.0f, 0.0f, 1.0f);
        this.renderInScreen(stack, entity, partialTicks);
        stack.pop();
        entity.setBodyRotationY(yRotBody);
        entity.setHeadRotationY(yRotHead);
        entity.setPreviousBodyRotationY(prevYRotBody);
        entity.setPreviousHeadRotationY(prevYRotHead);
    }
}
