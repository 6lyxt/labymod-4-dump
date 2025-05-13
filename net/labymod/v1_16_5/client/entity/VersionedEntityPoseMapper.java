// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.client.entity;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.entity.EntityPose;
import javax.inject.Inject;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.entity.EntityPoseMapper;

@Singleton
@Implements(EntityPoseMapper.class)
public class VersionedEntityPoseMapper implements EntityPoseMapper
{
    @Inject
    public VersionedEntityPoseMapper() {
    }
    
    @Override
    public EntityPose fromMinecraft(final Object pose) {
        if (!(pose instanceof aqx)) {
            return null;
        }
        final aqx minecraftPose = (aqx)pose;
        switch (minecraftPose) {
            case a: {
                return EntityPose.STANDING;
            }
            case b: {
                return EntityPose.FALL_FLYING;
            }
            case c: {
                return EntityPose.SLEEPING;
            }
            case d: {
                return EntityPose.SWIMMING;
            }
            case e: {
                return EntityPose.SPIN_ATTACK;
            }
            case f: {
                return EntityPose.CROUCHING;
            }
            case g: {
                return EntityPose.DYING;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + String.valueOf(minecraftPose));
            }
        }
    }
    
    @Nullable
    @Override
    public Object toMinecraft(final EntityPose entityPose) {
        switch (entityPose) {
            case CROUCHING: {
                return aqx.f;
            }
            case SLEEPING: {
                return aqx.c;
            }
            case DYING: {
                return aqx.g;
            }
            case FALL_FLYING: {
                return aqx.b;
            }
            case SWIMMING: {
                return aqx.d;
            }
            case STANDING: {
                return aqx.a;
            }
            case SPIN_ATTACK: {
                return aqx.e;
            }
            default: {
                return null;
            }
        }
    }
}
