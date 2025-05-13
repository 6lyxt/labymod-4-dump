// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.client.entity;

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
        if (!(pose instanceof bws)) {
            return null;
        }
        final bws minecraftPose = (bws)pose;
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
                return bws.f;
            }
            case SLEEPING: {
                return bws.c;
            }
            case DYING: {
                return bws.h;
            }
            case FALL_FLYING: {
                return bws.b;
            }
            case SWIMMING: {
                return bws.d;
            }
            case STANDING: {
                return bws.a;
            }
            case SPIN_ATTACK: {
                return bws.e;
            }
            case LONG_JUMPING: {
                return bws.g;
            }
            case CROAKING: {
                return bws.i;
            }
            case USING_TONGUE: {
                return bws.j;
            }
            case SITTING: {
                return bws.k;
            }
            case ROARING: {
                return bws.l;
            }
            case SNIFFING: {
                return bws.m;
            }
            case EMERGING: {
                return bws.n;
            }
            case DIGGING: {
                return bws.o;
            }
            case SLIDING: {
                return bws.p;
            }
            case SHOOTING: {
                return bws.q;
            }
            case INHALING: {
                return bws.r;
            }
            default: {
                return null;
            }
        }
    }
}
