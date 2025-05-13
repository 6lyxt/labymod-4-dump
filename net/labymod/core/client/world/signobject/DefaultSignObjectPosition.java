// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.world.signobject;

import net.labymod.api.util.math.vector.FloatVector4;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.client.blockentity.SignBlockEntity;
import net.labymod.api.client.world.block.BlockPosition;
import net.labymod.api.client.world.signobject.SignObjectPosition;

public class DefaultSignObjectPosition implements SignObjectPosition
{
    private final BlockPosition position;
    private final SignBlockEntity.SignSide side;
    private final float rotationYaw;
    private final FloatVector3 center;
    private final FloatMatrix4 target;
    private final FloatVector4 rotation;
    
    public DefaultSignObjectPosition(final BlockPosition position, final SignBlockEntity.SignSide side, final float rotationYaw) {
        this.center = new FloatVector3();
        this.target = new FloatMatrix4();
        this.rotation = new FloatVector4();
        this.position = position;
        this.side = side;
        this.rotationYaw = rotationYaw;
    }
    
    @Override
    public BlockPosition position() {
        return this.position;
    }
    
    @Override
    public SignBlockEntity.SignSide side() {
        return this.side;
    }
    
    @Override
    public float rotationYaw() {
        return this.side.modifyYaw(this.rotationYaw);
    }
    
    @Override
    public FloatVector3 calculatePosition(final float deltaX, final float deltaY, final float deltaZ) {
        final BlockPosition sign = this.position();
        this.center.set(sign.getX() + 0.5f, (float)sign.getY(), sign.getZ() + 0.5f);
        this.target.identity();
        this.target.rotate(this.rotationYaw(), 0.0f, 1.0f, 0.0f);
        return this.transform(deltaX, deltaY, deltaZ);
    }
    
    private FloatVector3 transform(final float offsetX, final float offsetY, final float offsetZ) {
        this.rotation.set(offsetX, 0.0f, offsetZ, 0.0f);
        this.rotation.transform(this.target);
        return new FloatVector3(this.rotation.getX() + this.center.getX(), offsetY + this.center.getY(), this.rotation.getZ() + this.center.getZ());
    }
    
    @Override
    public String toString() {
        final BlockPosition pos = this.position();
        return "SignObjectPosition(" + pos.getX() + "," + pos.getY() + "," + pos.getZ() + "," + this.rotationYaw() + "°)";
    }
}
