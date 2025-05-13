// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.client.entity;

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
        if (!(pose instanceof bes)) {
            return null;
        }
        final bes minecraftPose = (bes)pose;
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
                return bes.f;
            }
            case SLEEPING: {
                return bes.c;
            }
            case DYING: {
                return bes.h;
            }
            case FALL_FLYING: {
                return bes.b;
            }
            case SWIMMING: {
                return bes.d;
            }
            case STANDING: {
                return bes.a;
            }
            case SPIN_ATTACK: {
                return bes.e;
            }
            case LONG_JUMPING: {
                return bes.g;
            }
            case CROAKING: {
                return bes.i;
            }
            case USING_TONGUE: {
                return bes.j;
            }
            case SITTING: {
                return bes.k;
            }
            case ROARING: {
                return bes.l;
            }
            case SNIFFING: {
                return bes.m;
            }
            case EMERGING: {
                return bes.n;
            }
            case DIGGING: {
                return bes.o;
            }
            default: {
                return null;
            }
        }
    }
}
