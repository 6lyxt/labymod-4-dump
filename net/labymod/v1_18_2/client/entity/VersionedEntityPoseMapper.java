// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.client.entity;

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
        if (!(pose instanceof ayk)) {
            return null;
        }
        final ayk minecraftPose = (ayk)pose;
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
                return ayk.f;
            }
            case SLEEPING: {
                return ayk.c;
            }
            case DYING: {
                return ayk.h;
            }
            case FALL_FLYING: {
                return ayk.b;
            }
            case SWIMMING: {
                return ayk.d;
            }
            case STANDING: {
                return ayk.a;
            }
            case SPIN_ATTACK: {
                return ayk.e;
            }
            case LONG_JUMPING: {
                return ayk.g;
            }
            default: {
                return null;
            }
        }
    }
}
