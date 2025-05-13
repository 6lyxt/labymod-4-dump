// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21.client.entity;

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
        if (!(pose instanceof bua)) {
            return null;
        }
        final bua minecraftPose = (bua)pose;
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
                return bua.f;
            }
            case SLEEPING: {
                return bua.c;
            }
            case DYING: {
                return bua.h;
            }
            case FALL_FLYING: {
                return bua.b;
            }
            case SWIMMING: {
                return bua.d;
            }
            case STANDING: {
                return bua.a;
            }
            case SPIN_ATTACK: {
                return bua.e;
            }
            case LONG_JUMPING: {
                return bua.g;
            }
            case CROAKING: {
                return bua.i;
            }
            case USING_TONGUE: {
                return bua.j;
            }
            case SITTING: {
                return bua.k;
            }
            case ROARING: {
                return bua.l;
            }
            case SNIFFING: {
                return bua.m;
            }
            case EMERGING: {
                return bua.n;
            }
            case DIGGING: {
                return bua.o;
            }
            case SLIDING: {
                return bua.p;
            }
            case SHOOTING: {
                return bua.q;
            }
            case INHALING: {
                return bua.r;
            }
            default: {
                return null;
            }
        }
    }
}
