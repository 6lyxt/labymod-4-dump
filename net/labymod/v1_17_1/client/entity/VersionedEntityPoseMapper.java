// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.client.entity;

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
        if (!(pose instanceof aug)) {
            return null;
        }
        final aug minecraftPose = (aug)pose;
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
                return EntityPose.LONG_JUMPING;
            }
            case h: {
                return EntityPose.DYING;
            }
            default: {
                return EntityPose.UNKNOWN;
            }
        }
    }
    
    @Nullable
    @Override
    public Object toMinecraft(final EntityPose entityPose) {
        switch (entityPose) {
            case CROUCHING: {
                return aug.f;
            }
            case SLEEPING: {
                return aug.c;
            }
            case DYING: {
                return aug.h;
            }
            case FALL_FLYING: {
                return aug.b;
            }
            case SWIMMING: {
                return aug.d;
            }
            case STANDING: {
                return aug.a;
            }
            case SPIN_ATTACK: {
                return aug.e;
            }
            case LONG_JUMPING: {
                return aug.g;
            }
            default: {
                return null;
            }
        }
    }
}
