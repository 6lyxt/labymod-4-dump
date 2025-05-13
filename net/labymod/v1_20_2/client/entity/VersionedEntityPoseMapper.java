// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.client.entity;

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
        if (!(pose instanceof bjs)) {
            return null;
        }
        final bjs minecraftPose = (bjs)pose;
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
                return bjs.f;
            }
            case SLEEPING: {
                return bjs.c;
            }
            case DYING: {
                return bjs.h;
            }
            case FALL_FLYING: {
                return bjs.b;
            }
            case SWIMMING: {
                return bjs.d;
            }
            case STANDING: {
                return bjs.a;
            }
            case SPIN_ATTACK: {
                return bjs.e;
            }
            case LONG_JUMPING: {
                return bjs.g;
            }
            case CROAKING: {
                return bjs.i;
            }
            case USING_TONGUE: {
                return bjs.j;
            }
            case SITTING: {
                return bjs.k;
            }
            case ROARING: {
                return bjs.l;
            }
            case SNIFFING: {
                return bjs.m;
            }
            case EMERGING: {
                return bjs.n;
            }
            case DIGGING: {
                return bjs.o;
            }
            default: {
                return null;
            }
        }
    }
}
