// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.client.entity;

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
        if (!(pose instanceof byr)) {
            return null;
        }
        final byr minecraftPose = (byr)pose;
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
                return byr.f;
            }
            case SLEEPING: {
                return byr.c;
            }
            case DYING: {
                return byr.h;
            }
            case FALL_FLYING: {
                return byr.b;
            }
            case SWIMMING: {
                return byr.d;
            }
            case STANDING: {
                return byr.a;
            }
            case SPIN_ATTACK: {
                return byr.e;
            }
            case LONG_JUMPING: {
                return byr.g;
            }
            case CROAKING: {
                return byr.i;
            }
            case USING_TONGUE: {
                return byr.j;
            }
            case SITTING: {
                return byr.k;
            }
            case ROARING: {
                return byr.l;
            }
            case SNIFFING: {
                return byr.m;
            }
            case EMERGING: {
                return byr.n;
            }
            case DIGGING: {
                return byr.o;
            }
            case SLIDING: {
                return byr.p;
            }
            case SHOOTING: {
                return byr.q;
            }
            case INHALING: {
                return byr.r;
            }
            default: {
                return null;
            }
        }
    }
}
