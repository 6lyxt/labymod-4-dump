// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.render.model.entity;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Event;
import net.labymod.api.event.DefaultCancellable;

public class HumanoidModelPoseAnimationEvent extends DefaultCancellable implements Event
{
    private final Phase phase;
    private final LivingEntity livingEntity;
    private final LivingEntity.HandSide handSide;
    
    public HumanoidModelPoseAnimationEvent(@NotNull final Phase phase, @NotNull final LivingEntity livingEntity, @NotNull final LivingEntity.HandSide handSide) {
        this.phase = phase;
        this.livingEntity = livingEntity;
        this.handSide = handSide;
    }
    
    public Phase phase() {
        return this.phase;
    }
    
    public LivingEntity livingEntity() {
        return this.livingEntity;
    }
    
    public LivingEntity.HandSide handSide() {
        return this.handSide;
    }
}
