// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.util;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.util.math.Direction;
import net.labymod.api.util.math.vector.FloatVector3;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.phys.hit.BlockHitResult;
import net.labymod.api.client.world.phys.hit.EntityHitResult;
import net.labymod.api.client.world.phys.hit.HitResult;

@Mixin({ auh.class })
public class MixinMovingObjectPosition implements HitResult, EntityHitResult, BlockHitResult
{
    @Shadow
    public auh.a a;
    @Shadow
    public cq b;
    private FloatVector3 labyMod$hitLocation;
    private FloatVector3 labyMod$blockPosition;
    private Direction labyMod$blockHitDirection;
    private HitType labyMod$hitType;
    private Entity labyMod$entity;
    
    @Inject(method = { "<init>(Lnet/minecraft/util/MovingObjectPosition$MovingObjectType;Lnet/minecraft/util/Vec3;Lnet/minecraft/util/EnumFacing;Lnet/minecraft/util/BlockPos;)V" }, at = { @At("RETURN") })
    private void labyMod$createApiVector(final auh.a type, final aui location, final cq facing, final cj blockPosition, final CallbackInfo ci) {
        this.labyMod$hitLocation = new FloatVector3((float)location.a, (float)location.b, (float)location.c);
        this.labyMod$blockPosition = new FloatVector3((float)blockPosition.n(), (float)blockPosition.o(), (float)blockPosition.p());
        if (this.b != null) {
            this.labyMod$blockHitDirection = this.labyMod$getDirection(this.b);
        }
        if (this.a != null) {
            this.labyMod$hitType = this.labyMod$getHitType(this.a);
        }
    }
    
    @Inject(method = { "<init>(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/Vec3;)V" }, at = { @At("RETURN") })
    private void labyMod$createApiVectorEntity(final pk entity, final aui location, final CallbackInfo ci) {
        this.labyMod$hitLocation = new FloatVector3((float)location.a, (float)location.b, (float)location.c);
        this.labyMod$entity = (Entity)entity;
        this.labyMod$hitType = this.labyMod$getHitType(this.a);
    }
    
    @Override
    public FloatVector3 location() {
        return this.labyMod$hitLocation;
    }
    
    @Override
    public HitType type() {
        return this.labyMod$hitType;
    }
    
    @Override
    public FloatVector3 getBlockPosition() {
        return this.labyMod$blockPosition;
    }
    
    @Override
    public Direction getBlockDirection() {
        return this.labyMod$blockHitDirection;
    }
    
    @Override
    public boolean isInside() {
        return false;
    }
    
    @Override
    public Entity getEntity() {
        return this.labyMod$entity;
    }
    
    private HitType labyMod$getHitType(final auh.a type) {
        switch (type) {
            case a: {
                return HitType.MISS;
            }
            case b: {
                return HitType.BLOCK;
            }
            case c: {
                return HitType.ENTITY;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + String.valueOf(type));
            }
        }
    }
    
    private Direction labyMod$getDirection(final cq direction) {
        switch (direction) {
            case a: {
                return Direction.DOWN;
            }
            case b: {
                return Direction.UP;
            }
            case c: {
                return Direction.NORTH;
            }
            case d: {
                return Direction.SOUTH;
            }
            case e: {
                return Direction.WEST;
            }
            case f: {
                return Direction.EAST;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + String.valueOf(direction));
            }
        }
    }
}
