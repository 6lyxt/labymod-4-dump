// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.client.entity;

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
        if (!(pose instanceof buc)) {
            return null;
        }
        final buc minecraftPose = (buc)pose;
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
                return buc.f;
            }
            case SLEEPING: {
                return buc.c;
            }
            case DYING: {
                return buc.h;
            }
            case FALL_FLYING: {
                return buc.b;
            }
            case SWIMMING: {
                return buc.d;
            }
            case STANDING: {
                return buc.a;
            }
            case SPIN_ATTACK: {
                return buc.e;
            }
            case LONG_JUMPING: {
                return buc.g;
            }
            case CROAKING: {
                return buc.i;
            }
            case USING_TONGUE: {
                return buc.j;
            }
            case SITTING: {
                return buc.k;
            }
            case ROARING: {
                return buc.l;
            }
            case SNIFFING: {
                return buc.m;
            }
            case EMERGING: {
                return buc.n;
            }
            case DIGGING: {
                return buc.o;
            }
            case SLIDING: {
                return buc.p;
            }
            case SHOOTING: {
                return buc.q;
            }
            case INHALING: {
                return buc.r;
            }
            default: {
                return null;
            }
        }
    }
}
