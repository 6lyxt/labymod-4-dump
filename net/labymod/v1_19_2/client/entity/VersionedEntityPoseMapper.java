// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.client.entity;

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
        if (!(pose instanceof bco)) {
            return null;
        }
        final bco minecraftPose = (bco)pose;
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
            case k: {
                return EntityPose.ROARING;
            }
            case l: {
                return EntityPose.SNIFFING;
            }
            case m: {
                return EntityPose.EMERGING;
            }
            case n: {
                return EntityPose.DIGGING;
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
                return bco.f;
            }
            case SLEEPING: {
                return bco.c;
            }
            case DYING: {
                return bco.h;
            }
            case FALL_FLYING: {
                return bco.b;
            }
            case SWIMMING: {
                return bco.d;
            }
            case STANDING: {
                return bco.a;
            }
            case SPIN_ATTACK: {
                return bco.e;
            }
            case LONG_JUMPING: {
                return bco.g;
            }
            case CROAKING: {
                return bco.i;
            }
            case USING_TONGUE: {
                return bco.j;
            }
            case ROARING: {
                return bco.k;
            }
            case SNIFFING: {
                return bco.l;
            }
            case EMERGING: {
                return bco.m;
            }
            case DIGGING: {
                return bco.n;
            }
            default: {
                return null;
            }
        }
    }
}
