// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.client.entity;

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
        if (!(pose instanceof bud)) {
            return null;
        }
        final bud minecraftPose = (bud)pose;
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
                return bud.f;
            }
            case SLEEPING: {
                return bud.c;
            }
            case DYING: {
                return bud.h;
            }
            case FALL_FLYING: {
                return bud.b;
            }
            case SWIMMING: {
                return bud.d;
            }
            case STANDING: {
                return bud.a;
            }
            case SPIN_ATTACK: {
                return bud.e;
            }
            case LONG_JUMPING: {
                return bud.g;
            }
            case CROAKING: {
                return bud.i;
            }
            case USING_TONGUE: {
                return bud.j;
            }
            case SITTING: {
                return bud.k;
            }
            case ROARING: {
                return bud.l;
            }
            case SNIFFING: {
                return bud.m;
            }
            case EMERGING: {
                return bud.n;
            }
            case DIGGING: {
                return bud.o;
            }
            case SLIDING: {
                return bud.p;
            }
            case SHOOTING: {
                return bud.q;
            }
            case INHALING: {
                return bud.r;
            }
            default: {
                return null;
            }
        }
    }
}
