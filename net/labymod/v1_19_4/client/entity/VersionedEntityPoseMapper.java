// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.client.entity;

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
        if (!(pose instanceof bgj)) {
            return null;
        }
        final bgj minecraftPose = (bgj)pose;
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
                return bgj.f;
            }
            case SLEEPING: {
                return bgj.c;
            }
            case DYING: {
                return bgj.h;
            }
            case FALL_FLYING: {
                return bgj.b;
            }
            case SWIMMING: {
                return bgj.d;
            }
            case STANDING: {
                return bgj.a;
            }
            case SPIN_ATTACK: {
                return bgj.e;
            }
            case LONG_JUMPING: {
                return bgj.g;
            }
            case CROAKING: {
                return bgj.i;
            }
            case USING_TONGUE: {
                return bgj.j;
            }
            case SITTING: {
                return bgj.k;
            }
            case ROARING: {
                return bgj.l;
            }
            case SNIFFING: {
                return bgj.m;
            }
            case EMERGING: {
                return bgj.n;
            }
            case DIGGING: {
                return bgj.o;
            }
            default: {
                return null;
            }
        }
    }
}
