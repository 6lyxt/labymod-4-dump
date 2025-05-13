// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.client.entity;

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
        if (!(pose instanceof bvu)) {
            return null;
        }
        final bvu minecraftPose = (bvu)pose;
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
                return bvu.f;
            }
            case SLEEPING: {
                return bvu.c;
            }
            case DYING: {
                return bvu.h;
            }
            case FALL_FLYING: {
                return bvu.b;
            }
            case SWIMMING: {
                return bvu.d;
            }
            case STANDING: {
                return bvu.a;
            }
            case SPIN_ATTACK: {
                return bvu.e;
            }
            case LONG_JUMPING: {
                return bvu.g;
            }
            case CROAKING: {
                return bvu.i;
            }
            case USING_TONGUE: {
                return bvu.j;
            }
            case SITTING: {
                return bvu.k;
            }
            case ROARING: {
                return bvu.l;
            }
            case SNIFFING: {
                return bvu.m;
            }
            case EMERGING: {
                return bvu.n;
            }
            case DIGGING: {
                return bvu.o;
            }
            case SLIDING: {
                return bvu.p;
            }
            case SHOOTING: {
                return bvu.q;
            }
            case INHALING: {
                return bvu.r;
            }
            default: {
                return null;
            }
        }
    }
}
