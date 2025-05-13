// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.client.entity;

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
        if (!(pose instanceof bgl)) {
            return null;
        }
        final bgl minecraftPose = (bgl)pose;
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
            case i: {
                return EntityPose.CROAKING;
            }
            case j: {
                return EntityPose.USING_TONGUE;
            }
            case l: {
                return EntityPose.ROARING;
            }
            case m: {
                return EntityPose.SNIFFING;
            }
            case n: {
                return EntityPose.EMERGING;
            }
            case o: {
                return EntityPose.DIGGING;
            }
            case k: {
                return EntityPose.SITTING;
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
                return bgl.f;
            }
            case SLEEPING: {
                return bgl.c;
            }
            case DYING: {
                return bgl.h;
            }
            case FALL_FLYING: {
                return bgl.b;
            }
            case SWIMMING: {
                return bgl.d;
            }
            case STANDING: {
                return bgl.a;
            }
            case SPIN_ATTACK: {
                return bgl.e;
            }
            case LONG_JUMPING: {
                return bgl.g;
            }
            case CROAKING: {
                return bgl.i;
            }
            case USING_TONGUE: {
                return bgl.j;
            }
            case SITTING: {
                return bgl.k;
            }
            case ROARING: {
                return bgl.l;
            }
            case SNIFFING: {
                return bgl.m;
            }
            case EMERGING: {
                return bgl.n;
            }
            case DIGGING: {
                return bgl.o;
            }
            default: {
                return null;
            }
        }
    }
}
