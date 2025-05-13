// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.client.entity;

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
        if (!(pose instanceof bmx)) {
            return null;
        }
        final bmx minecraftPose = (bmx)pose;
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
            case p: {
                return EntityPose.SLIDING;
            }
            case q: {
                return EntityPose.SHOOTING;
            }
            case r: {
                return EntityPose.INHALING;
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
                return bmx.f;
            }
            case SLEEPING: {
                return bmx.c;
            }
            case DYING: {
                return bmx.h;
            }
            case FALL_FLYING: {
                return bmx.b;
            }
            case SWIMMING: {
                return bmx.d;
            }
            case STANDING: {
                return bmx.a;
            }
            case SPIN_ATTACK: {
                return bmx.e;
            }
            case LONG_JUMPING: {
                return bmx.g;
            }
            case CROAKING: {
                return bmx.i;
            }
            case USING_TONGUE: {
                return bmx.j;
            }
            case SITTING: {
                return bmx.k;
            }
            case ROARING: {
                return bmx.l;
            }
            case SNIFFING: {
                return bmx.m;
            }
            case EMERGING: {
                return bmx.n;
            }
            case DIGGING: {
                return bmx.o;
            }
            case SLIDING: {
                return bmx.p;
            }
            case SHOOTING: {
                return bmx.q;
            }
            case INHALING: {
                return bmx.r;
            }
            default: {
                return null;
            }
        }
    }
}
